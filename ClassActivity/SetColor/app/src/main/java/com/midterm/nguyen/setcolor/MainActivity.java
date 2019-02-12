package com.midterm.nguyen.setcolor;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int SELECTOR_CONST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSelectColor(View view) {
        Intent myIntent = new Intent(MainActivity.this, SelectColorActivity.class);

        startActivityForResult(myIntent, SELECTOR_CONST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case SELECTOR_CONST:
                if(resultCode == RESULT_OK){
                    String color = data.getExtras().getString("selectedColor");
                    findViewById(R.id.colorbox).setBackgroundColor(Color.parseColor(color));
                }
        }
    }
}
