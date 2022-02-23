package com.example.imageurl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText etUrl;
    Button btClear,btSubmit;
    ImageView ivResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl=findViewById(R.id.et_url);
        btClear=findViewById(R.id.bt_clear);
        btSubmit=findViewById(R.id.bt_submit);
        ivResult=findViewById(R.id.iv_result);

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUrl.setText("");
                ivResult.setImageBitmap(null);
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLink = etUrl.getText().toString();
                if (urlLink.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the Link Please!!"
                            , Toast.LENGTH_SHORT).show();
            }else {
                LoadImage loadImage = new LoadImage(ivResult);
                loadImage.execute(urlLink);
                }

            }
        });
    }
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView ivResult){
            this.imageView=ivResult;
        }

        @Override
        protected Bitmap doInBackground(String...strings){
            String urlLink= strings[0];
            Bitmap bitmap=null;
            try {
                InputStream inputStream=new java.net.URL(urlLink).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivResult.setImageBitmap(bitmap);
        }
    }
}