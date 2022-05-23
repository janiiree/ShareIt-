package com.example.shareit.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareit.R;
import com.example.shareit.adapters.ApuntesAdapter;
import com.example.shareit.objetos.Apuntes;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class ApuntesActivity extends AppCompatActivity implements View.OnClickListener {

    //Botones
    private Button buttonChoose;
    private Button buttonUpload;

    private EditText editText;

    //  URLs
    public static final String UPLOAD_URL = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/jbarbero004/WEB/shareit/subirarchivos.php";
    public static final String PDF_FETCH_URL = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/jbarbero004/WEB/shareit/getApuntes.php";

    ImageView imageView;

    //Solicitud de imagen
    private int PICK_PDF_REQUEST = 1;

    //Permiso de almacenamiento
    private static final int STORAGE_PERMISSION_CODE = 123;



    //Uri para almacenar la imagen uri
    private Uri filePath;

    //ListView
    ListView listView;

    //Boton
    Button buttonFetch;

    //Progreso
    ProgressDialog progressDialog;

    //Array de apuntes
    ArrayList<Apuntes> apuntesList= new ArrayList<Apuntes>();

    //Adapter de apuntes
    ApuntesAdapter pdfAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestStoragePermission();

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        editText = (EditText) findViewById(R.id.editTextName);

        listView = (ListView) findViewById(R.id.listView);

        buttonFetch = (Button) findViewById(R.id.buttonFetchPdf);


        progressDialog = new ProgressDialog(this);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonFetch.setOnClickListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Apuntes apuntes = (Apuntes) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(apuntes.getUrl()));
                startActivity(intent);

            }
        });
    }

    public void uploadMultipart() {
        
        String titulo = editText.getText().toString().trim();
        String descr = editText.getText().toString().trim();
        String email = editText.getText().toString().trim();
        String url = editText.getText().toString().trim();
        String degree = editText.getText().toString().trim();
        String asignatura = editText.getText().toString().trim();

        String path = FilePath.getPath(this, filePath);

        if (path == null) {

            Toast.makeText(this, "Mueva su archivo .pdf al almacenamiento interno y vuelva a intentarlo", Toast.LENGTH_LONG).show();
        } else {
            try {
                String uploadId = UUID.randomUUID().toString();

                new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "apuntes")
                        .addParameter("titulo", titulo) 
                        .addParameter("descr", descr) 
                        .addParameter("email", email) 
                        .addParameter("url", url) 
                        .addParameter("degree", degree) 
                        .addParameter("asignatura", asignatura) 
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload();

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Elige Pdf"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Necesito el permiso, por favor.", Toast.LENGTH_LONG).show();
        }
        //Pedir el permiso
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //Se llamará a este método cuando el usuario haga clic en permitir o denegar
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido ahora puedes leer el almacenamiento", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Vaya, acabas de denegar el permiso.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadMultipart();
        }

        if(v==buttonFetch){

            getApuntes();
        }
    }

    private void getApuntes() {

        progressDialog.setMessage("Obteniendo Apuntes... Por favor espere");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(ApuntesActivity.this,obj.getString("titulo"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("apuntes");

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Apuntes apuntes  = new Apuntes();
                                String pdfName = jsonObject.getString("titulo");
                                String pdfDescr = jsonObject.getString("descripcion");
                                String pdfEmail = jsonObject.getString("email");
                                String pdfUrl = jsonObject.getString("url");
                                String pdfDegree = jsonObject.getString("degree");
                                String pdfAsignatura = jsonObject.getString("asignatura");
                                apuntes.setName(pdfName);
                                apuntes.setDescr(pdfDescr);
                                apuntes.setEmail(pdfEmail);
                                apuntes.setUrl(pdfUrl);
                                apuntes.setDegree(pdfDegree);
                                apuntes.setAsignatura(pdfAsignatura);
                                apuntesList.add(apuntes);

                            }

                            pdfAdapter=new ApuntesAdapter(ApuntesActivity.this,R.layout.list_apuntes, apuntesList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
    }
}
