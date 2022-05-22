package com.example.shareit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.shareit.R;
import com.example.shareit.objetos.LostOBJ;
import com.example.shareit.objetos.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MenuDelDiaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudeldia);


        FloatingActionButton sum = findViewById(R.id.sum);
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toAddLO(getCurrentFocus());
            }

        });

        setContentView(R.layout.lost_objectslv);
        ListView tareas = (ListView) findViewById(R.id.lv1);


        Adapters.AdapterLO elAdaptador = new Adapters.AdapterLO(getApplicationContext(), listaObjetos);
        tareas.setAdapter(elAdaptador);

    }

    @Override
    public void onResume() {
        super.onResume();

        cargarImagenWebService();
    }

    private void cargarImagenWebService(View view) {

        Context contexto;
        contexto = getApplicationContext();

        String urlImagen="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/jbarbero004/WEB/shareit/menu/menu.jpg";
        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ImageView ivFoto= (ImageView) view.findViewById(R.id.imageView);
                ivFoto.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(contexto.getApplicationContext(),"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(contexto).addToRequestQueue(imageRequest);
    }

}
