package com.eats.fei.ui.principal.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.gallery.GalleryViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    //Apartado para mostrar datos y conexion a base de datos
    private final int GALLERY_INTENT = 1;
    private DatabaseReference dDatabase;
    //private FirebaseAuth firebaseAuth = null;
    private GalleryViewModel galleryViewModel;
    private DatabaseReference mDatabase;




    private RecyclerView recyclerViewProducto;
    private RecyclerViewAdaptador adaptadorProducto;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //Rellenar productos
        recyclerViewProducto = root.findViewById(R.id.recyclerProducto);
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(SlideshowFragment.this.getContext()));

        adaptadorProducto = new RecyclerViewAdaptador(obtenerProducto());
        recyclerViewProducto.setAdapter(adaptadorProducto);

        //Datos a setear
        final TextView nombre = root.findViewById (R.id.txtNombre);
        final TextView precio = root.findViewById (R.id.txtPrecio);
        final TextView descripcion = root.findViewById (R.id.txtDescripcion);
        final Button btnEditar =  root.findViewById(R.id.bntEditar);
        final Button btnEliminar =  root.findViewById(R.id.btnEliminar);
        final ImageView imagen1 = root.findViewById (R.id.imgProducto);

        /*Apartado para boton   ---------------Está por agregarse*/
        /*btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SlideshowFragment.this.getContext(), EditarActivity.class));
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SlideshowFragment.this.getContext(), EliminarActivity.class));
            }
        });

         */

        /*Boton agregar producto-------------------------------------*/
        final Button registarP = root.findViewById(R.id.button3);
        //Actividad para registrar producto
        registarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SlideshowFragment.this.getContext(), RegistrarProductoActivity.class));
            }
        });


        /*Final del Apartado para los botones -----------------------*/
        //Base de datos ------------------------------
        FirebaseAuth mAuth; //AUTENTICACIÓN
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Productos");

       // dDatabase = FirebaseDatabase.getInstance();
        FirebaseUser ref1 = FirebaseAuth.getInstance().getCurrentUser();
        obtenerProducto();





        return root;
    }

    private List<ProductoModelo> obtenerProducto(){
        final List<ProductoModelo> producto = new ArrayList<>();
        //dDatabase = FirebaseDatabase.getInstance();
        //final FirebaseUser user = firebaseAuth.getInstance ().getCurrentUser();
        //String id_producto = UUID.randomUUID().toString();/*.child(id_producto)*/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dDatabase = FirebaseDatabase.getInstance().getReference("Productos");
        dDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                producto.add(new ProductoModelo("Dulce 1","$10.00","Dulce muy rico",R.drawable.dulce));

                if (dataSnapshot.exists()){

                    //producto.clear();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            //if(dDatabase.getReference("Productos").child("ID_usuario") == mDatabase.child("Usuarios").child(user.getUid())) {
                              //  String nombre = ds.child("Nombre Producto").getValue().toString();
                                //String precio = ds.child("Precio Producto").getValue().toString();
                                //String descripcion = ds.child("descripcion").getValue().toString();
                                //Uri photoUrl = Uri.parse(dataSnapshot.child("fotoProductoURL").getValue().toString());

                                //producto.add(new ProductoModelo(nombre, precio, descripcion,R.drawable.dulce));
                           // }

                        }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //producto.add(new ProductoModelo("Dulce 1","$10.00","Dulce muy rico",R.drawable.dulce));
        //producto.add(new ProductoModelo("Dulce 2","$5.00","Dulce muy rico",R.drawable.dulce));
        //producto.add(new ProductoModelo("Dulce 3","$7.00","Dulce muy rico",R.drawable.dulce));
        //producto.add(new ProductoModelo("Dulce 4","$9.00","Dulce muy rico",R.drawable.dulce));




        return producto;
    }


}
