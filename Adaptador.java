package com.example.facturasapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private Context context;
    ArrayList <Factura> biblioteca;
    int position;

    public Adaptador(Context context, ArrayList<Factura> biblioteca) {
        this.context = context;
        this.biblioteca = biblioteca;


    }

    @Override
    public int getCount() {
        return biblioteca.size();
    }

    @Override
    public Object getItem(int position) {
        return biblioteca.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Factura item = (Factura) getItem(position);
        this.position=position;

        convertView = LayoutInflater.from(context).inflate(R.layout.elemento_lista, null);
        TextView concepto = (TextView) convertView.findViewById(R.id.conceptoElemento);
        TextView fecha = (TextView) convertView.findViewById(R.id.fechaElemento);
        TextView importe = (TextView) convertView.findViewById(R.id.importeElemento);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipoElemento);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);


        concepto.setText(item.getConcepto());
        fecha.setText(item.getFecha_factura());
        importe.setText(""+item.getImporte()+" €");
        tipo.setText(item.getTipo());

        switch (item.getTipo()){
            case "Internet":
                imagen.setImageResource(R.drawable.internet);
                break;
            case "Luz":
                imagen.setImageResource(R.drawable.electricidad);
                break;
            case "Agua":
                imagen.setImageResource(R.drawable.agua);
                break;
            case "Alquiler":
                imagen.setImageResource(R.drawable.alquiler);
                break;
            case "Varios":
                imagen.setImageResource(R.drawable.varios);
                break;
        }





        return convertView;
    }

}
