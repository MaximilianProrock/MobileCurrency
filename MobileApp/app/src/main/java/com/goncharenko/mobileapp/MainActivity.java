package com.goncharenko.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    private EditText amount_input;
    private EditText from_input;
    private EditText to_input;
    private TextView result_info;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount_input = findViewById(R.id.amount_input);
        from_input = findViewById(R.id.from_input);
        to_input = findViewById(R.id.to_input);
        result_info = findViewById(R.id.result);

        dbHelper = new DBHelper(this);
    }

    public void onClick(View view) {
        String from = from_input.getText().toString();
        String to = to_input.getText().toString();
        int amount = Integer.parseInt(amount_input.getText().toString());
        String key = "&apikey=wWv45iPIAYg2Z6S1GPgeLI4gltWnwW4c";

        String url = "https://api.apilayer.com/fixer/convert?to=" + to + "&from=" + from + "&amount=" + amount + key;
        new GetURLData().execute(url);
    }

    public void onClickDB(View view) {
        dbHelper.close();
        Intent intent = new Intent(MainActivity.this, DBActivity.class);
        startActivity(intent);
    }

    public void onClickAdd(View view) {
        String currency_from = from_input.getText().toString();
        String currency_to = to_input.getText().toString();
        double amount = Double.parseDouble(amount_input.getText().toString());
        double result = Double.parseDouble(result_info.getText().toString());

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_FROM, currency_from);
        contentValues.put(DBHelper.KEY_TO, currency_to);
        contentValues.put(DBHelper.KEY_AMOUNT, amount);
        contentValues.put(DBHelper.KEY_RESULT, result);

        database.insert(DBHelper.TABLE_CURRENCIES, null, contentValues);
    }


    @SuppressLint("StaticFieldLeak")
    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            result_info.setText("Загрузка...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = bufferedReader.readLine()) != null){
                    buffer.append(line).append("\n");
                }

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }

                try{
                    if(bufferedReader != null){
                        bufferedReader.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject object = new JSONObject(result);
                result_info.setText("" + object.getDouble("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}