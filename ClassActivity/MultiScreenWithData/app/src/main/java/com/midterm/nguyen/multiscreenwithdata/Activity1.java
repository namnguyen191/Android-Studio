package com.midterm.nguyen.multiscreenwithdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity1 extends AppCompatActivity {
    EditText txtVal1, txtVal2;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        txtVal1 = (EditText)findViewById(R.id.editText);
        txtVal2 = (EditText)findViewById(R.id.editText2);
        addBtn = (Button)findViewById(R.id.button);

    }

    public void onAddData(View view) {
            try{
                Double v1 = Double.parseDouble(txtVal1.getText().toString());
                Double v2 = Double.parseDouble(txtVal2.getText().toString());

                Intent myIntent = new Intent(this, Activity2.class);
                myIntent.putExtra("key1", v1);
                myIntent.putExtra("key2", v2);
                startActivity(myIntent);
            } catch (Exception e){
                Toast.makeText(this,"Please Don't Leave The Fields Empty!", Toast.LENGTH_SHORT).show();
            }



    }
}
