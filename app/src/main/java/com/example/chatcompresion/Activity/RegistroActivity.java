package com.example.chatcompresion.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatcompresion.Entidades.Usuario;
import com.example.chatcompresion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText nombre, correo, clave, claveRepetida;
    private Button botonRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = (EditText) findViewById(R.id.idRegistroNombre);
        correo = (EditText) findViewById(R.id.idRegistroCorreo);
        clave = (EditText) findViewById(R.id.idRegistroClave);
        claveRepetida = (EditText) findViewById(R.id.idRegistroClaveRepetida);

        botonRegistrar = (Button) findViewById(R.id.idRegistroRegistrar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        botonRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String cadenaCorreo = correo.getText().toString();
                String cadenaNombre = nombre.getText().toString();

                if(isValidEmail(cadenaCorreo) && validarContraseña() && validarNombre(cadenaNombre)){

                    String cadenaClave = clave.getText().toString();

                    mAuth.createUserWithEmailAndPassword(cadenaCorreo, cadenaClave)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegistroActivity.this, "Se Registro correctamente", Toast.LENGTH_SHORT).show();
                                        Usuario usuario = new Usuario();
                                        usuario.setCorreo(cadenaCorreo);
                                        usuario.setNombre(cadenaNombre);
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                        reference.setValue(usuario);

                                        finish();

                                    } else {

                                        Toast.makeText(RegistroActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                } else {

                    Toast.makeText(RegistroActivity.this, "Error en la validacion", Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    private boolean isValidEmail(CharSequence target){

        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    public boolean validarContraseña(){

        String cadenaClave, cadenaClaveRepetida;

        cadenaClave = clave.getText().toString();
        cadenaClaveRepetida = claveRepetida.getText().toString();

        if(cadenaClave.equals(cadenaClaveRepetida)){

            if(cadenaClave.length()>=6 && cadenaClave.length()<=16){

                return true;

            } else {

                return false;

            }

        } else {

            return false;

        }

    }

    public boolean validarNombre(String nombre){

        return !nombre.isEmpty();

    }

}