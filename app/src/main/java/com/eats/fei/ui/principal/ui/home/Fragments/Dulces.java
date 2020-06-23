package com.eats.fei.ui.principal.ui.home.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.slideshow.Mensaje;
import com.eats.fei.ui.principal.ui.slideshow.SlideshowViewModel;
import com.eats.fei.ui.principal.ui.slideshow.adapters.MensajeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dulces extends Fragment {
    private StorageReference dStorage;
    private ImageView fotoP;
    private DatabaseReference ddulces;
    private ListView viewlista;
    private MensajeAdapter mAdapter;
    private ArrayAdapter<String> adapter;
    private ArrayList<Mensaje> mMensajesList = new ArrayList<>();
    // private ProductoModelo mAdapter;
    private RecyclerView mRecyclerView;
    // private ArrayList<ProductoModelo> mMensajesList = new ArrayList<>();



    private SlideshowViewModel slideshowViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dulces, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewMensajes2);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(Dulces.this.getContext()));
        //Mostrar todos los productos
        ddulces = FirebaseDatabase.getInstance().getReference();
        //Query query = ddulces.child("Productos");



        //----------
        getMensajesFromFirebase();
        //--------------

        return root;


    }

    private void getMensajesFromFirebase(){
        Query query = ddulces.child("Productos").orderByChild("Categoria").equalTo("Dulces");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = Objects.requireNonNull(ds.child("Nombre Producto").getValue()).toString();
                        String precio = Objects.requireNonNull(ds.child("Precio Producto").getValue()).toString();
                        String descripcion = Objects.requireNonNull(ds.child("descripcion").getValue()).toString();

                        mMensajesList.add(new Mensaje(nombre, precio,descripcion));

                    }

                    mAdapter = new MensajeAdapter(mMensajesList, R.layout.item_producto2);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}