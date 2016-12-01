package com.example.giang.ql_thuchicanhan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class danhmuctaikhoan extends AppCompatActivity {

    Button btnThemTk;
    ListView lstTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuctaikhoan);


        btnThemTk = (Button) findViewById(R.id.buttonThemTK);
        btnThemTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(danhmuctaikhoan.this, themtaikhoan.class);
                startActivity(intent);
            }
        });
        lstTaiKhoan = (ListView) findViewById(R.id.lstTaiKhoan);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getTaiKhoan().execute("http://huficlass.com/json.php");
            }
        });
    }

    public void editTaiKhoan(View v) {
        Intent intent = new Intent(danhmuctaikhoan.this, themtaikhoan.class);
        startActivity(intent);
    }

    public class getTaiKhoan extends AsyncTask<String, Integer, String> {
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

            TaiKhoanAdapter myAdapter = new TaiKhoanAdapter(danhmuctaikhoan.this, list);
            lstTaiKhoan.setAdapter(myAdapter);
        }

    }
}
