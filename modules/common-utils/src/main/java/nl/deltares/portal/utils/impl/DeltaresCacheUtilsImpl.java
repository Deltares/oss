package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.cache.AggregatedPortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.utils.DeltaresCacheUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = {DeltaresCacheUtils.class, ModelListener.class}
)
public class DeltaresCacheUtilsImpl extends BaseModelListener<JournalArticle> implements DeltaresCacheUtils {

    // Default time to live is 1 hour
    private static final int DEFAULT_TIME_TO_LIVE = 3600;
    protected static final String CACHE_NAME = DeltaresCacheUtils.class.getName();
    protected static final String TIME_TO_LIVE_CACHE_NAME = DeltaresPortalCacheListener.class.getName();
    @Reference
    private SingleVMPool _singleVMPool;

    private volatile boolean clearingCache;
    private static PortalCache<String, Object> _portalCache;
    private static PortalCache<String, Long> _timeToLiveCache;

    @Activate
    public void activate() {
        //noinspection unchecked
        _portalCache = (PortalCache<String, Object>) _singleVMPool.getPortalCache(CACHE_NAME);
        //noinspection unchecked
         _timeToLiveCache = (PortalCache<String, Long>) _singleVMPool.getPortalCache(TIME_TO_LIVE_CACHE_NAME);
        _portalCache.registerPortalCacheListener(new DeltaresPortalCacheListener(       ));
    }

    @Deactivate
    public void deactivate() {
        _portalCache.unregisterPortalCacheListeners();
        _singleVMPool.removePortalCache(CACHE_NAME);
        _singleVMPool.removePortalCache(TIME_TO_LIVE_CACHE_NAME);
    }

    @Override
    public AbsDsdArticle findArticle(JournalArticle article) {
        if (clearingCache) return null;
        return (AbsDsdArticle) _portalCache.get(String.valueOf(article.getResourcePrimKey()));
    }

    @Override
    public long getCacheSize() {
        if (clearingCache) return 0;
        return _portalCache.getKeys().size();
    }

    @Override
    public void putArticle(JournalArticle journalArticle, AbsDsdArticle article) {
        if (clearingCache) return;
        if (journalArticle == null) return;
        if (article == null) {
            _portalCache.remove(String.valueOf(journalArticle.getResourcePrimKey()));
        } else {
            _portalCache.put(String.valueOf(journalArticle.getResourcePrimKey()), article, DEFAULT_TIME_TO_LIVE);
        }
    }

    @Override
    public void clearCache() {
        if (clearingCache) return;
        clearingCache = true;
        _portalCache.removeAll();
        clearingCache = false;
    }

    @Override
    public Map<String, Object> findPortletConfig(String portletId) {
        if (clearingCache) return null;
        //noinspection unchecked
        return (Map<String, Object>) _portalCache.get(portletId);


    }

    @Override
    public void putPortletConfig(String portletId, Map<String, Object> config) {
        if (clearingCache) return;
        if (portletId == null) return;
        if (config == null) {
            _portalCache.remove(portletId);
        } else {
            _portalCache.put(portletId, config, DEFAULT_TIME_TO_LIVE);
        }
    }

    @Override
    public void onAfterRemove(JournalArticle model) throws ModelListenerException {
        if (!clearingCache && model != null) {
            _portalCache.remove(String.valueOf(model.getResourcePrimKey()));
        }

        super.onAfterRemove(model);
    }

    @Override
    public void onBeforeUpdate(JournalArticle originalModel, JournalArticle model) throws ModelListenerException {
        if (!clearingCache && originalModel != null) {
            _portalCache.remove(String.valueOf(originalModel.getResourcePrimKey()));
        }

        super.onBeforeUpdate(originalModel, model);
    }

    private static class DeltaresPortalCacheListener extends AggregatedPortalCacheListener<String, Object> {


        // Cleanup cache every hour
        private static final long CLEANUP_TIME_PERIOD = 3600000;
        private long lastCleanup = System.currentTimeMillis();
        private volatile boolean clearingCache;

        @Override
        public void notifyEntryPut(PortalCache<String, Object> portalCache, String key, Object value, int timeToLive) throws PortalCacheException {
            _timeToLiveCache.put(key, System.currentTimeMillis() + timeToLive * 1000L);
            cleanupCache();
        }

        @Override
        public void notifyEntryEvicted(PortalCache<String, Object> portalCache, String key, Object value, int timeToLive) {
            _timeToLiveCache.remove(key);
            cleanupCache();
        }

        private void cleanupCache() {
            if (clearingCache) return;
            if (System.currentTimeMillis() - lastCleanup > CLEANUP_TIME_PERIOD) {
                clearingCache = true;
                try {
                    long now = System.currentTimeMillis();
                    lastCleanup = now;
                    final List<String> keys = _timeToLiveCache.getKeys();
                    for (String key : keys) {
                        final Long time = _timeToLiveCache.get(key);
                        if (time != null && time < now) {
                            _portalCache.remove(key);
                            _timeToLiveCache.remove(key);
                        }
                    }
                } finally {
                    clearingCache = false;
                }
            }

        }

        @Override
        public void notifyRemoveAll(PortalCache<String, Object> portalCache) {
            _timeToLiveCache.removeAll();
        }
    }
}
