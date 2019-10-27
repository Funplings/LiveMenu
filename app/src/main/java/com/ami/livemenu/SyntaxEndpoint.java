package com.ami.livemenu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SyntaxEndpoint {
    @POST("v1beta2/documents:analyzeSyntax")
    Call<SyntaxData> getSyntaxData(@Body SyntaxInput input, @Query("key") String key);
}
