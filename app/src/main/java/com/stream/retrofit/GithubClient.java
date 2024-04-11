package com.stream.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubClient {
    @GET("/users/{username}/repos")
    Call<List<GitHubRepo>>reposForUser(@Path("username")String user);
}
