package com.example.projectpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Random;

public class ContentActivity extends AppCompatActivity {
    EditText mName;
    EditText mPhone;
    TableLayout mDBView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mName = (EditText) findViewById(R.id.text_name);
        mPhone = (EditText) findViewById(R.id.text_phone);
        mDBView = (TableLayout) findViewById(R.id.tableLayout1);
    }

    public void onInsert(View view) {
        ContentValues values  = new ContentValues();
        values.put(ContactDatabase.ID,new Random().nextInt(100));
        values.put(ContactDatabase.NAME,mName.getText().toString());
        values.put(ContactDatabase.PHONE,mPhone.getText().toString());
        getApplicationContext().getContentResolver().insert(ContactContentProvider.CONTENT_URI,values);
        Toast.makeText(this,"Inserted new contact!",Toast.LENGTH_SHORT).show();
        onShow(view);
    }

    public void onClear(View view) {
        int delcount = getContentResolver().delete(ContactContentProvider.CONTENT_URI,null,null);
        Toast.makeText(this,"Deleted " + delcount + " contacts!",Toast.LENGTH_SHORT).show();
        onShow(view);
    }

    public void onShow(View view) {
        Uri uri = ContactContentProvider.CONTENT_URI;
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);

        // Purge table so rows can be rebuilt
        mDBView.removeAllViews();

        int rows = cursor.getCount();
        int cols = cursor.getColumnCount();

        cursor.moveToFirst();

        for (int i = 0; i < rows; ++i) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < cols; ++j) {
                TextView text = new TextView(this);
                text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                text.setGravity(Gravity.CENTER);

                text.setText(cursor.getString(j));

                row.addView(text);
            }

            cursor.moveToNext();
            mDBView.addView(row);
        }
    }
}


