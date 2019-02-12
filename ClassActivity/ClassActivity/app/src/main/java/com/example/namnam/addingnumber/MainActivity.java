package com.example.namnam.addingnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int num1, num2;
    EditText edit1, edit2;
    TextView txt;
    Button btnAdd;
    CheckBox chkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1 = (EditText)findViewById(R.id.editTextNum1);

        edit2 = (EditText)findViewById(R.id.editTextNum2);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        chkBox = (CheckBox)findViewById(R.id.checkBox);
        txt = (TextView)findViewById(R.id.txtResult);


    }

    public void onCheckChange(View view) {

        if(chkBox.isChecked())
            Toast.makeText(this, "LIKED", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "DISLIKED", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        num1 = Integer.parseInt(edit1.getText().toString());
        num2 = Integer.parseInt(edit2.getText().toString());
        String result = Integer.toString(num1 + num2);
        txt.setText(result);
    }
}
