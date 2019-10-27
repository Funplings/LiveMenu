
package com.ami.livemenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("type")
    @Expose
    private String type = "PLAIN_TEXT";
    @SerializedName("language")
    @Expose
    private String language = "en";
    @SerializedName("content")
    @Expose
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
