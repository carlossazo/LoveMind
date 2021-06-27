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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    TextView userAPP, Password;
    Button ingresar, registro;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAPP = findViewById(R.id.user);
        Password = findViewById(R.id.Password);
        ingresar = findViewById(R.id.ingresar);
        registro = findViewById(R.id.registro);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        userAPP.setText(intent.getStringExtra("usuario"));
        ingresar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String usuario = userAPP.getText().toString();
                String password = Password.getText().toString();
                Log.e("entrar onClik",userAPP.getText().toString());


                if(!usuario.isEmpty() && !password.isEmpty()){
                    Log.e("entrar if",userAPP.getText().toString());
                    loginUser(usuario,password);

                }else{

                    AlertDialog.Builder alerta = new AlertDialog.Builder(loginActivity.this);
                    alerta.setMessage("complete los campos por favor").setNegativeButton("reintentar",null);
                }
            }


        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, Registro_Activity.class);
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

                            Intent intent = new Intent(loginActivity.this, BienvenidoActivity.class);
                            intent.putExtra("nombre",usuario);
                            startActivity(intent);

                        }else{

                            Toast.makeText(loginActivity.this,"contraseña o mail incorecctos",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}