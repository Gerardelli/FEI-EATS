package com.eats.fei.ui.principal.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eats.fei.R;
import com.eats.fei.ui.registrar.RegistrarActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private RecyclerView recyclerViewProducto;
    private RecyclerViewAdaptador adaptadorProducto;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //Rellenar productos
        recyclerViewProducto = (RecyclerView)root.findViewById(R.id.recyclerProducto);
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(SlideshowFragment.this.getContext()));

        adaptadorProducto = new RecyclerViewAdaptador(obtenerProducto());
        recyclerViewProducto.setAdapter(adaptadorProducto);




        //Boton agregar producto
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

    public List<ProductoModelo> obtenerProducto(){
        List<ProductoModelo> producto = new ArrayList<>();
        producto.add(new ProductoModelo("Dulce 1","$10.00","Dulce muy rico",R.drawable.dulce));
        producto.add(new ProductoModelo("Dulce 2","$5.00","Dulce muy rico",R.drawable.dulce));
        producto.add(new ProductoModelo("Dulce 3","$7.00","Dulce muy rico",R.drawable.dulce));
        producto.add(new ProductoModelo("Dulce 4","$9.00","Dulce muy rico",R.drawable.dulce));

        return producto;
    }
}
