package com.example.john.crud;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10-07-2016.
 */
public class CreateActivity extends AppCompatActivity implements OnClickListener {

    //declaracion de botones
    Button btnInsert;
    Button btnBack;
    EditText txtUser;
    EditText txtPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        //mensaje de bienvenida
        Toast t = Toast.makeText(this,"Bienvenido a la vista Crear",Toast.LENGTH_SHORT);
        t.show();

        //inicializar vistas
        btnInsert = (Button)findViewById(R.id.btnIn);
        btnBack = (Button)findViewById(R.id.btnBackCreate);
        txtUser = (EditText)findViewById(R.id.txtUser);
        txtPass = (EditText)findViewById(R.id.txtPass);

        //setear para onClickListener
        btnInsert.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    //METODO DE INTERFAZ ONCLICKLISTENER
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //boton insertar
            case R.id.btnIn:
                    new Insertar(CreateActivity.this).execute();
                break;
            //boton volver
            case R.id.btnBackCreate:

                break;
        }
    }

    //insertar los datos en el servidor
    private boolean insertar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePair;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://192.168.1.38/AndroidConnection/Controller/insert.php");
        //añadimos nuestros datos
        nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("username",txtUser.getText().toString().trim()));
        nameValuePair.add(new BasicNameValuePair("password",txtPass.getText().toString().trim()));
        try{
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            httpClient.execute(httpPost);
            return true;
        } catch (UnsupportedEncodingException e) {
            Log.e("*****ERROR: ",e.toString()+"*****");
        } catch (ClientProtocolException e) {
            Log.e("*****ERROR: ",e.toString()+"*****");
        } catch (IOException e) {
            Log.e("*****ERROR: ",e.toString()+"*****");
        } catch (Exception e){
            Log.e("*****ERROR: ",e.toString()+"*****");
        }
        return false;
    }

    //clase interna dentro de la Activity
    class Insertar extends AsyncTask<String,String,String>{

        private Activity context;

        Insertar(Activity context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            if(insertar()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Usuario insertado", Toast.LENGTH_SHORT).show();
                        txtUser.setText("");
                        txtPass.setText("");
                    }
                });
            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error de insercion", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }
}
