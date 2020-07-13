package com.uisrael.mensajeriainstantanea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    public void Register(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }
}
