package com.example.capstonepromobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
//import android.view.ScaleGestureDetector;
//import android.graphics.Matrix;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity2 extends AppCompatActivity {
    ImageView serverImageView;
    Button btn;

   // private ScaleGestureDetector scaleGestureDetector;
   // private Matrix matrix = new Matrix();
    Handler h= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        serverImageView =(ImageView)findViewById(R.id.imageView);


        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Socketconnn socon=new Socketconnn();
                socon.execute("Screenshot");
                Thread myThread = new Thread(new ServerImageThread());
                myThread.start();
            }
        });
        serverImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
                alert.setTitle("Delete Image");
                alert.setMessage("Are you sure you want to delete this image ?");
                // alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
                        serverImageView.setVisibility(View.GONE);
                        //new asyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Stop");
                        Socketconnn socon=new Socketconnn();
                        socon.execute("Stop");
                        Intent i=new Intent(MainActivity2.this,MainActivity.class);
                        startActivity(i);
                    }
                });
                alert.setNegativeButton("Cancel", null );
                alert.show();

                return true;
            }
        });
     //   scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());
    }
    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            matrix.setScale(scaleFactor, scaleFactor);
            serverImageView.setImageMatrix(matrix);
            return true;
        }
    }*/
    public class ServerImageThread implements Runnable {
      //  ServerSocket ss;
        Socket s;
        DataInputStream dis;
        int len;
        byte[] data;


        @Override
        public void run() {
            try {
             //   ss = new ServerSocket(7800);
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Listening", Toast.LENGTH_SHORT).show();
                    }
                });



                         while(true) {
                            // Toast.makeText(getApplicationContext(),"Image received", Toast.LENGTH_LONG).show();
                             s = MainActivity.ss.accept();
                             InputStream in = s.getInputStream();
                             dis = new DataInputStream(in);

                            len = dis.readInt();
                            data = new byte[len];
                             if (len > 0) {
                                     dis.readFully(data, 0, data.length);


                             }
                             h.post(new Runnable()
                             {
                                 @Override
                                 public void run() {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    serverImageView.setImageBitmap(bitmap);
                                    serverImageView.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(),"Image received", Toast.LENGTH_SHORT).show();
                                 }

                             });
                             dis.close();
                             in.close();
                             s.close();
                         }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}