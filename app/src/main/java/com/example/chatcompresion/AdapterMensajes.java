package com.example.chatcompresion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensaje> {

    private List<MensajeRecibir> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes( Context c ) {
        this.c = c;
    }

    public void addMensaje(MensajeRecibir m){

        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());

    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes, parent, false);

        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {

        holder.getNombre().setText(listMensaje.get(position).getNombre());

        if(listMensaje.get(position).getType_mensaje().equals("2")){

            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            holder.getFotoMensaje().setImageBitmap(listMensaje.get(position).getBitmap());

        } else if (listMensaje.get(position).getType_mensaje().equals("1")){

            holder.getMensaje().setText(listMensaje.get(position).getMensaje1());
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);

        }

        if(listMensaje.get(position).getFotoPerfil().isEmpty()){

            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);

        } else {

            Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());

        }

        Map<String, Integer> freq = listMensaje.get(position).getFreq1();

        Long codigoHora = listMensaje.get(position).getHora();

        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        holder.getHora().setText(sdf.format(d));

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

}
