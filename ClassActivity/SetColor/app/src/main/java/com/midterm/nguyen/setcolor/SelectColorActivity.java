package com.midterm.nguyen.setcolor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SelectColorActivity extends AppCompatActivity {
    Intent selectColor;
    private String color = "GREEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);
        setTitle("Select Color Activity");

        RadioGroup colors = (RadioGroup)findViewById(R.id.radioGroup);
        Button btnOk = (Button)findViewById(R.id.button2);
        selectColor = new Intent();
        colors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radGreen:
                        color = "GREEN";
                        break;
                    case R.id.radBlue:
                        color = "BLUE";
                        break;
                    case R.id.radOrange:
                        color = "#FFA500";
                        break;
                    case R.id.radPurple:
                        color = "#800080";
                        break;
                    case R.id.radRed:
                        color = "RED";
                        break;
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectColor.putExtra("selectedColor", color);
                setResult(RESULT_OK, selectColor);
                finish();
            }
        });
    }
}
