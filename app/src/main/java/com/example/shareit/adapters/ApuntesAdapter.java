package com.example.shareit.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shareit.R;
import com.example.shareit.activity.ApuntesActivity;
import com.example.shareit.objetos.Apuntes;

import java.util.ArrayList;

public class ApuntesAdapter extends ArrayAdapter<Apuntes> {
    Activity activity;
    int layoutResourceId;
    ArrayList<Apuntes> data=new ArrayList<Apuntes>();
    Apuntes apuntes;

    public ApuntesAdapter(Activity activity, int layoutResourceId, ArrayList<Apuntes> data) {
        super(activity, layoutResourceId, data);
        this.activity=activity;
        this.layoutResourceId=layoutResourceId;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        PdfHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater=LayoutInflater.from(activity);
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new PdfHolder();
            holder.textViewName= (TextView) row.findViewById(R.id.textViewName);
            holder.textViewUrl= (TextView) row.findViewById(R.id.textViewUrl);
            row.setTag(holder);
        }
        else
        {
            holder= (PdfHolder) row.getTag();
        }

        apuntes = data.get(position);
        holder.textViewName.setText(apuntes.getName());
        holder.textViewUrl.setText(apuntes.getUrl());
        return row;
    }

    class PdfHolder
    {
        TextView textViewName,textViewUrl;
    }
}