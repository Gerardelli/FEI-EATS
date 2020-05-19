package com.eats.fei.ui.principal.ui.slideshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eats.fei.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistrarProductoActivity extends AppCompatActivity {
    //VARIABLES DE ENLACE INTERFAZ Y LOGICA
    private EditText nombreProducto, precioProducto, descripcionProducto;
    private Spinner categoriaProducto;
    private Button registarProducto;

    //VARIABLES PARA ALMACENAR LOS DATOS
    private String nombreP = "";
    private String precioP = "";
    private String descripcionP = "";
    private String categoriaP ="";
    private String id_usaurio="";


    //VARIABLES DE FIREBASE
    private FirebaseDatabase dDatabase;
    private DatabaseReference dReference;
    private FirebaseAuth mAuth = null; //AUTENTICACIÓN

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        nombreProducto = (EditText) findViewById(R.id.nombreProducto);
        precioProducto = (EditText) findViewById(R.id.precioProducto);
        descripcionProducto = (EditText) findViewById(R.id.descripcionProducto);
        categoriaProducto = (Spinner) findViewById(R.id.categoriaProducto);
        registarProducto = (Button) findViewById(R.id.registrarProducto);

        dDatabase = FirebaseDatabase.getInstance();
        dReference = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getInstance ().getCurrentUser();

        registarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreP = nombreProducto.getText().toString();
                precioP = precioProducto.getText().toString();
                descripcionP = descripcionProducto.getText().toString();
                categoriaP = categoriaProducto.getSelectedItem().toString();
                id_usaurio = dDatabase.getReference ("Usuarios").child(user.getUid()).toString();

                if(nombreP.isEmpty() || precioP.isEmpty() || descripcionP.isEmpty()){
                    validarDatos();

                }else {
                    guardarProducto();
                    limpiarDatos();
                    Toast.makeText(RegistrarProductoActivity.this, "Producto Registrado", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void guardarProducto(){
        String id_producto = UUID.randomUUID().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("ID_usuario", id_usaurio);
        map.put("Nombre Producto", nombreP);
        map.put("Precio Producto", precioP);
        map.put("descripcion", descripcionP);
        map.put("Categoria", categoriaP);

        dReference.child("Productos").child(id_producto).setValue(map);
    }

    public void limpiarDatos(){
        nombreProducto.setText("");
        precioProducto.setText("");
        descripcionProducto.setText("");
    }

    public void validarDatos(){
        if(nombreP.isEmpty()){
            nombreProducto.setError("Requerido");
        }

        if(precioP.isEmpty()){
            precioProducto.setError("Requerido");
        }

        if(descripcionP.isEmpty()){
            descripcionProducto.setError("Requerido");
        }
    }
}
