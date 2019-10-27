
package com.ami.livemenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Text {

    @SerializedName("content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
