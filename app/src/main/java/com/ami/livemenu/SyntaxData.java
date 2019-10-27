
package com.ami.livemenu;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyntaxData {

    @SerializedName("tokens")
    @Expose
    private List<Token> tokens = null;

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

}
