package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.utils.DsdArticleCache;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        service = {DsdArticleCache.class, ModelListener.class}
)
public class DsdArticleCacheImpl extends BaseModelListener<JournalArticle> implements DsdArticleCache {

    protected static final String CACHE_NAME = DsdArticleCache.class.getName();
    @Reference
    private SingleVMPool _singleVMPool;

    private volatile boolean clearingCache ;
    private static PortalCache<Long, AbsDsdArticle> _portalCache;

    @Activate
    public void activate() {
        //noinspection unchecked
        _portalCache = (PortalCache<Long, AbsDsdArticle>) _singleVMPool.getPortalCache(CACHE_NAME);
    }

    @Deactivate
    public void deactivate() {
        _singleVMPool.removePortalCache(CACHE_NAME);
    }

    @Override
    public AbsDsdArticle findArticle(JournalArticle article) {
        if (clearingCache) return null;
        return _portalCache.get(article.getResourcePrimKey());
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
            _portalCache.remove(journalArticle.getResourcePrimKey());
        } else {
            _portalCache.put(journalArticle.getResourcePrimKey(), article, 600);
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
    public void onAfterRemove(JournalArticle model) throws ModelListenerException {
        if (!clearingCache && model != null) {
            _portalCache.remove(model.getResourcePrimKey());
        }

        super.onAfterRemove(model);
    }

    @Override
    public void onBeforeUpdate(JournalArticle originalModel, JournalArticle model) throws ModelListenerException {
        if (!clearingCache && originalModel != null) {
            _portalCache.remove(originalModel.getResourcePrimKey());
        }

        super.onBeforeUpdate(originalModel, model);
    }

}
