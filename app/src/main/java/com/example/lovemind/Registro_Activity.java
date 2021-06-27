package com.example.lovemind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro_Activity extends AppCompatActivity {
    TextView userAPP, Password, name, birthday;
    Button registro;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        userAPP = findViewById(R.id.user);
        Password = findViewById(R.id.Password);
        registro = findViewById(R.id.registro);
        name = findViewById(R.id.name);
        birthday = findViewById(R.id.birthday);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = userAPP.getText().toString();
                String password = Password.getText().toString();
                String name = name.getText().toString();
                String birthday = birthday.getText().toString();

                if(!usuario.isEmpty() && !password.isEmpty() && !name.isEmpty() && !birthday.isEmpty()) {
                    Log.e("entrar if", userAPP.getText().toString());

                    if (password.length() >= 6) {

                        userRegister(usuario, password,name,birthday);
                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro_Activity.this);
                        alerta.setMessage("la contraseña debe tener mas de 5 caracteres").setNegativeButton("reintentar", null).show();
                    }

                }else{

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro_Activity.this);
                    alerta.setMessage("complete todos los campos").setNegativeButton("reintentar",null).show();
                }

            }
        });
    }
    public void userRegister(final String usuario, final String password, final String name, final String birthday) {


        mAuth.createUserWithEmailAndPassword(usuario, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Log.e("entrar task", usuario.toString());
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", usuario);
                    map.put("password", password);
                    map.put("name", name);
                    map.put("birthday", birthday);

                    // obtenemos el id del suario recien registrado
                    String id = mAuth.getCurrentUser().getUid();

                    // validacion si se han creado nuevos datos en la BDD
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()) {
                                Toast.makeText(Registro_Activity.this, "registro exitoso", Toast.LENGTH_SHORT).show();

                                login(usuario);
                            }


                        }
                    });
                } else {
                    Toast.makeText(Registro_Activity.this, "no se pudo registrar usuario", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }

    public void login(String usuario){
        Intent intent = new Intent(Registro_Activity.this,loginActivity.class);

        intent.putExtra("usuario",usuario);
        startActivity(intent);
    }
}