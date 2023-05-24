package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContactActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ContactsDbHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        long contactId = getIntent().getLongExtra("contactId", 0);
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");

        // Find the EditText views in the layout
        EditText nameEditText = findViewById(R.id.nameTextEdit);
        EditText phoneEditText = findViewById(R.id.phoneTextEdit);
        EditText emailEditText = findViewById(R.id.emailTextEdit);

        // Set the contact details in the EditText views
        nameEditText.setText(name);
        phoneEditText.setText(phone);
        emailEditText.setText(email);

        sqlHelper=new ContactsDbHelper(this);
        db=sqlHelper.getWritableDatabase();

        Button saveButton = findViewById(R.id.saveButtonEdit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the updated values from the EditText views
                String updatedName = nameEditText.getText().toString();
                String updatedPhone = phoneEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();

                // Check if the required fields are empty
                if (updatedName.isEmpty()) {
                    Toast.makeText(EditContactActivity.this, "Name field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(updatedPhone.isEmpty() && updatedEmail.isEmpty()) {
                    Toast.makeText(EditContactActivity.this, "Phone or Email field is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the contact in the database
                ContentValues cv = new ContentValues();
                cv.put(ContactsDbHelper.COLUMN_NAME, updatedName);
                cv.put(ContactsDbHelper.COLUMN_PHONE, updatedPhone);
                cv.put(ContactsDbHelper.COLUMN_EMAIL, updatedEmail);
                db.update(ContactsDbHelper.TABLE, cv, ContactsDbHelper.COLUMN_ID + "=?", new String[]{String.valueOf(contactId)});

                // Display a toast message to indicate successful edit
                Toast.makeText(EditContactActivity.this, "Contact updated successfully", Toast.LENGTH_SHORT).show();

                goHome();
            }
        });

        Button backButton = findViewById(R.id.backButtonEdit);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

    }

    private void goHome() {
        db.close();
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}