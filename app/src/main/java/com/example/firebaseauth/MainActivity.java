package com.example.firebaseauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        String resultado = sharedPreferences.getString("LOGIN", "");
        if (!Boolean.parseBoolean(resultado)) {

            criarLogin();
        }
    }

    private void criarLogin() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.ic_user)
                        .setTheme(R.style.LoginTheme)
                        .build(), 123
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK) {

            sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("LOGIN","TRUE");
            editor.apply();

        }
        else {
            finish();
        }
    }
    public  void Logout (View v){

        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN","false");
        editor.apply();
        criarLogin();

    }
}
