package com.example.giang.ql_thuchicanhan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    public ArrayList<NGUOI_DUNG> list = new ArrayList<>();
    Button btnLogin;
    EditText edtName, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getNGUOI_DUNG().execute("https://nguyenquyt95.000webhostapp.com/json.php");
            }
        });
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for (int i = 0; i < list.size(); i++) {
                    if (edtName.getText().toString().equals(list.get(i).USER) && edtPass.getText().toString().equals(list.get(i).PASS)) {
                        editor.putBoolean("isSave", true);
                        editor.commit();
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);
                    } else {
                        editor.putBoolean("isSave", false);
                        editor.commit();
                        Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        });

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        boolean Save = sharedPreferences.getBoolean("isSave", false);
        if (Save) {
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
        }
    }

    class getNGUOI_DUNG extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return XuLy.displayDataFromTable(strings[0], "NGUOI_DUNG");
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                int id;
                String user, pass;

                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json_object = arr.getJSONObject(i);
                    id = json_object.getInt("ID");
                    user = json_object.getString("USER");
                    pass = json_object.getString("PASS");

                    list.add(new NGUOI_DUNG(id, user, pass));
                }
            } catch (JSONException e) {
            }
        }
    }
}
