package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import nl.deltares.portal.model.impl.AbsDsdArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuplicateCheck {

    private final ArrayList<String> uuidCache = new ArrayList<>();

    public boolean checkDuplicates(AbsDsdArticle article){
        return checkDuplicates(article.getJournalArticle());
    }
    public boolean checkDuplicates(JournalArticle article){
        String uuid = article.getUuid();
        if (uuidCache.contains(uuid)) return false;
        return uuidCache.add(uuid);
    }

    public List<JournalArticle> filterLatest(List<JournalArticle> structureArticles) {

        HashMap<String, JournalArticle> latestVersionMap = new HashMap<>();
        structureArticles.forEach(article -> {
            JournalArticle existing;
            if ((existing = latestVersionMap.get(article.getArticleId())) != null
                    && existing.getVersion() >= article.getVersion()){
                return;
            }
            latestVersionMap.put(article.getArticleId(), article);
        });
        return new ArrayList<>(latestVersionMap.values());
    }

}
