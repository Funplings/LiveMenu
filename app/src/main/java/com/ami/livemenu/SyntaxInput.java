
package com.ami.livemenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyntaxInput {

    @SerializedName("document")
    @Expose
    private Document document;
    @SerializedName("encodingType")
    @Expose
    private String encodingType = "UTF8";

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

}
