package com.stream.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GithubClient githubClient = retrofit.create(GithubClient.class);
        Call<List<GitHubRepo>> call = githubClient.reposForUser("tomleejumah");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos = response.body();
                StringBuilder repoNames = new StringBuilder();

                if (repos != null) {
                    for (GitHubRepo repo : repos) {
                        repoNames.append(repo.getName()).append("\n");
                    }
                    Toast.makeText(MainActivity.this, repoNames.toString(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: "+ repoNames);
                } else {
                    Toast.makeText(MainActivity.this, "No repositories found", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, " "+throwable, Toast.LENGTH_LONG).show();
            }
        });
    }
}