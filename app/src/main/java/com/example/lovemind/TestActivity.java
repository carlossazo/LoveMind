package com.example.lovemind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class TestActivity extends AppCompatActivity {
    private String[] all_questions;
    TextView Question;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    Button btn_next, btn_previus;
    ImageButton btn_send;
    TextView Problem;
    ImageView img;

    private RadioGroup group;
    private int current_question; //indice para el arreglo de preguntas
    private int correct_answer;
    private int[] answer;
    private int [] ansCheck; //array de respuestas seccionadas para guardarlas antes de usar el boton previus
    private  boolean [] ques_depression, ques_stress, ques_anxiety;
    private int global_counter, depression_counter, stress_counter, anxiety_counter ;

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
        btn_previus = findViewById(R.id.btn_previus);
        btn_send = findViewById(R.id.Maps);
        Problem = findViewById(R.id.problem);
        group =  findViewById(R.id.answer_group);

        // oculto el boton de maps
        btn_send.setVisibility(View.INVISIBLE);
        // oculto la sugerencia
        Problem.setVisibility(View.INVISIBLE);
        // guardo el arreglo creado en los recursos string
        all_questions = getResources().getStringArray(R.array.all_questions);


        // guardo erreglo para guardar respuestas de cierto marcador de puntaje
        ques_depression= new boolean[all_questions.length];
        ques_stress= new boolean[all_questions.length];
        ques_anxiety= new boolean[all_questions.length];
        ansCheck = new int[all_questions.length];

        //recorro las preguntas chequeadas
        for (int i = 0; i < ansCheck.length; i++){
            ansCheck[i] = -1;
        }

        current_question = 0;
        show_questions();

        //recorro las preguntas
        answer = new int[all_questions.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = -1;
        }
;


        // guardo el entero creado en recursos string
        final int depression_answer = getResources().getInteger(R.integer.depression_answer);
        final int stress_answer = getResources().getInteger(R.integer.stress_answer);
        final int anxiety_answer = getResources().getInteger(R.integer.anxiety_answer);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group.getCheckedRadioButtonId() == -1){

                    Toast.makeText(TestActivity.this,"debe seleccionar una respuesta",Toast.LENGTH_LONG).show();

                }
                else{
                    int answer_select = checkAnswer();

                   /* if (answer_select == depression_answer){
                        depression_counter += 20;
                        depre_count.setText(Integer.toString(depression_counter));
                    }else if(answer_select == stress_answer){
                        stress_counter +=20;
                    }else{
                        anxiety_counter +=20;
                    }*/
                    Log.i("pauek","btn_check!"); // me mostrara en consola si detecta el accionar del button
                    Log.d("depre :",Integer.toString(depression_counter));
                    Log.d("estres :",Integer.toString(stress_counter));
                    Log.d("ansiedad :",Integer.toString(anxiety_counter));


                    // usamos una condicional par controlar la cantidad de veces que aumenta
                    //el indice en base a las preguntas totales,podria con un <=
                    if (current_question < all_questions.length -1){
                        current_question++; // pasamos a la siguiente pregunta
                        show_questions();
                    }else{
                        // desabilitamos el button si que se llego a la ultima pregunta
                        btn_next.setText(R.string.finish);
                        btn_next.setEnabled(false);

                        for (boolean b: ques_depression){
                            if (b) depression_counter+=10;
                            if (b) global_counter+=10;

                        }
                        for (boolean b: ques_stress){
                            if (b) stress_counter+=20;
                            if (b) global_counter+=20;

                        }
                        for (boolean b: ques_anxiety){
                            if (b) anxiety_counter+=30;
                            if (b) global_counter+=30;

                        }



                        Problem.setVisibility(View.VISIBLE);
                        problem();

                        Log.d("depre :",Integer.toString(depression_counter));
                        Log.d("estre :",Integer.toString(stress_counter));
                        Log.d("ansie :",Integer.toString(anxiety_counter));
                    }

                }


            }


        });

        btn_previus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer_select = checkAnswer();
                if(current_question > 0){
                    current_question--;
                    show_questions();
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offMap(view);
            }
        });

    }
    private int checkAnswer() {
        // guardo el id del radio button seleccionado
        int id = group.getCheckedRadioButtonId();
        Log.i("id buton",Integer.toString(id));
        int answer_select = -1;
        for (int i = 0; i < ids_answers.length; i++){
            if (ids_answers[i] == id){
                answer_select = i; // guardo la posicion de respuesta seleccionada
            }
        }

        ques_anxiety[current_question] = (answer_select == 0);
        ques_stress[current_question] = (answer_select == 1);
        ques_depression[current_question] = (answer_select == 2);
        ansCheck[current_question] = answer_select;
        return answer_select;
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

            if(ansCheck[current_question] == i){
                rb.setChecked(true);
            }
        }

        if (current_question == 0){
            btn_previus.setVisibility(View.INVISIBLE);
        }else{
            btn_previus.setVisibility(View.VISIBLE);
        }

        // cambiamos el texto del button si que se llego a la ultima pregunta
        if (current_question == all_questions.length -1){
            btn_next.setText(R.string.finish);

        }else{

        }

        if(current_question == all_questions.length){
            btn_next.setEnabled(true);

        }
    }

    public void problem(){
        if(global_counter >= 100 && global_counter <= 199){
            Problem.setText(R.string.p1);

        }else
        if(global_counter >= 200 && global_counter <= 249){
            Problem.setText(R.string.p2);
            btn_send.setVisibility(View.VISIBLE);
        }else
        if(global_counter >= 250 && global_counter <= 300){
            Problem.setText(R.string.p3);
            btn_send.setVisibility(View.VISIBLE);
        }else{
            Problem.setText("error puntaje");
        }
    }

    public void offMap(View view) {
        if(global_counter >= 200 && global_counter <= 249){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?q=psicologos+cerca+de+mi&sxsrf=ALeKk00sWggJlRyVM0wqYys8e8763DEG7w:1616269397490&gs_lcp=Cgdnd3Mtd2l6EAMYADIECCMQJzIHCAAQsQMQQzIHCAAQsQMQQzIFCAAQsQMyAggAMgUIABCxAzICCAAyAggAMgUIABCxAzIICAAQxwEQrwE6BwgAEEcQsAM6BAguECc6BAguEEM6CwgAELEDEMcBEKMCOggIABCxAxCDAToECAAQQzoHCC4QsQMQQ1CIzg9YmtsPYJ3tD2gDcAJ4AIABywaIAY4NkgEHMC42LjYtMZgBAKABAaoBB2d3cy13aXrIAQjAAQE&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiTppza0b_vAhWfF7kGHc2vCgsQ_AUoAXoECAIQAw"));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }else
        if(global_counter >= 250 && global_counter <= 300){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?q=psicologos+cerca+de+mi&sxsrf=ALeKk00sWggJlRyVM0wqYys8e8763DEG7w:1616269397490&gs_lcp=Cgdnd3Mtd2l6EAMYADIECCMQJzIHCAAQsQMQQzIHCAAQsQMQQzIFCAAQsQMyAggAMgUIABCxAzICCAAyAggAMgUIABCxAzIICAAQxwEQrwE6BwgAEEcQsAM6BAguECc6BAguEEM6CwgAELEDEMcBEKMCOggIABCxAxCDAToECAAQQzoHCC4QsQMQQ1CIzg9YmtsPYJ3tD2gDcAJ4AIABywaIAY4NkgEHMC42LjYtMZgBAKABAaoBB2d3cy13aXrIAQjAAQE&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiTppza0b_vAhWfF7kGHc2vCgsQ_AUoAXoECAIQAw"));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }else{

        }
    }
}