package com.example.shareit.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.shareit.R;
import com.example.shareit.objetos.LostOBJ;
import com.example.shareit.workers.ObtenerLODB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LostObjectsActivitylv extends AppCompatActivity {


    private ProgressDialog progreso;
    private List<LostOBJ> listaObjetos = new ArrayList<LostOBJ>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_objectslv);




        FloatingActionButton sum = findViewById(R.id.sum);
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toAddLO(getCurrentFocus());
            }

        });

        setContentView(R.layout.lost_objectslv);
        ListView tareas = (ListView) findViewById(R.id.lv1);


        AdapterLO elAdaptador = new AdapterLO(getApplicationContext(), listaObjetos);
        tareas.setAdapter(elAdaptador);

    }

    @Override
    public void onResume() {
        super.onResume();

        cargarWebService();
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Obteniendo los objetos perdiodos del servidor...");
        progreso.show();
        progreso.hide();



        Data datos = new Data.Builder()
                .putString("obtener", "todo")
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ObtenerLODB.class).setInputData(datos).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>(){
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (workInfo.getState().equals(WorkInfo.State.SUCCEEDED)) {
                                if (workInfo.getOutputData().getString("objetosperdidos").equals("error")) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "En datos devuelve error :(", Toast.LENGTH_SHORT);
                                    toast.show();
                                    progreso.hide();
                                } else if (workInfo.getOutputData().getString("objetosperdidos").equals("No hay objetos perdidos")) {

                                    Toast toast = Toast.makeText(getApplicationContext(), "No hay objetos perdidos", Toast.LENGTH_SHORT);
                                    toast.show();

                                } else{
                                    String resultados = workInfo.getOutputData().getString("objetosperdidos");
                                    String[] resultSeparados = new String[4];
                                    resultSeparados = resultados.split("],");
                                    String[] resultDesc = resultSeparados[0].split("\":");
                                    String[] resultEmail= resultSeparados[1].split("\":");
                                    String[] resultURL = resultSeparados[2].split("\":");
                                    String[] resultTit = resultSeparados[3].split("\":");

                                    StringBuilder de = new StringBuilder(resultDesc[1]);
                                    StringBuilder em = new StringBuilder(resultEmail[1]);
                                    StringBuilder ur = new StringBuilder(resultURL[1]);
                                    StringBuilder ti = new StringBuilder(resultTit[1]);

                                    de.deleteCharAt(0);
                                    de.deleteCharAt(0);
                                    resultDesc = de.toString().split("\",\"");

                                    em.deleteCharAt(0);
                                    em.deleteCharAt(0);
                                    em.deleteCharAt(em.length()-1);
                                    resultEmail = em.toString().split("\",\"");

                                    ur.deleteCharAt(0);
                                    ur.deleteCharAt(0);
                                    ur.deleteCharAt(ur.length()-1);
                                    resultURL = ur.toString().split("\",\"");

                                    ti.deleteCharAt(0);
                                    ti.deleteCharAt(0);
                                    ti.deleteCharAt(ti.length()-1);
                                    resultTit = ti.toString().split("\",\"");

                                    ListView tareas = (ListView) findViewById(R.id.lv1);
                                    listaObjetos.clear();
                                    for(int i=0;i<resultURL.length;i++){
                                        LostOBJ newOBJ = new LostOBJ(resultEmail[i], resultTit[i], resultDesc[i], resultURL[i]);
                                        listaObjetos.add(newOBJ);
                                    }
                                    AdapterLO elAdaptador = new AdapterLO(getApplicationContext(), listaObjetos);
                                    tareas.setAdapter(elAdaptador);
                                    progreso.hide();
                                    progreso.dismiss();


                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Algo ha ido mal :(", Toast.LENGTH_SHORT);
                                toast.show();
                                progreso.hide();
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);



    }


}