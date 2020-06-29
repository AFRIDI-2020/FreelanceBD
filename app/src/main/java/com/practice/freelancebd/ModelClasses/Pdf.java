package com.practice.freelancebd.ModelClasses;

import android.net.Uri;

public class Pdf {

    private Uri pdfUri;
    private String pdfName;

    public Uri getPdfUri() {
        return pdfUri;
    }

    public String getPdfName() {
        return pdfName;
    }

    public Pdf(Uri pdfUri, String pdfName) {
        this.pdfUri = pdfUri;
        this.pdfName = pdfName;
    }

    public Pdf() {
    }
}
