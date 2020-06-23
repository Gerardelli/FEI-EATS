package com.eats.fei.ui.principal.ui.slideshow.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.slideshow.Mensaje;

import java.util.ArrayList;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Mensaje> mensajesList;

    public MensajeAdapter(ArrayList<Mensaje>mensajesList, int resource){
        this.mensajesList = mensajesList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        Mensaje mensaje = mensajesList.get(index);

        viewHolder.textViewMensaje.setText(mensaje.getNombre());
        viewHolder.textViewMensaje2.setText(mensaje.getPrecio());
       viewHolder.textViewMensaje3.setText(mensaje.getDescripcion());

        //StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");
        //Glide.with(viewHolder.view).load(mensaje.getUrl()).into(imageView);
       // Glide.with(viewHolder.view).load("https://console.firebase.google.com/project/fei-uv/storage/fei-uv.appspot.com/files").into(viewHolder.Imagen);

    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewMensaje;
        private TextView textViewMensaje2;
       private TextView textViewMensaje3;
        //private ImageView Imagen;
        public View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            this.textViewMensaje = (TextView) view.findViewById(R.id.textViewMensaje);
            this.textViewMensaje2 = (TextView) view.findViewById(R.id.textViewMensaje2);
           this.textViewMensaje3 = (TextView) view.findViewById(R.id.textViewMensaje3);
           // this.Imagen =view.findViewById(R.id.imgProducto);

        }

    }
}

