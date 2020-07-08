package nl.deltares.portal.model.impl;

import java.util.ArrayList;

class DuplicateCheck {

    private final ArrayList<String> uuidCache = new ArrayList<>();

    public boolean checkDuplicates(AbsDsdArticle article){
        String uuid = article.getJournalArticle().getUuid();
        if (uuidCache.contains(uuid)) return false;
        return uuidCache.add(uuid);
    }
}
