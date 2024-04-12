package com.stream.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyApiClient {
    @GET("/users/{username}/repos")
    Call<List<MyModelClass>>reposForUser(@Path("username")String user);
    @POST("User")
    Call<MyModelClass>createAccount(@Body MyModelClass myModelClass);
}
