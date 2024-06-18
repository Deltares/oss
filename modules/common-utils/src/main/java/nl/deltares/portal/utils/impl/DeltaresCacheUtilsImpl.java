package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.cache.PortalCache;
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

import java.util.Map;

@Component(
        immediate = true,
        service = {DeltaresCacheUtils.class, ModelListener.class}
)
public class DeltaresCacheUtilsImpl extends BaseModelListener<JournalArticle>  implements DeltaresCacheUtils {

    protected static final String CACHE_NAME = DeltaresCacheUtils.class.getName();
    @Reference
    private SingleVMPool _singleVMPool;

    private volatile boolean clearingCache ;
    private static PortalCache<String, Object> _portalCache;

    @Activate
    public void activate() {
        //noinspection unchecked
        _portalCache = (PortalCache<String, Object>) _singleVMPool.getPortalCache(CACHE_NAME);
    }

    @Deactivate
    public void deactivate() {
        _singleVMPool.removePortalCache(CACHE_NAME);
    }

    @Override
    public AbsDsdArticle findArticle(JournalArticle article) {
        if (clearingCache) return null;
        return (AbsDsdArticle) _portalCache.get(String.valueOf(article.getResourcePrimKey()));
    }

    @Override
    public long getCacheSize(){
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
            _portalCache.put(String.valueOf(journalArticle.getResourcePrimKey()), article, 600);
        }
    }

    @Override
    public void clearCache(){
        if (clearingCache) return;
        clearingCache = true;
        deactivate();
        activate();
        clearingCache = false;
    }

    @Override
    public Map<String, Object> findPortletConfig(String portletId) {
        if(clearingCache) return null;
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
            _portalCache.put(portletId, config, 3600);
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

}
