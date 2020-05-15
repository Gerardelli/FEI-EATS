package com.eats.fei.ui.registrar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eats.fei.R;
import com.eats.fei.ui.principal.PrincipalActivity;
import com.eats.fei.ui.principal.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegistrarProductoActivity extends AppCompatActivity {
    //Variables ocupadas
    private EditText NombreP;
    private EditText PrecioP;
    private EditText DescripcionP;
    private Spinner sp_categoria;
    private Button btnGuardarP;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    //VARIABLES DE FIREBASE
    FirebaseAuth mAuth; //AUTENTICACIÓN

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Productos");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_productos);

        NombreP = findViewById(R.id.etNombreP);
        PrecioP = findViewById(R.id.etPrecio);
        DescripcionP = findViewById(R.id.etDescripcionP);
        sp_categoria = findViewById(R.id.sp_categoria);
        btnGuardarP = findViewById(R.id.btnGuardarP);

        //apuntar a la base de datos y agregar referencia
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnGuardarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProducto();

            }
        });


    }
//Función para registrar los productos en la base de datos firebase
    public void registrarProducto(){

        String nombre = NombreP.getText().toString();
        String precio = PrecioP.getText().toString();
        String descripcion = DescripcionP.getText().toString();
        String categoria = sp_categoria.getSelectedItem().toString();


        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", nombre);
        map.put("Precio", precio);
        map.put("Descripción", descripcion);
        map.put("Categoría", categoria);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //Aqui obtienes la referencia de la base de datos
        FirebaseUser product = firebaseAuth.getInstance ().getCurrentUser();

        mDatabase.child("Usuarios").child(product.getUid()).setValue(map);


        /**/

        /**/

/* Este metodo lo habìa implementado, pero vi que tenia el otro, donde usa child y lo dejé arriba
--------------

        if(!TextUtils.isEmpty(nombre)){

            String id = mDatabase.push().getKey();
            Producto produ = new Producto(id,nombre,precio,descripcion,categoria);
            mDatabase.child(id).setValue(produ);

            Toast.makeText(this, "Producto Registrado", Toast.LENGTH_LONG).show();

            Intent Produ2 = new Intent(RegistrarProductoActivity.this, HomeFragment.class);
            startActivity(Produ2);


        }else{
            Toast.makeText(RegistrarProductoActivity.this,"Debe registrar un título", Toast.LENGTH_LONG).show();//Si el titulo está vacío, le solicito al usuario que lo registre
        }
----------------
termina
*/

//Otro metodo



    }

}

