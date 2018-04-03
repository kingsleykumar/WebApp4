package com.sb.services.utils;

/**
 * Created by Kingsley Kumar on 24/11/2016.
 */
public class SelectionItem {
    private String id;
    private String displayName;

    public SelectionItem(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
