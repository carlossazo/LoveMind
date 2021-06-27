package com.example.lovemind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TestActivity extends AppCompatActivity {
    private String[] all_questions;
    TextView Question;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    Button btn_next;
    TextView depre_count;
    ImageView img;

    private RadioGroup group;
    private int current_question; //indice para el arreglo de preguntas
    private int correct_answer;
    private int[] answer;
    private int depression_counter;

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        img = findViewById(R.id.imageView);

        Question = findViewById(R.id.Question);
        btn_next = (Button) findViewById(R.id.btn_next);
        group =  findViewById(R.id.answer_group);
        depre_count = findViewById(R.id.depre_count);

        // guardo el arreglo creado en los recursos string
        all_questions = getResources().getStringArray(R.array.all_questions);
        current_question = 0;
        show_questions();

        //recorro las preguntas
        answer = new int[all_questions.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = -1;
        }



        // guardo el entero creado en recursos string
        final int depression_answer = getResources().getInteger(R.integer.depression_answer);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // guardo el id del radio button seleccionado
                int id = group.getCheckedRadioButtonId();
                int answer_select = -1;
                for (int i = 0; i < ids_answers.length; i++){
                    if (ids_answers[i] == id){
                        answer_select = i; // guardo la posicion de respuesta seleccionada
                    }
                }
                if (answer_select == depression_answer){
                    depression_counter += 20;
                    depre_count.setText(Integer.toString(depression_counter));
                }
                Log.i("pauek","btn_check!"); // me mostrara en consola si detecta el accionar del button
                Log.d("valor:",Integer.toString(depression_counter));

                // usamos una condicional par controlar la cantidad de veces que aumenta
                //el indice en base a las preguntas totales,podria con un <=
                if (current_question < all_questions.length -1){
                    current_question++; // pasamos a la siguiente pregunta
                    show_questions();
                }
                // desabilitamos el button si que se llego a la ultima pregunta
                if (current_question == all_questions.length){
                    btn_next.setEnabled(false);
                }


            }


        });


    }

    private void show_questions() {
        String q = all_questions[current_question]; // guardo el array de preguntas
        String[] parts = q.split(";"); // parto el array por cada ;
        group.clearCheck(); // desmarco el radiobutton, a traves del group que es el padre de los radio button

        Question.setText(parts[0]);

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            rb.setText(ans);

        }
    }

    public void offMap(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?q=psicologos+cerca+de+mi&sxsrf=ALeKk00sWggJlRyVM0wqYys8e8763DEG7w:1616269397490&gs_lcp=Cgdnd3Mtd2l6EAMYADIECCMQJzIHCAAQsQMQQzIHCAAQsQMQQzIFCAAQsQMyAggAMgUIABCxAzICCAAyAggAMgUIABCxAzIICAAQxwEQrwE6BwgAEEcQsAM6BAguECc6BAguEEM6CwgAELEDEMcBEKMCOggIABCxAxCDAToECAAQQzoHCC4QsQMQQ1CIzg9YmtsPYJ3tD2gDcAJ4AIABywaIAY4NkgEHMC42LjYtMZgBAKABAaoBB2d3cy13aXrIAQjAAQE&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiTppza0b_vAhWfF7kGHc2vCgsQ_AUoAXoECAIQAw"));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}