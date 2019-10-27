
package com.ami.livemenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("text")
    @Expose
    private Text text;
    @SerializedName("partOfSpeech")
    @Expose
    private PartOfSpeech partOfSpeech;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

}
