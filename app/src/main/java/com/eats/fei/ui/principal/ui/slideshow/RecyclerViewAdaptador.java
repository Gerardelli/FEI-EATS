package com.eats.fei.ui.principal.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eats.fei.R;
import com.eats.fei.ui.registrar.Producto;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    static class ViewHolder<imageView> extends RecyclerView.ViewHolder{
        private TextView txtNombre, txtPrecio, txtDescripcion;
        ImageView fotoProducto;
        //botones


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.txtNombre);
            txtPrecio=itemView.findViewById(R.id.txtPrecio);
            txtDescripcion=itemView.findViewById(R.id.txtDescripcion);
            fotoProducto= itemView.findViewById(R.id.imgProducto);


        }
    }

    private List<ProductoModelo> productoLista;
//Se cambiò el modo de paso de paràmetros, de pùblico a privado
    RecyclerViewAdaptador(List<ProductoModelo> productoLista) {
        this.productoLista = productoLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*holder.txtNombre.setText(productoLista.get(position).getNombre());
        holder.txtPrecio.setText(productoLista.get(position).getPrecio());
        holder.txtDescripcion.setText(productoLista.get(position).getDescripcion());

        holder.fotoProducto.setImageResource(productoLista.get(position).getImgProducto());


         */

        ProductoModelo producto = productoLista.get(position);
        holder.txtNombre.setText(producto.getNombre());
        holder.txtPrecio.setText(producto.getPrecio());
        holder.txtDescripcion.setText(producto.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return productoLista.size();
    }

    //Acción para botones,

}
