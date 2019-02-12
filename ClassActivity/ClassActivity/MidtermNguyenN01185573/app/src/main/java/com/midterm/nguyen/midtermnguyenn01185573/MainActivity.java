package com.midterm.nguyen.midtermnguyenn01185573;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText loanAmount, interest, months;
    private TextView monthPay, totalPay;
    private Button btnCal;
    double monthly, total;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loanAmount = (EditText)findViewById(R.id.editLoanAmount);
        interest = (EditText)findViewById(R.id.editInterest);
        months = (EditText)findViewById(R.id.editMonths);
        btnCal = (Button)findViewById(R.id.btnCal);
        monthPay = (TextView)findViewById(R.id.txtMonthlyPay);
        totalPay = (TextView)findViewById(R.id.txtTotalPay);
        loanAmount.setText("10000");
        interest.setText("2");
        months.setText("5");
        calculatePayment();
    }

    protected void calculatePayment(){
        double amount = Double.parseDouble(loanAmount.getText().toString());
        double rate = Double.parseDouble(interest.getText().toString());
        double month = Double.parseDouble(months.getText().toString());
        if(amount <=0 || rate <=0 || month <=0){
            Toast.makeText(this,"Please Enter A Valid Number!", Toast.LENGTH_SHORT).show();
            monthPay.setText("Unavailable");
            totalPay.setText("Unavailable");
        } else {
             monthly = (amount * rate / 100) / 12;
             total = ((amount * rate / 100) / 12) * month;
            monthPay.setText(Double.toString(monthly));
            totalPay.setText(Double.toString(total));
        }
    }

    public void calculatePayment(View view) {
        calculatePayment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putDouble("amount",Double.parseDouble(loanAmount.getText().toString()) );
        outState.putDouble("interest",Double.parseDouble(interest.getText().toString()) );
        outState.putDouble("month",Double.parseDouble(months.getText().toString()));
        outState.putDouble("monthly",Double.parseDouble(monthPay.getText().toString()));
        outState.putDouble("total", Double.parseDouble(totalPay.getText().toString()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        loanAmount.setText(Double.toString(saveInstanceState.getDouble("amount")));
        interest.setText(Double.toString(saveInstanceState.getDouble("interest")));
        months.setText(Double.toString(saveInstanceState.getDouble("month")));
        monthPay.setText(Double.toString(saveInstanceState.getDouble("monthly")));
        totalPay.setText(Double.toString(saveInstanceState.getDouble("total")));
    }
}
