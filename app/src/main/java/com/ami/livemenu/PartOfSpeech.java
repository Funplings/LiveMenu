
package com.ami.livemenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartOfSpeech {

    @SerializedName("tag")
    @Expose
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
