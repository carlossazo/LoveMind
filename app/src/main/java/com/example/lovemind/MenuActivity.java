package com.example.lovemind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {
ImageButton Test, Meditacion, Chat, Perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Test = findViewById(R.id.Test);
        Meditacion = findViewById(R.id.meditacion);
        Chat = findViewById(R.id.ChatBot);
        Perfil = findViewById(R.id.perfil);

        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });

        Meditacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meditacion();
            }
        });

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBot();
            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfil();
            }
        });
    }

    public  void test(){
        Intent intent = new Intent(MenuActivity.this,TestActivity.class);
        startActivity(intent);
    }

    public  void meditacion(){

        Intent intent = new Intent(MenuActivity.this,MeditacionActivity.class);
        startActivity(intent);

    }

    public  void chatBot(){

        Intent intent = new Intent(MenuActivity.this,ChatActivity.class);
        startActivity(intent);
    }

    public  void perfil(){

    }
}