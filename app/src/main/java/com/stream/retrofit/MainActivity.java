package com.stream.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
    private EditText edtAbout,edtMail,edtName;

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

        edtName = findViewById(R.id.edtName);
        edtAbout = findViewById(R.id.edtAbout);
        edtMail = findViewById(R.id.edtMail);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MyApiClient myApiClient = retrofit.create(MyApiClient.class);
        Call<List<MyModelClass>> call = myApiClient.reposForUser("tomleejumah");

        call.enqueue(new Callback<List<MyModelClass>>() {
            @Override
            public void onResponse(Call<List<MyModelClass>> call, Response<List<MyModelClass>> response) {
                List<MyModelClass> repos = response.body();
                StringBuilder repoNames = new StringBuilder();

                if (repos != null) {
                    for (MyModelClass repo : repos) {
                        repoNames.append(repo.getReposName()).append("\n");
                    }
                    Toast.makeText(MainActivity.this, repoNames.toString(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: "+ repoNames);
                } else {
                    Toast.makeText(MainActivity.this, "No repositories found", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<MyModelClass>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, " "+throwable, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendReq(View view) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MyApiClient client = retrofit.create(MyApiClient.class);

        MyModelClass userModel = new MyModelClass(
                edtName.getText().toString(),
                edtMail.getText().toString(),
                edtAbout.getText().toString().split(",")

        );
        Call<MyModelClass> call=client.createAccount(userModel);

        call.enqueue(new Callback<MyModelClass>() {
            @Override
            public void onResponse(Call<MyModelClass> call, Response<MyModelClass> response) {
                Toast.makeText(MainActivity.this, "ID"+response.body().getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyModelClass> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, ":)"+ throwable, Toast.LENGTH_SHORT).show();
            }
        });
    }
}