package com.example.giang.ql_thuchicanhan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Hashtable;

public class themtaikhoan extends AppCompatActivity {
    Button btnLuuTK, btnHuyTK;
    EditText edtTen, edtGhiChu, edtSoTien;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themtaikhoan);
        btnLuuTK = (Button) findViewById(R.id.buttonLuuTK);
        btnHuyTK = (Button) findViewById(R.id.buttonHuyTK);
        edtTen = (EditText) findViewById(R.id.edtTenTK);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        edtSoTien = (EditText) findViewById(R.id.edtSoTien);
        spinner = (Spinner) findViewById(R.id.spinnerLoai);


        btnLuuTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new insertTAI_KHOAN().execute("http://huficlass.com/json.php");
                    }
                });
            }
        });
        btnHuyTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    class insertTAI_KHOAN extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            Hashtable<String, String> data = new Hashtable<>();
//                data.put("TEN_TAI_KHOAN", edtTen.getText().toString());
//                data.put("ID_NGUOI_DUNG", "Ví của Nhi");
//                data.put("SO_TIEN", edtSoTien.getText().toString());
//                data.put("NGAY_TAO", "Ví của Nhi");
//                data.put("LOAI_TAI_KHOAN", "Ví của Nhi");
//                data.put("GHI_CHU", "Ví của Nhi");

            return XuLy.insertData("http://huficlass.com/insert.php", "TAI_KHOAN", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
