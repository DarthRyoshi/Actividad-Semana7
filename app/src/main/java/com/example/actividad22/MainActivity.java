package com.example.actividad22;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        mImageView = findViewById(R.id.imageView);
        downloadButton = findViewById(R.id.downloadButton);

        // Configurar el botón de descarga para iniciar la tarea AsyncTask
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImageTask().execute("https://assets1.ignimgs.com/2018/03/13/assassinscreedorigins-pharaohs-1280-1520978099431_160w.jpg?width=1280"); // URL de ejemplo
            }
        });
    }

    // Método para descargar la imagen desde la URL
    private Bitmap loadImageFromNetwork(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // AsyncTask para descargar la imagen en segundo plano
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                mImageView.setImageBitmap(result);
                Toast.makeText(MainActivity.this, "Imagen descargada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error al descargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
