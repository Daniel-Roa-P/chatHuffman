package com.example.chatcompresion.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatcompresion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText correo, contaseña;
    private Button login, registro;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = (EditText) findViewById(R.id.correoLogin);
        contaseña = (EditText) findViewById(R.id.claveLogin);

        login = (Button) findViewById(R.id.botonLogin);
        registro = (Button) findViewById(R.id.botonLoginRegistro);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cadenaCorreo = correo.getText().toString();
                String cadenaClave = contaseña.getText().toString();

                if(isValidEmail(cadenaCorreo) && validarContraseña()){

                    mAuth.signInWithEmailAndPassword(cadenaCorreo, cadenaClave)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(LoginActivity.this, "Se logueo correctamente", Toast.LENGTH_SHORT).show();

                                        nextActivity();

                                    } else {

                                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                } else {

                    Toast.makeText(LoginActivity.this, "Error en la validacion", Toast.LENGTH_SHORT).show();

                }

            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));


            }
        });

    }

    private boolean isValidEmail(CharSequence target){

        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    public boolean validarContraseña(){

        String cadenaClave = contaseña.getText().toString();

            if(cadenaClave.length()>=6 && cadenaClave.length()<=16){

                return true;

            } else {

                return false;

            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            Toast.makeText(LoginActivity.this, "Usuario Logueado", Toast.LENGTH_SHORT).show();

            nextActivity();
        }

    }

    private void nextActivity(){

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();

    }

}
