package com.afinal.group.myconnections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.PersistableBundle;
import android.service.autofill.RegexValidator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContact extends AppCompatActivity {
    private TextInputLayout phoneNo;
    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout email;
    private TextInputLayout note;
    private TextInputLayout company;

    private static final String SAVE_FIRST_NAME = "First Name";
    private static final String SAVE_LAST_NAME = "Last Name";
    private static final String SAVE_EMAIL = "Email";
    private static final String SAVE_PHONE = "Phone";
    private static final String SAVE_COMPANY = "Company";
    private static final String SAVE_NOTE = "Note";

    private String f,l, oPhone, oEmail, oCompany, oNote;


    private DataBaseHelper db;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, UpdateContact.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        db = new DataBaseHelper(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        phoneNo =  findViewById(R.id.text_input_phoneNo);
        note = findViewById(R.id.text_input_note);
        note.getEditText();
        firstName = findViewById(R.id.text_input_firstname);
        lastName = findViewById(R.id.text_input_lastname);
        email = findViewById(R.id.text_input_email);
        company = findViewById(R.id.text_input_company);
        firstName.getEditText().setText(bundle.getString("firstname"));
        lastName.getEditText().setText(bundle.getString("lastname"));
        f =  firstName.getEditText().getText().toString().trim();
        l = lastName.getEditText().getText().toString().trim();
        Contact contact = db.getContact(f,l);
        phoneNo.getEditText().setText(contact.getPhoneNo());
        email.getEditText().setText(contact.getEmail());
        company.getEditText().setText(contact.getCompany());
        note.getEditText().setText(contact.getNote());
        (phoneNo.getEditText()).addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        email.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    db.checkEmailExist(email.getEditText().getText().toString());
                    if((db.checkEmailExist(email.getEditText().getText().toString())) != null){
                        if(!db.checkEmailExist(email.getEditText().getText().toString()).equalsIgnoreCase(firstName.getEditText().getText().toString() + " " + lastName.getEditText().getText().toString())){
                            email.setError("This Email Already Belongs To " + db.checkEmailExist(email.getEditText().getText().toString()) );
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(email.getEditText().getWindowToken(), 0);
                        } else {
                            email.setError(null);
                            email.setErrorEnabled(false);
                        }
                    } else {
                        email.setError(null);
                        email.setErrorEnabled(false);
                    }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneNo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                db.checkPhoneExist(phoneNo.getEditText().getText().toString());
                if((db.checkPhoneExist(phoneNo.getEditText().getText().toString())) != null){
                    if(!db.checkPhoneExist(phoneNo.getEditText().getText().toString()).equalsIgnoreCase(firstName.getEditText().getText().toString() + " " + lastName.getEditText().getText().toString())){
                        phoneNo.setError("This Phone Number Already Belongs To " + db.checkPhoneExist(phoneNo.getEditText().getText().toString()) );
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(email.getEditText().getWindowToken(), 0);
                    } else {
                        phoneNo.setError(null);
                        phoneNo.setErrorEnabled(false);
                    }
                } else {
                    phoneNo.setError(null);
                    phoneNo.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (savedInstanceState != null){
            firstName.getEditText().setText(savedInstanceState.getString(SAVE_FIRST_NAME));
            lastName.getEditText().setText(savedInstanceState.getString(SAVE_LAST_NAME));
            email.getEditText().setText(savedInstanceState.getString(SAVE_EMAIL));
            phoneNo.getEditText().setText(savedInstanceState.getString(SAVE_PHONE));
            company.getEditText().setText(savedInstanceState.getString(SAVE_COMPANY));
            note.getEditText().setText(savedInstanceState.getString(SAVE_NOTE));
        }

    }

    private boolean validateFirstName(){
        String firstNameInput = firstName.getEditText().getText().toString().trim();
        if(firstNameInput.isEmpty()){
            firstName.setError("This Field Cannot Be Empty!");
            return false;
        } else if(firstNameInput.length() > 20) {
            firstName.setError("First Name Cannot Be Longer Than 20 Characters!");
            return false;
        } else {
            firstName.setError(null);
            firstName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName(){
        String lastNameInput = lastName.getEditText().getText().toString().trim();
        if(lastNameInput.isEmpty()){
            lastName.setError("This Field cannot be empty!");
            return false;
        } else if(lastNameInput.length() > 20) {
            firstName.setError("Last Name Cannot Be Longer Than 20 Characters!");
            return false;
        } else {
            lastName.setError(null);
            lastName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNumber(){
        String phoneNumberInput = phoneNo.getEditText().getText().toString().trim();
        if(!phoneNumberInput.isEmpty()){
            if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumberInput)) {
                phoneNo.setError("Please Enter A Valid Phone Number");
                return false;
            } else {
                phoneNo.setError(null);
                phoneNo.setErrorEnabled(false);
                return true;
            }
        } else return true;
    }

    private boolean validateEmail(){
        String emailInput = email.getEditText().getText().toString().trim();
        if (!emailInput.isEmpty()){
            if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.setError("Please Enter A Valid Email!");
                return false;
            } else {
                email.setError(null);
                email.setErrorEnabled(false);
                return true;
            }
        } else return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    public void onUpdateContact(View view) {
        if (firstName.getEditText().getText().toString().equalsIgnoreCase(f)
                && lastName.getEditText().getText().toString().equalsIgnoreCase(l)
                && phoneNo.getEditText().getText().toString().trim().equalsIgnoreCase(oPhone)
                && email.getEditText().getText().toString().trim().equalsIgnoreCase(oEmail)
                && company.getEditText().getText().toString().trim().equalsIgnoreCase(oCompany)
                && note.getEditText().getText().toString().trim().equalsIgnoreCase(oNote)){
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            this.finish();
        } else {
            if (!validateEmail() | !validateFirstName() | !validateLastName() | !validatePhoneNumber()) {
                return;
            } else {
                final Contact contact = new Contact();
                contact.setFirstName(firstName.getEditText().getText().toString().trim());
                contact.setLastName(lastName.getEditText().getText().toString().trim());
                contact.setEmail(email.getEditText().getText().toString().trim());
                contact.setPhoneNo(phoneNo.getEditText().getText().toString().trim());
                contact.setCompany(company.getEditText().getText().toString().trim());
                contact.setNote(note.getEditText().getText().toString().trim());
                String pN = phoneNo.getEditText().getText().toString().trim();
                if (db.getContact(contact.getFirstName(), contact.getLastName()) == null) {
                    db.updateEntry(contact, f, l);
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    this.finish();
                } else if((firstName.getEditText().getText().toString().equalsIgnoreCase(f)
                        && lastName.getEditText().getText().toString().equalsIgnoreCase(l))) {
                    db.updateEntry(contact, f, l);
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    this.finish();
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this);
                    adb.setTitle("Warning");
                    adb.setMessage("Contact Already Exist");
                    adb.setNegativeButton("Ok", null);
                    adb.show();
                    return;
                }
            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_FIRST_NAME, firstName.getEditText().getText().toString());
        outState.putString(SAVE_LAST_NAME, lastName.getEditText().getText().toString());
        outState.putString(SAVE_EMAIL, email.getEditText().getText().toString());
        outState.putString(SAVE_PHONE, phoneNo.getEditText().getText().toString());
        outState.putString(SAVE_COMPANY, company.getEditText().getText().toString());
        outState.putString(SAVE_NOTE, note.getEditText().getText().toString());
    }

}
