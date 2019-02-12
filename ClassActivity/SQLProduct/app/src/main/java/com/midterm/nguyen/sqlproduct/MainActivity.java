package com.midterm.nguyen.sqlproduct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtProductId;
    EditText editProductName;
    EditText editProductQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtProductId = (TextView)findViewById(R.id.txtProductId);
        editProductName = (EditText)findViewById(R.id.editProductName);
        editProductQuantity = (EditText)findViewById(R.id.editProductQuantity);

    }

    public void addProductClick(View view) {
        DataBaseOpenHelper dbHandler = new DataBaseOpenHelper(this, null, null,1);
        int quantity = Integer.parseInt(editProductQuantity.getText().toString());
        Product product = new Product(editProductName.getText().toString(), quantity);
        dbHandler.addProduct(product);
        editProductName.getText().clear();
        editProductQuantity.getText().clear();
        txtProductId.setText("Product Id:");
        Toast.makeText(this, "New Product Added!", Toast.LENGTH_SHORT).show();
    }

    public void findProductClick(View view) {
        DataBaseOpenHelper dbHandler = new DataBaseOpenHelper(this, null, null,1);
        Product product = dbHandler.findProduct(editProductName.getText().toString());
        if(product != null){
            txtProductId.setText("Product Id: " + String.valueOf(product.getId()));
            editProductQuantity.setText(String.valueOf(product.getQuantity()));
        } else {
            Toast.makeText(this, "Product Not Found!", Toast.LENGTH_SHORT).show();
            editProductQuantity.getText().clear();
            txtProductId.setText("Product Id:");
        }
    }

    public void deleteProductClick(View view) {
        DataBaseOpenHelper dbHandler = new DataBaseOpenHelper(this, null, null,1);
        boolean result = dbHandler.deleteProduct(editProductName.getText().toString());
        if (result){
            editProductName.getText().clear();
            editProductQuantity.getText().clear();
            txtProductId.setText("Product Id:");
            Toast.makeText(this, "Product Delete!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Match Found!", Toast.LENGTH_SHORT).show();
            editProductQuantity.getText().clear();
            txtProductId.setText("Product Id:");
        }
    }
}
