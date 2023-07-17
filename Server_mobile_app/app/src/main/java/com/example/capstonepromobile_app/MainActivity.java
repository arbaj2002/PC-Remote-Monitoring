package com.example.capstonepromobile_app;

//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;



public class MainActivity extends User_login {
    Spinner spinner;
    static ArrayList<String> arrayList;
    ArrayAdapter <String> arrayAdapter;
    public static Integer count=0;
    public static Integer itemselected=0;
    public static String delpc;
    public static ServerSocket ss;

    {
        try {
            ss = new ServerSocket(7800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=findViewById(R.id.spinner);
        Button newpc=(Button)findViewById(R.id.btn2);
        newpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,Addpc.class);
                startActivity(i);
            }
        });
        Button connect = findViewById(R.id.btn1);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intt = getIntent();
               // String username = intt.getStringExtra("uname");
               if(delpc.equals("ASUS-KHalid")) {
                   Socketconn socon = new Socketconn();
                   //Socketconn sv=new Socketconn();
                   socon.execute(userid);
                   // socon.mess
                   // openactivity2();
               }
               else {
                   Toast.makeText(getApplicationContext(),"This PC is not in Connection",Toast.LENGTH_LONG).show();
               }
            }
        });
        if (count==0){

            arrayList = new ArrayList<>();
            arrayList.add("ASUS-KHalid");
        }
        if (count==1){
            Intent intent = getIntent();
            String intentarr = intent.getStringExtra("key");
            if(!intentarr.isEmpty()){
            arrayList.add(intentarr);
            count=2;}
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  String tutorialName= parent.getItemAtPosition(position).toString();
                                                  Toast.makeText(parent.getContext(),"Selected: "+tutorialName, Toast.LENGTH_SHORT).show();
                                                  itemselected=position;
                                                  delpc=tutorialName;
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          });
       /* spinner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,  int i, long l) {
                //String tutorialName= adapterView.getItemAtPosition(i).toString();
               // Toast.makeText(adapterView.getContext(),"Selected: "+tutorialName, Toast.LENGTH_LONG).show();
                final int Which_item=i;
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("are you sure");
                alert.setMessage("Are you sure you want to delete this?");
               // alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
                        arrayList.remove(Which_item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Cancel", null );
                alert.show();



                return true;

            }
        });*/

/*
        final Handler actionHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Long click performed", Toast.LENGTH_SHORT).show();
            }
        };*/

       /* spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                   // actionHandler.postDelayed(runnable, 1000);
                   // Toast.makeText(MainActivity.this, "Long click performed", Toast.LENGTH_SHORT).show();

                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    //actionHandler.removeCallbacks(runnable);
                    Toast.makeText(MainActivity.this, "Long click performed", Toast.LENGTH_SHORT).show();
                }
                return false;
            }


        });*/

        spinner.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                //System.out.println(Spinner.getSelectedItem().toString() + " is long clicked");
                final int Which_item=itemselected;
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete PC");
                alert.setMessage("Are you sure you want to delete the pc "+delpc+" ?");
                // alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
                        arrayList.remove(Which_item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Cancel", null );
                alert.show();

                return true;
            }
        });

    }

    /*public void openactivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }*/
  public class Socketconn extends AsyncTask<String,Void,Void> {
      Socket ss;
      DataOutputStream dos;
      PrintWriter pw;
      //Context ctx;
   /*   Socketconn(MainActivity context){

      }*/
      @Override
      protected Void doInBackground(String... voids) {
          // this.ctx = ctx;
          String message = voids[0];
         // String mess = "nn";
          try {

              ss = new Socket("192.168.43.97", 7800);

              //s.setSoTimeout(10000);
              pw = new PrintWriter(ss.getOutputStream());
              pw.write(message);
              pw.flush();
              pw.close();
              Intent i = new Intent(MainActivity.this, MainActivity2.class);
              // i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
               startActivity(i);
             // InputStreamReader isr = new InputStreamReader(s.getInputStream());
             // BufferedReader br = new BufferedReader(isr);
            //  Toast.makeText(MainActivity.this, mess, Toast.LENGTH_SHORT).show();

             // mess = br.readLine();
             // Toast.makeText(MainActivity.this, mess, Toast.LENGTH_SHORT).show();

           //if (mess.equals("Connected")) {
               // Intent i = new Intent(MainActivity.this, MainActivity2.class);
                // i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
               // startActivity(i);
            //} else {
              //  Toast.makeText(MainActivity.this, "NO CONNECTION with PC", Toast.LENGTH_LONG).show();

            //}
              ss.close();
          } catch(ConnectException son){
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      Toast.makeText(MainActivity.this, "NO CONNECTION with PC", Toast.LENGTH_LONG).show();
                    //  Intent i = new Intent(MainActivity.this, MainActivity2.class);
                      // i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                     // startActivity(i);

                  }
              });

          } catch (IOException e) {
            /*  runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      Toast.makeText(MainActivity.this, "NO CONNECTION with PC", Toast.LENGTH_LONG).show();
                  }
              });*/
              //Toast.makeText(MainActivity.this, "NO CONNECTION with PC", Toast.LENGTH_LONG).show();
              e.printStackTrace();
              //Intent i = new Intent(MainActivity.this, MainActivity2.class);
              // i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
              //startActivity(i);
          }
          return null;
      }

 /*  @Override
    protected void onPostExecute(String ss) {
        //super.onPostExecute(ss);
        if (ss.equals("Connected")) {
            Intent i = new Intent(ctx, MainActivity2.class);
            // i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);

        } else {
            Toast.makeText(ctx, "NO CONNECTION with PC", Toast.LENGTH_SHORT).show();

        }
    }*/
  }

    /* @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = getIntent();
        String intentarr=intent.getStringExtra("key");

    }*/
}