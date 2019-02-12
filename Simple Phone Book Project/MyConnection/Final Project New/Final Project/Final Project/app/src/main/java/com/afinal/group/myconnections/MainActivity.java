package com.afinal.group.myconnections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView contact;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayContact;
    private List<Contact> contactList = new ArrayList<Contact>();

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact = (ListView) findViewById(R.id.contact);
        registerForContextMenu(contact);
        db = new DataBaseHelper(getApplicationContext());
        contactList = db.getAllContactsList();
        displayAllContact(contactList);


        contact.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Intent intent = new Intent(MainActivity.this, UpdateContact.class);
                final String fullName = contact.getItemAtPosition(position).toString();
                final String firstName = fullName.split(" ")[0];
                final String lastName = fullName.split(" ")[1];
                intent.putExtra("firstname", firstName);
                intent.putExtra("lastname",  lastName);
                startActivityForResult(intent , 1);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchContact = (SearchView) findViewById(R.id.searchContact);
        searchContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.contact) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);


        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String fullName = contact.getItemAtPosition(info.position).toString();
        final String firstName = fullName.split(" ")[0];
        final String lastName = fullName.split(" ")[1];
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Delete?");
        adb.setMessage("Are you sure you want to delete " + fullName);
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                db.deleteEntry(firstName, lastName);
                contactList = db.getAllContactsList();
                displayAllContact(contactList);
            }
        });
        adb.show();
        return super.onContextItemSelected(item);
    }

    public void onNewContact(View view) {
        Intent intent = AddNewContact.newIntent(this);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            contactList = db.getAllContactsList();
            displayAllContact(contactList);
        }
    }

    private void displayAllContact(List<Contact> contactList) {
        arrayContact = new ArrayList<>();
        for (Contact sm : contactList) {
            arrayContact.add(sm.getFirstName() + " " + sm.getLastName());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrayContact);
        contact.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        contactList = db.getAllContactsList();
        displayAllContact(contactList);
    }








}
