package com.example.namnam.radiotest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    RadioButton radRed, radGreen, radBlue;
    TextView txt;
    RadioGroup radGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radRed = (RadioButton)findViewById(R.id.radRed);
        radGreen = (RadioButton)findViewById(R.id.radGreen);
        radBlue = (RadioButton)findViewById(R.id.radBlue);
        txt = (TextView)findViewById(R.id.textView);
        radGroup = (RadioGroup)findViewById(R.id.rGroup);
    }
    public void changeColor(View view) {
        if(radBlue.isChecked())
            txt.setTextColor(Color.BLUE);
        else if(radGreen.isChecked())
            txt.setTextColor(Color.GREEN);
        else
            txt.setTextColor(Color.RED);

    }
}
