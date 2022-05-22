package com.example.shareit.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CheckUserLoginWorker extends Worker {

    public CheckUserLoginWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/jbarbero004/WEB/shareit/checkUserLogin.php";
        HttpURLConnection urlConnection;

        String email = getInputData().getString("email");
        String password = getInputData().getString("password");

        // Para enviar parámetros al fichero PHP en formato JSON
        JSONObject json_params = new JSONObject();
        json_params.put("email", email);
        json_params.put("password", password);

        Log.d("kkkkkkkkkkkkkkkkkkkkkkk", email + " " + password);

        try {
            URL dest = new URL(direccion);
            urlConnection = (HttpURLConnection) dest.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            // Configurar el objeto HttpURLConnection para indicar que se envían parámetros (en formato JSON)
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            // Convertir el JSON a String
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(json_params);
            out.close();

            // Se ejecuta la llamada al servicio web
            int statusCode = urlConnection.getResponseCode();
            Log.d("STATUS", String.valueOf(statusCode));
            // Se mira el código de vuelta y se procesa el resultado
            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();

                Log.d("PRUEBA", result.toString());
                // Se extrae la información del resultado
                JSONArray jsonArray = new JSONArray(result);
                ArrayList<String> nameList = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String nombre = jsonArray.getJSONObject(i).getString("name");
                    nameList.add(nombre);
                }

                Log.d("prueba", nameList.toString());

                // Para enviar parámetros de vuelta a la actividad
                Data data = new Data.Builder()
                        .putString("name", nameList.get(0).toString())
                        .build();
                return Result.success(data);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
