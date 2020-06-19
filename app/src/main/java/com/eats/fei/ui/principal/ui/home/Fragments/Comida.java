package com.eats.fei.ui.principal.ui.home.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.slideshow.SlideshowViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Comida extends Fragment {
    private DatabaseReference ddulces;
    private ListView viewlista;
    private ArrayList<String> listastrings;
    private ArrayAdapter<String> adapter;

    public Comida() {
        // Required empty public constructor
    }


    private SlideshowViewModel slideshowViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comida, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_todos, container, false);

        //Mostrar todos los productos
        ddulces = FirebaseDatabase.getInstance().getReference();
        Query query = ddulces.child("Productos");


        viewlista = root.findViewById(R.id.listComida);
        listastrings= new ArrayList<>(0);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot produ: dataSnapshot.getChildren()){

                        String nombre = Objects.requireNonNull(produ.child("Nombre Producto").getValue()).toString();
                        String precio = Objects.requireNonNull(produ.child("Precio Producto").getValue()).toString();
                        //  String descripcion = produ.child("descripcion").getValue().toString();
                        //String categoria = produ.child("Categoria").getValue().toString();

                        listastrings.add(System.getProperty ("line.separator")+
                                "Producto:    "+ nombre + System.getProperty ("line.separator")+
                                "Precio:     $"+ precio+ System.getProperty ("line.separator"));
                        //"Descripción: "+ descripcion+ System.getProperty ("line.separator"));
                        // "Categoria:   "+ categoria+ System.getProperty ("line.separator"));

                        adapter = new ArrayAdapter<>(Objects.requireNonNull(Comida.this.getContext()), android.R.layout.simple_list_item_1, listastrings);
                        viewlista.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
}