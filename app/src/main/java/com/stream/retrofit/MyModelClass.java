package com.stream.retrofit;

import com.google.gson.annotations.SerializedName;

public class MyModelClass {
    private Integer id;
   private String Username;
    private String email;
    private String[] topics;

    public MyModelClass(String name, String email, String[] topics) {
        this.Username = name;
        this.email = email;
        this.topics = topics;
    }

    public Integer getId() {
        return id;
    }
    @SerializedName("name")
    private String name;
    public String getReposName() {
        return name;
    }
}
