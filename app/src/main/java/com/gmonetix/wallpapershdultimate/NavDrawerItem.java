package com.gmonetix.wallpapershdultimate;

public class NavDrawerItem {

    private String albumId, albumTitle;
    // boolean flag to check for recent album
    private boolean isRecentAlbum = false;

    public NavDrawerItem() {
    }

    public NavDrawerItem(String albumId, String albumTitle) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
    }

    public NavDrawerItem(String albumId, String albumTitle,
                         boolean isRecentAlbum) {
        this.albumTitle = albumTitle;
        this.isRecentAlbum = isRecentAlbum;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return this.albumTitle;
    }

    public void setTitle(String title) {
        this.albumTitle = title;
    }
}