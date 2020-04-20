package com.eats.fei.ui.registrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eats.fei.R;
import com.eats.fei.ui.login.LoginActivity;
import com.eats.fei.ui.principal.PrincipalActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    private EditText TelefonoEdt;
    private EditText CorreoEdt;
    private EditText ContrasenaEdt;
    private Button RegistrarBtn;
    private EditText Nombretext;

    //VARIABLES PARA ALMACENAR LOS DATOS
    private String telefono = "";
    private String correo = "";
    private String contrasena = "";
    private String nombre ="";

    //VARIABLES DE FIREBASE
    FirebaseAuth mAuth; //AUTENTICACIÓN
    DatabaseReference mDatabase;//USAR LA BASE DE DATOS REAL TIME


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        TelefonoEdt = (EditText) findViewById(R.id.TelefonoEdt);
        CorreoEdt = (EditText) findViewById(R.id.CorreoEdt);
        ContrasenaEdt =(EditText) findViewById(R.id.ContrasenaEdt);
        RegistrarBtn = (Button) findViewById(R.id.RegistrarBtn);
        Nombretext = (EditText) findViewById(R.id.Nombre);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        RegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se asigna los que el usuario ingresan en los EditText a las variables
                telefono = TelefonoEdt.getText().toString();
                correo = CorreoEdt.getText().toString();
                contrasena = ContrasenaEdt.getText().toString();
                nombre = Nombretext.getText().toString();

                if(!telefono.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty() && !nombre.isEmpty()){
                    if(contrasena.length()>=6 && telefono.length()==10){
                        registrarUsuario();
                    }else{
                        Toast.makeText(RegistrarActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                        Toast.makeText(RegistrarActivity.this, "El telefono debe ser de 10 dígitos", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(RegistrarActivity.this, "Completar campos vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });


        final TextView textViewLogin = findViewById(R.id.textLogin);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    private void registrarUsuario(){
        mAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("Telefono", telefono);
                    map.put("Correo", correo);
                    map.put("Contrasena", contrasena);
                    map.put("Nombre", nombre);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity( new Intent(RegistrarActivity.this, PrincipalActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistrarActivity.this, "No se crearon los datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegistrarActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
