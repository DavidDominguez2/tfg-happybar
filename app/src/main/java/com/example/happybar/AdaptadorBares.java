package com.example.happybar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happybar.DAO.Bar;

import java.util.ArrayList;

public class AdaptadorBares extends RecyclerView.Adapter<AdaptadorBares.BarViewHolder> {
    private ArrayList<Bar> listaBares;
    private listenersInterfaz mOnClickListener;

    public AdaptadorBares(ArrayList<Bar> listaBares, listenersInterfaz mOnClickListener) {
        this.listaBares = listaBares;
        this.mOnClickListener = mOnClickListener;
    }

    public ArrayList<Bar> getListaBares() {
        return listaBares;
    }

    public void setListaBares(ArrayList<Bar> listaBares) {
        this.listaBares = listaBares;
    }

    interface listenersInterfaz{
        void clickEnElementoCard(int pos);
    }



    @NonNull
    @Override
    public AdaptadorBares.BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new BarViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBares.BarViewHolder holder, int position) {
        Bar barFav = listaBares.get(position);
        holder.getNombreTxt().setText(barFav.getNombre());

        holder.getImagenBar().setImageResource(R.drawable.logo);

    }

    @Override
    public int getItemCount() {
        return listaBares.size();
    }

    public class BarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombreTxt;
        ImageView imagenBar;

        public BarViewHolder(@NonNull View itemView) {
            super(itemView);
            //Se utiliza este View para enlazar el XML con Java
            this.nombreTxt = itemView.findViewById(R.id.nombreBar_list);
            this.imagenBar = itemView.findViewById(R.id.imageBar);
            itemView.setOnClickListener(this);
        }

        public TextView getNombreTxt() {
            return nombreTxt;
        }

        public void setNombreTxt(TextView nombreTxt) {
            this.nombreTxt = nombreTxt;
        }

        public ImageView getImagenBar() {
            return imagenBar;
        }

        public void setImagenBar(ImageView imagenBar) {
            this.imagenBar = imagenBar;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.clickEnElementoCard(position);
        }
    }

}
