package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class ContactsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="contacts.db";
    private static final int SCHEMA=1;
    public static final String TABLE="contacts";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PHONE="phone";
    public static final String COLUMN_EMAIL="email";

    public ContactsDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts("
                +COLUMN_ID+" integer PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NAME+" text, "
                +COLUMN_PHONE+" text, "
                +COLUMN_EMAIL+" text)");

        String[] names = {
                "John Doe", "Jane Smith", "Michael Johnson", "Emily Davis", "Robert Wilson",
                "Sarah Brown", "David Taylor", "Olivia Martinez", "James Anderson", "Emma Thomas",
                "William Clark", "Sophia Rodriguez", "Joseph Lee", "Isabella Young", "Daniel Lewis",
                "Mia Thompson", "Christopher Turner", "Ava Harris", "Matthew Walker", "Abigail Allen"
        };

        String[] phones = {
                "123-456-7890", "987-654-3210", "555-555-5555", "111-222-3333", "444-555-6666",
                "777-888-9999", "333-222-1111", "444-555-6666", "888-777-6666", "999-888-7777",
                "222-333-4444", "111-999-8888", "555-444-3333", "222-555-8888", "999-444-1111",
                "777-222-5555", "888-444-7777", "333-555-6666", "222-111-4444", "555-888-7777"
        };

        String[] emails = {
                "john.doe@example.com", "jane.smith@example.com", "michael.johnson@example.com", "emily.davis@example.com", "robert.wilson@example.com",
                "sarah.brown@example.com", "david.taylor@example.com", "olivia.martinez@example.com", "james.anderson@example.com", "emma.thomas@example.com",
                "william.clark@example.com", "sophia.rodriguez@example.com", "joseph.lee@example.com", "isabella.young@example.com", "daniel.lewis@example.com",
                "mia.thompson@example.com", "christopher.turner@example.com", "ava.harris@example.com", "matthew.walker@example.com", "abigail.allen@example.com"
        };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String phone = phones[i];
            String email = emails[i];

            String query = "INSERT INTO " + TABLE + " (" + COLUMN_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_EMAIL + ") " +
                    "VALUES ('" + name + "', '" + phone + "', '" + email + "')";
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
