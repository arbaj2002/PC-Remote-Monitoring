package com.example.capstonepromobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class Addpc extends MainActivity {
    @Override

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpc);
        final EditText pname =(EditText)findViewById(R.id.Etpname);
        final EditText ipadd =(EditText)findViewById(R.id.Etipaddr);
        final Button addpc = (Button)findViewById(R.id.button2);
        addpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pname.getText().toString().equals("") && !ipadd.getText().toString().equals("")) {

                    String pc = pname.getText().toString();
                    count = 1;
                    Intent intent = new Intent(Addpc.this, MainActivity.class);
                    intent.putExtra("key", pc);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter The Details",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
