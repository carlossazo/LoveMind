package com.example.lovemind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button Btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Btn2 = findViewById(R.id.button2);

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Login();
            }
        });
    }

    public void Login(){
        Intent intent = new Intent(this,ChatActivity.class);
        startActivity(intent);
    }
}