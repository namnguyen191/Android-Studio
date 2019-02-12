package ca.javateacher.filedemo1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private EditText mSaveText;
  private TextView mLoadText;
  private String mFileName;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mFileName = getResources().getString(R.string.file_name);

    setContentView(R.layout.main);

    mSaveText = findViewById(R.id.saveField);
    mLoadText = findViewById(R.id.loadField);

    Button saveBtn = findViewById(R.id.saveBtn);
    saveBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideKeyboard();
        FileUtils.save(MainActivity.this, mFileName, mSaveText.getText().toString());
      }
    });

    Button loadBtn = findViewById(R.id.loadBtn);
    loadBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mLoadText.setText(FileUtils.load(MainActivity.this, mFileName));
      }
    });
  }

  private void hideKeyboard(){
    InputMethodManager imm
        = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    if(imm != null) {
      imm.hideSoftInputFromWindow(mSaveText.getWindowToken(), 0);
    }
  }
}