package com.goncharenko.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DBActivity extends AppCompatActivity {

    Button btnAdd, btnRead, btnClear;
    EditText etFrom, etTo, etAmount, etResult;

    DBHelper dbHelper;

    ArrayList<Model> modelsList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               add();
               read();
            }
        });

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();
            }
        });

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                read();
            }
        });

        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        etAmount = findViewById(R.id.etAmount);
        etResult = findViewById(R.id.etResult);

        recyclerView = findViewById(R.id.recycler_view);

        dbHelper = new DBHelper(this);
    }

    public void add(){
        String currency_from = etFrom.getText().toString();
        String currency_to = etTo.getText().toString();
        double amount = Double.parseDouble(etAmount.getText().toString());
        double result = Double.parseDouble(etResult.getText().toString());

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_FROM, currency_from);
        contentValues.put(DBHelper.KEY_TO, currency_to);
        contentValues.put(DBHelper.KEY_AMOUNT, amount);
        contentValues.put(DBHelper.KEY_RESULT, result);


        database.insert(DBHelper.TABLE_CURRENCIES, null, contentValues);
    }

    public void read(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_CURRENCIES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int fromIndex = cursor.getColumnIndex(DBHelper.KEY_FROM);
            int toIndex = cursor.getColumnIndex(DBHelper.KEY_TO);
            int amountIndex = cursor.getColumnIndex(DBHelper.KEY_AMOUNT);
            int resultIndex = cursor.getColumnIndex(DBHelper.KEY_RESULT);
            StringBuilder str = new StringBuilder();
            modelsList = new ArrayList<>();
            do {
                str.append("ID = ").append(cursor.getInt(idIndex)).
                        append(", from = ").append(cursor.getString(fromIndex)).
                        append(", to = ").append(cursor.getString(toIndex)).
                        append(", amount = ").append(cursor.getDouble(amountIndex)).
                        append(", result = ").append(cursor.getDouble(resultIndex)).append("\n");
                modelsList.add(new Model(
                        cursor.getInt(idIndex),
                        cursor.getString(fromIndex),
                        cursor.getString(toIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getDouble(resultIndex)));
            } while (cursor.moveToNext());
        }

        if(modelsList.size()==0){
            // обработка если нет музыки
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ListAdapter(modelsList, this.getApplicationContext()));
        }
    }

    public void clear(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_CURRENCIES, null, null);
    }
}