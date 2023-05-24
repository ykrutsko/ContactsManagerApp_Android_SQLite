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

public class AddContactActivity extends AppCompatActivity {
    EditText nameBox;
    EditText phoneBox;
    EditText emailBox;
    SQLiteDatabase db;
    ContactsDbHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameBox=findViewById(R.id.nameTextAdd);
        phoneBox=findViewById(R.id.phoneTextAdd);
        emailBox=findViewById(R.id.emailTextAdd);

        sqlHelper=new ContactsDbHelper(this);
        db=sqlHelper.getWritableDatabase();

        Button saveButton=findViewById(R.id.saveButtonAdd);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the added values from the EditText views
                String addName = nameBox.getText().toString();
                String addPhone = phoneBox.getText().toString();
                String addEmail = emailBox.getText().toString();

                // Check if the required fields are empty
                if (addName.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Name field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(addPhone.isEmpty() && addEmail.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Phone or Email field is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues cv=new ContentValues();
                cv.put(ContactsDbHelper.COLUMN_NAME, addName);
                cv.put(ContactsDbHelper.COLUMN_PHONE, addPhone);
                cv.put(ContactsDbHelper.COLUMN_EMAIL, addEmail);
                db.insert(ContactsDbHelper.TABLE, null, cv);
                goHome();
            }
        });

        Button backButton=findViewById(R.id.backButtonAdd);
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
