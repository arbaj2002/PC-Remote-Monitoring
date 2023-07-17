package com.example.capstonepromobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User_login extends AppCompatActivity {
    public Button button;
    public EditText ed1,ed2;
    public static String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        button = (Button) findViewById(R.id.button2);
        ed1= findViewById(R.id.editTextTextPersonName);

        ed2=findViewById(R.id.editTextTextPassword);
        String users[]={"Arbaj","Khalid","Habib","Rehan"};
        final List<String> userlist=new ArrayList<>(Arrays.asList(users));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userlist.contains(ed1.getText().toString()) && ed2.getText().toString().equals("admin")) {



                        Intent intent = new Intent(User_login.this, MainActivity.class);
                        userid = ed1.getText().toString();
                        //intent.putExtra("uname", str);
                        startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter correct id password",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}
