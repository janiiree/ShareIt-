package com.example.shareit;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterWorker extends Worker {

    public RegisterWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/jbarbero004/WEB/shareit/registerUser.php";
        HttpURLConnection urlConnection;

        String email = getInputData().getString("email");
        String password = getInputData().getString("password");
        String username = getInputData().getString("username");

        // Para enviar parámetros al fichero PHP en formato JSON
        JSONObject json_params = new JSONObject();
        json_params.put("email", email);
        json_params.put("password", password);
        json_params.put("name", username);

        Log.d("prueba", username + " " + email + " " + password);

        try {
            URL dest = new URL(direccion);
            urlConnection = (HttpURLConnection) dest.openConnection();
            urlConnection.setConnectTimeout(5000);

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

            // Se mira el código de vuelta y se procesa el resultado
            if (statusCode == 200) {
                return Result.success();
            } else {
                Result.failure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
