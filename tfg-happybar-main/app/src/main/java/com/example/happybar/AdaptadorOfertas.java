package com.example.happybar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happybar.DAO.Bar;
import com.example.happybar.DAO.Oferta;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorOfertas extends RecyclerView.Adapter<AdaptadorOfertas.OfertaViewHolder> {
    private ArrayList<Oferta> listaOfertas;
    private listenersInterfaz mOnClickListener;

    public AdaptadorOfertas(ArrayList<Oferta> listaOfertas, listenersInterfaz mOnClickListener) {
        this.listaOfertas = listaOfertas;
        this.mOnClickListener = mOnClickListener;
    }

    public ArrayList<Oferta> getListaOfertas() {
        return listaOfertas;
    }

    public void setListaOfertas(ArrayList<Oferta> listaOfertas) {
        this.listaOfertas = listaOfertas;
    }

    interface listenersInterfaz{
        void clickEnElementoCard(int pos);
    }



    @NonNull
    @Override
    public AdaptadorOfertas.OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oferta, parent, false);
        return new OfertaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull OfertaViewHolder holder, int position) {
        Oferta oferta = listaOfertas.get(position);

        holder.getPrecio().setText(oferta.getPrecio());
        holder.getTitle().setText(oferta.getNombre());
        holder.getDescripcion().setText(oferta.getDescripcion());

        if(oferta.getImg().equalsIgnoreCase("")){
            holder.getImagenOferta().setImageResource(R.drawable.logo);
        }else {
            Picasso.get().load(oferta.getImg()).into(holder.getImagenOferta());
        }

    }

    @Override
    public int getItemCount() {
        return listaOfertas.size();
    }

    public class OfertaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView precio, title, descripcion;
        ImageView imagenOferta;

        public OfertaViewHolder(@NonNull View itemView) {
            super(itemView);
            //Se utiliza este View para enlazar el XML con Java

            this.precio = itemView.findViewById(R.id.itemPrecio);
            this.title = itemView.findViewById(R.id.itemTitle);
            this.descripcion = itemView.findViewById(R.id.itemDescription);
            this.imagenOferta = itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);
        }

        public TextView getPrecio() {
            return precio;
        }

        public void setPrecio(TextView precio) {
            this.precio = precio;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(TextView descripcion) {
            this.descripcion = descripcion;
        }

        public ImageView getImagenOferta() {
            return imagenOferta;
        }

        public void setImagenOferta(ImageView imagenOferta) {
            this.imagenOferta = imagenOferta;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.clickEnElementoCard(position);
        }
    }

}
