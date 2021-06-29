package com.example.lovemind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.*;

public class MainActivity extends AppCompatActivity {
Button login,registro;
EditText userAPP, Password;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        login = findViewById(R.id.Login);
        userAPP = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        registro = findViewById(R.id.Register);

        mAuth = getInstance();

        Intent intent = getIntent();
        userAPP.setText(intent.getStringExtra("usuario"));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = userAPP.getText().toString();
                String password = Password.getText().toString();
                Log.e("entrar onClik",userAPP.getText().toString());


                if(!usuario.isEmpty() && !password.isEmpty()){
                    Log.e("entrar if",userAPP.getText().toString());
                    loginUser(usuario,password);

                }else{

                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setMessage("complete los campos por favor").setNegativeButton("reintentar",null);
                }
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(final String usuario, String password) {
        mAuth.signInWithEmailAndPassword(usuario,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            intent.putExtra("nombre",usuario);
                            startActivity(intent);

                        }else{

                            Toast.makeText(MainActivity.this,"contraseña o mail incorecctos",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}