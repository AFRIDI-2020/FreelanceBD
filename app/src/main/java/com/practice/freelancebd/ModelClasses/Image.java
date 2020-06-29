package com.practice.freelancebd.ModelClasses;

import android.net.Uri;

public class Image {

    private Uri userSelectUri;
    private String fileName;

    public Uri getUserSelectUri() {
        return userSelectUri;
    }

    public String getFileName() {
        return fileName;
    }

    public Image(Uri userSelectUri, String fileName) {
        this.userSelectUri = userSelectUri;
        this.fileName = fileName;
    }

    public Image() {
    }
}
