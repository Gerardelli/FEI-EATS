package com.eats.fei.ui.principal.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.eats.fei.R;
import com.eats.fei.ui.login.LoginActivity;
import com.eats.fei.ui.registrar.EditarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class GalleryFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    private final int GALLERY_INTENT = 1;
    private FirebaseDatabase dDatabase;
    private FirebaseAuth firebaseAuth = null;
    private GalleryViewModel galleryViewModel;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final TextView nombre = root.findViewById (R.id.textView3);
        final TextView telefono = root.findViewById (R.id.textView14);
        final TextView correo = root.findViewById (R.id.textView13);
        final Button button2 =  root.findViewById(R.id.button2);
        final Button button3 =  root.findViewById(R.id.button3);
        final ImageView image4 = root.findViewById (R.id.imageView4);
        final Button eliminarUsuario = root.findViewById(R.id.btnEliminar);

       /*Apartado para boton*/
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(GalleryFragment.this.getContext(), EditarActivity.class));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryFragment.this.getContext(), EditarFoto.class));
            }
        });

        eliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });


        dDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        //Obtiene el id del usuario

        dDatabase.getReference ("Usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Obtiene el valor de cada campo de un usuario
                    String nombre1 = dataSnapshot.child("Nombre").getValue().toString();
                    String telefono1 = dataSnapshot.child("Telefono").getValue().toString();
                    String correo1 = dataSnapshot.child("Correo").getValue().toString();
                    Uri photoUrl = Uri.parse (dataSnapshot.child ("fotoPerfilURL").getValue().toString ());

                    //Asigna el valor de la variable al TextView correspondiente
                    nombre.setText("Nombre: " + nombre1);
                    telefono.setText("Teléfono: " + telefono1);
                    correo.setText("Correo: " + correo1);
                    //Uso librería Glide para poder mostrar la foto
                    Glide.with (getActivity ())
                            .load (photoUrl)
                            .fitCenter()
                            .centerCrop()
                            //Asigna el valor de lo que tiene la URL al imageView correspondiente
                            .into (image4);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return root;
    }



    public void deleteUser() {
        mAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // Configura el titulo.
        alertDialogBuilder.setTitle("¿Deseas eliminar tu perfil?");

        // Configura el mensaje.
        alertDialogBuilder
                .setMessage("Al dar click en si, ya no tendrás acceso a tu cuenta y perderas todos tus productos ")
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Si la respuesta es afirmativa se procede a eliminar la autenticación
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        // Obtiene credenciales de autenticación del usuario para volver a autenticarse.
                        AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");

                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        user.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User account deleted.");
                                                            mAuth.signOut();
                                                            startActivity(new Intent(GalleryFragment.this.getContext(), LoginActivity.class));
                                                        }
                                                    }
                                                });

                                    }
                                });
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }


//menú popup en posible versión futura
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult (intent,GALLERY_INTENT);
                return true;
            case R.id.item2:
                Toast.makeText(getActivity (), "Item 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(getActivity (), "Item 3 clicked", Toast.LENGTH_SHORT).show();
                return true;
            default: return false;
        }
    }

}