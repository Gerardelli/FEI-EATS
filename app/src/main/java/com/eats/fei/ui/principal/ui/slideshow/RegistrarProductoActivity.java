package com.eats.fei.ui.principal.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eats.fei.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistrarProductoActivity extends AppCompatActivity {
    //VARIABLES DE ENLACE INTERFAZ Y LOGICA
    private EditText nombreProducto, precioProducto, descripcionProducto;
    private Spinner categoriaProducto;
    private Button registarProducto;
    private Button btnFotoP;
    private final int GALLERY_INTENT = 1;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView fotoP;

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
    private StorageReference dStorage;

    private FirebaseUser user;
    String fotou;

    //private ProgressDialog dProgressDialog;
    //private FirebaseDatabase dDatabase;
    private FirebaseAuth firebaseAuth = null;
    private ProgressDialog dProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        nombreProducto = (EditText) findViewById(R.id.nombreProducto);
        precioProducto = (EditText) findViewById(R.id.precioProducto);
        descripcionProducto = (EditText) findViewById(R.id.descripcionProducto);
        categoriaProducto = (Spinner) findViewById(R.id.categoriaProducto);
        registarProducto = (Button) findViewById(R.id.registrarProducto);
        btnFotoP =(Button) findViewById (R.id.button);
        fotoP = findViewById (R.id.imageView5);

        dDatabase = FirebaseDatabase.getInstance();
        dReference = FirebaseDatabase.getInstance().getReference();
        dStorage = FirebaseStorage.getInstance().getReference();

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

        btnFotoP.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult (intent,GALLERY_INTENT);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData ( );
            StorageReference filePath = dStorage.child ("fotosProducto").child (uri.getLastPathSegment ( ));

            filePath.putFile (uri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata ( ) != null) {
                        if (taskSnapshot.getMetadata ( ).getReference ( ) != null) {
                            Task<Uri> result = taskSnapshot.getStorage ( ).getDownloadUrl ( );
                            result.addOnSuccessListener (new OnSuccessListener<Uri> ( ) {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String imageUrl = uri.toString ( );
                                    fotou = uri.toString();
                                    Glide.with (RegistrarProductoActivity.this)
                                            .load (imageUrl)
                                            .fitCenter()
                                            .centerCrop()
                                            //Asigna el valor de lo que tiene la URL al imageView correspondiente
                                            .into (fotoP);
                                    Toast.makeText (RegistrarProductoActivity.this, "Foto Subida", Toast.LENGTH_SHORT).show ( );

                                }
                            });
                        }
                    }
                }
            });

        }


        /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //ProgressDialog muestra que se está subiendo la imágen
            dProgressDialog.setTitle ("Subiendo foto...");
            dProgressDialog.setMessage ("Subiendo foto, espera");
            dProgressDialog.setCancelable (false);
            dProgressDialog.show ( );

            Uri uri = data.getData ( );
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            StorageReference filePath = dStorage.child ("fotoProductoURL/"+timeStamp+".jpg").child (uri.getLastPathSegment ( ));

            filePath.putFile (uri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> ( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata ( ) != null) {
                        if (taskSnapshot.getMetadata ( ).getReference ( ) != null) {
                            Task<Uri> result = taskSnapshot.getStorage ( ).getDownloadUrl ( );
                            result.addOnSuccessListener (new OnSuccessListener<Uri> ( ) {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dProgressDialog.dismiss ( );
                                    String imageUrl = uri.toString ( );

                                    Map<String, Object> map = new HashMap<> ( );
                                    map.put ("fotoProductoURL", imageUrl);

                                    FirebaseUser user = firebaseAuth.getInstance ( ).getCurrentUser ( );
                                    dDatabase.getReference ( ).child ("Usuarios").child (user.getUid ( )).updateChildren (map);

                                    Toast.makeText (RegistrarProductoActivity.this, "Foto Subida", Toast.LENGTH_SHORT).show ( );

                                }
                            });
                        }
                    }
                }
            });
        }*/

    }

    public void guardarProducto(){
        String id_producto = UUID.randomUUID().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("ID_usuario", id_usaurio);
        map.put("Nombre Producto", nombreP);
        map.put("Precio Producto", precioP);
        map.put("descripcion", descripcionP);
        map.put("Categoria", categoriaP);
        map.put("fotoProductoURL", fotou);


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

