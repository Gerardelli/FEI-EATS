package com.eats.fei.ui.principal.ui.gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.eats.fei.R;
import com.eats.fei.ui.principal.PrincipalActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class EditarFoto extends AppCompatActivity {

    private final int GALLERY_INTENT = 1;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView foto;
    private Button btn20;
    private Button btn22;
    private Button btn21;
    private StorageReference dStorage;
    private ProgressDialog dProgressDialog;
    private FirebaseDatabase dDatabase;
    private FirebaseAuth firebaseAuth = null;
    private static String urlFoto ="https://firebasestorage.googleapis.com/v0/b/fei-uv.appspot.com/o/fotosPerfil%2Fuser.png?alt=media&token=a367e233-200d-407f-a693-fb30c4de3b81";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_editar_foto);
        dStorage = FirebaseStorage .getInstance().getReference();
        btn20 =(Button) findViewById (R.id.button20);
        btn21 =(Button) findViewById (R.id.button21);
        btn22 =(Button) findViewById (R.id.button22);
        dProgressDialog = new ProgressDialog (this);
        foto = findViewById (R.id.imageView20);


        //permiso para tener acceso a la cámara
        if (ContextCompat.checkSelfPermission(EditarFoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditarFoto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditarFoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }


                                                                /*SECCIÓN DE BOTONES*/
        /*--------------------------------------------------------------------------------------------------------------------------*/
        //Boton para ir a la pantalla principal
        Button btn23 = (Button) findViewById(R.id.button23);
        btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PrincipalActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Botón para abrir la galería del dispositivo
        btn20.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult (intent,GALLERY_INTENT);
            }
        });

        //Boton para eliminar la foto de perfil de usuario
        btn22.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditarFoto.this);

                // Configura el titulo.
                alertDialogBuilder.setTitle("Eliminar foto");

                // Configura el mensaje.
                alertDialogBuilder
                        .setMessage("¿Seguro que deseas eliminar la foto de perfil?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //Si la respuesta es afirmativa se elimina la foto.
                                Map<String, Object> map = new HashMap<>();
                                map.put("fotoPerfilURL", urlFoto);

                                FirebaseUser user = firebaseAuth.getInstance ().getCurrentUser();
                                dDatabase.getReference ().child("Usuarios").child(user.getUid()).updateChildren(map);

                                Toast.makeText (EditarFoto.this, "Foto de perfil eliminada", Toast.LENGTH_SHORT).show ( );
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });


        btn21.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

                                        /*MÉTODO PARA MOSTRAR LA FOTO DE PERFIL*/
    /*-----------------------------------------------------------------------------------------------------------------------------*/
        dDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getInstance ().getCurrentUser();
        dDatabase.getReference ("Usuarios").child(user.getUid ()).addValueEventListener (new ValueEventListener ( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Uri photoUrl = Uri.parse (dataSnapshot.child ("fotoPerfilURL").getValue().toString ());
                Glide.with (EditarFoto.this)
                        .load (photoUrl)
                        .fitCenter()
                        .centerCrop()
                        //Asigna el valor de lo que tiene la URL al imageView correspondiente
                        .into (foto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

                                              //Método para subir la foto
    /*-----------------------------------------------------------------------------------------------------------------------------*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            //ProgressDialog muestra que se está subiendo la imágen
            dProgressDialog.setTitle ("Subiendo foto...");
            dProgressDialog.setMessage ("Subiendo foto, espera");
            dProgressDialog.setCancelable (false);
            dProgressDialog.show ( );

            Uri uri = data.getData ( );
            StorageReference filePath = dStorage.child ("fotosPerfil").child (uri.getLastPathSegment ( ));

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
                                    map.put ("fotoPerfilURL", imageUrl);

                                    FirebaseUser user = firebaseAuth.getInstance ( ).getCurrentUser ( );
                                    dDatabase.getReference ( ).child ("Usuarios").child (user.getUid ( )).updateChildren (map);

                                    Toast.makeText (EditarFoto.this, "Foto Subida", Toast.LENGTH_SHORT).show ( );

                                }
                            });
                        }
                    }
                }
            });

        }
                                                    //Método para subir la foto con la cámara
        /*-----------------------------------------------------------------------------------------------------------------------------*/

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //ProgressDialog muestra que se está subiendo la imágen
            dProgressDialog.setTitle ("Subiendo foto...");
            dProgressDialog.setMessage ("Subiendo foto, espera");
            dProgressDialog.setCancelable (false);
            dProgressDialog.show ( );

            Uri uri = data.getData ( );
            //String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            StorageReference filePath = dStorage.child ("fotosPerfil").child (uri.getLastPathSegment ( ));

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
                                    map.put ("fotoPerfilURL", imageUrl);

                                    FirebaseUser user = firebaseAuth.getInstance ( ).getCurrentUser ( );
                                    dDatabase.getReference ( ).child ("Usuarios").child (user.getUid ( )).updateChildren (map);

                                    Toast.makeText (EditarFoto.this, "Foto Subida", Toast.LENGTH_SHORT).show ( );

                                }
                            });
                        }
                    }
                }
            });
        }

    }


}
