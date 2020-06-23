package com.eats.fei.ui.principal.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.gallery.GalleryViewModel;
import com.eats.fei.ui.principal.ui.home.Fragments.Frituras;
import com.eats.fei.ui.principal.ui.slideshow.adapters.MensajeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlideshowFragment extends Fragment {

    //Apartado para mostrar datos y conexion a base de datos
    private final int GALLERY_INTENT = 1;

    private GalleryViewModel galleryViewModel;
    private DatabaseReference mDatabase;

    //Nueva relacion de seteo
    private DatabaseReference ddulces;
    private ListView viewlista;
    //private ArrayList<String> listastrings;
   // private ArrayAdapter<String> adapter;

//-----------------
private StorageReference dStorage;

    private MensajeAdapter mAdapter;

    private ArrayList<Mensaje> mMensajesList = new ArrayList<>();
    // private ProductoModelo mAdapter;
    private RecyclerView mRecyclerView;
    // private ArrayList<ProductoModelo> mMensajesList = new ArrayList<>();



    private SlideshowViewModel slideshowViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //---------------------------------------------------------------
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewMensajes5);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(SlideshowFragment.this.getContext()));
        //Mostrar todos los productos
        ddulces = FirebaseDatabase.getInstance().getReference();




        //----------
        getMensajesFromFirebase();
        //---------------------------------------------------------------

        /*Boton agregar producto-------------------------------------*/
        final Button registarP = root.findViewById(R.id.button3);
        //Actividad para registrar producto
        registarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SlideshowFragment.this.getContext(), RegistrarProductoActivity.class));
            }
        });




        return root;
    }
    private void getMensajesFromFirebase(){
        ddulces.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = Objects.requireNonNull(ds.child("Nombre Producto").getValue()).toString();
                        String precio = Objects.requireNonNull(ds.child("Precio Producto").getValue()).toString();
                        String descripcion = Objects.requireNonNull(ds.child("descripcion").getValue()).toString();

                        mMensajesList.add(new Mensaje(nombre, precio, descripcion));

                    }

                    mAdapter = new MensajeAdapter(mMensajesList, R.layout.item_producto);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
