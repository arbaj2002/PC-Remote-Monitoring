package com.example.capstonepromobile_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Socketconnn extends AsyncTask<String,Void,String> {
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;
    String message;
    //Context ctx;
   /* Socketconn(MainActivity context){

    }*/
    @Override
    protected String doInBackground(String... voids) {
       // this.ctx = ctx;
         message = voids[0];
        //Toast.makeText(, message, Toast.LENGTH_SHORT).show();
        String mess = null;
        try {
            s = new Socket("192.168.43.97", 7800);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
           // InputStreamReader isr = new InputStreamReader(s.getInputStream());
           // BufferedReader br = new BufferedReader(isr);
           // mess = br.readLine();
           // Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();

        /*    if (mess.equals("Connected")) {
                Intent i = new Intent(ctx, MainActivity2.class);
                // i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);
            } else {
                Toast.makeText(ctx, "NO CONNECTION with PC", Toast.LENGTH_SHORT).show();

            }*/
            s.close();
        } catch (IOException e) {

            e.printStackTrace();

        }


        //return mess;
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
