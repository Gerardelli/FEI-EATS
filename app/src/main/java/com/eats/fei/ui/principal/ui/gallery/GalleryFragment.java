package com.eats.fei.ui.principal.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.eats.fei.R;
import com.eats.fei.ui.registrar.EditarActivity;
import com.eats.fei.ui.registrar.RegistrarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GalleryFragment extends Fragment {

    private FirebaseDatabase dDatabase;
    private FirebaseAuth firebaseAuth = null;

    private GalleryViewModel galleryViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final TextView nombre = root.findViewById (R.id.textView3);
        final TextView telefono = root.findViewById (R.id.textView14);
        final TextView correo = root.findViewById (R.id.textView13);
       final Button button2 =  root.findViewById(R.id.button2);


       /*Apartado para boton*/
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(GalleryFragment.this.getContext(), EditarActivity.class));
            }

        });
        /*Fin del apartado*/

        dDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getInstance ().getCurrentUser();
        //Obtiene el id del usuario
        dDatabase.getReference ("Usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Obtiene el valor de cada campo de un usuario
                    String nombre1 = dataSnapshot.child("Nombre").getValue().toString();
                    String telefono1 = dataSnapshot.child("Telefono").getValue().toString();
                    String correo1 = dataSnapshot.child("Correo").getValue().toString();

                    //Asigna el valor de la variable al TextView correspondiente
                    nombre.setText("Nombre: " + nombre1);
                    telefono.setText("Teléfono: " + telefono1);
                    correo.setText("Correo: " + correo1);


                }





            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        return root;


    }

}
