package com.eats.fei.ui.principal.ui.gallery;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.eats.fei.R;
import com.eats.fei.ui.principal.PrincipalActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



public class EditarFoto extends AppCompatActivity {

    private final int GALLERY_INTENT = 1;
    private ImageView foto;
    private Button btn20;
    private StorageReference dStorage;
    private ProgressDialog dProgressDialog;
    private Task<Uri> descargarfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_editar_foto);
        dStorage = FirebaseStorage .getInstance().getReference();
        btn20 =(Button) findViewById (R.id.button20);
        dProgressDialog = new ProgressDialog (this);
        foto = (ImageView) findViewById (R.id.imageView20);

        //permiso para tener acceso a la cámara
        if (ContextCompat.checkSelfPermission(EditarFoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditarFoto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditarFoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        Button btn23 = (Button) findViewById(R.id.button23);
        btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PrincipalActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        btn20.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult (intent,GALLERY_INTENT);
            }
        });
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult (requestCode,resultCode,data);

            if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
                dProgressDialog.setTitle ("Subiendo foto...");
                dProgressDialog.setMessage ("Subiendo foto, espera");
                dProgressDialog.setCancelable (false);
                dProgressDialog.show();

                Uri uri= data.getData ();
                StorageReference filePath = dStorage.child ("fotosPerfil").child (uri.getLastPathSegment ());

                filePath.putFile (uri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> ( ) {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dProgressDialog.dismiss ();
                        descargarfoto = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        Glide.with (EditarFoto.this) // .load(descargarfoto).fitCenter().centerCrop().into(logoempresa);
                                .load (descargarfoto)
                                .into (foto);
                        Toast.makeText (EditarFoto.this, "Foto Subida", Toast.LENGTH_SHORT).show ( );
                    }
                });
            }


            /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse("fotosPerfil"))
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void> () {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }
                        }
                    });*/
        }
}
