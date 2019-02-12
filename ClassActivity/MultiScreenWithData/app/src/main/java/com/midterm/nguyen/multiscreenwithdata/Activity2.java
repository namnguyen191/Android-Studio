package com.midterm.nguyen.multiscreenwithdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    TextView dataRec;
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        dataRec = (TextView)findViewById(R.id.textView2);
        btnDone = (Button)findViewById(R.id.button2);

        Intent myLocalIntent = getIntent();
        double v1 = myLocalIntent.getDoubleExtra("key1",-1);
        double v2 = myLocalIntent.getDoubleExtra("key2",-1);
        double result = v1 + v2;
        dataRec.setText("Data received is \n"
                        + "val1= " + v1 + "\nval2= " + v2
                        + "\n\nresult= " + result);
    }

    public void onClose(View view) {
        finish();
    }
}
