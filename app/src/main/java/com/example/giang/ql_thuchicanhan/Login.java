package com.example.giang.ql_thuchicanhan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Login extends AppCompatActivity {
    public static int idUser = 0;
    public ArrayList<NGUOI_DUNG> list = new ArrayList<>();
    Button btnLogin;
    EditText edtName, edtPass;
    TextView tvDKTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getNGUOI_DUNG().execute("http://huficlass.com/json.php");
            }
        });
        btnLogin = (Button) findViewById(R.id.btnDangKy);
        edtName = (EditText) findViewById(R.id.edtNameDK);
        edtPass = (EditText) findViewById(R.id.edtPassDK);
        tvDKTK = (TextView) findViewById(R.id.tvDKTK);
        tvDKTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, DangKy.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for (int i = 0; i < list.size(); i++) {
                    if (edtName.getText().toString().equals(list.get(i).USER) && edtPass.getText().toString().equals(list.get(i).PASS)) {
                        idUser = list.get(i).ID;
                        editor.putBoolean("isSave", true);
                        editor.putInt("idUser", idUser);
                        editor.commit();
                        finish();
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);
                        return;
                    }
                }
                editor.putBoolean("isSave", false);
                editor.commit();
                Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        boolean Save = sharedPreferences.getBoolean("isSave", false);
        if (Save) {
            idUser = sharedPreferences.getInt("idUser", 0);
            finish();
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (edtName.getText().toString().equals(list.get(i).USER) && edtPass.getText().toString().equals(list.get(i).PASS)) {
                        idUser = list.get(i).ID;
                        editor.putBoolean("isSave", true);
                        editor.putInt("idUser", idUser);
                        editor.commit();
                        finish();
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);
                        return;
                    }
                }
                editor.putBoolean("isSave", false);
                editor.commit();
                Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //get data from mysql to create sqlite
    void createDBSQLITE() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getTaiKhoan().execute("http://huficlass.com/json.php");
            }
        });
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

    class getTaiKhoan extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {

            return XuLy.displayDataFromTable(strings[0], "TAI_KHOAN");
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<TAI_KHOAN> list = new ArrayList<>();
            try {
                int ID;
                String TEN_TAI_KHOAN;
                int ID_NGUOI_DUNG;
                double SO_TIEN;
                Date NGAY_TAO;
                int LOAI_TAI_KHOAN;
                String GHI_CHU;

                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json_object = arr.getJSONObject(i);
                    ID = json_object.getInt("ID");
                    TEN_TAI_KHOAN = json_object.getString("TEN_TAI_KHOAN");
                    ID_NGUOI_DUNG = json_object.getInt("ID_NGUOI_DUNG");
                    SO_TIEN = json_object.getInt("SO_TIEN");
                    NGAY_TAO = new Date(2016, 1, 1);//json_object.getString("NGAY_TAO");
                    LOAI_TAI_KHOAN = json_object.getInt("LOAI_TAI_KHOAN");
                    GHI_CHU = json_object.getString("GHI_CHU");
                    list.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU));
                }
            } catch (JSONException e) {
            }
        }

    }


}
