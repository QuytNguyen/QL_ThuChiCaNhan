package com.example.giang.ql_thuchicanhan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class DangKy extends AppCompatActivity {

    Button btnDangKy;
    EditText edtNameDK, edtPassDK, edtXacNhan;
    String USER, PASS, xacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edtNameDK = (EditText) findViewById(R.id.edtNameDK);
        edtPassDK = (EditText) findViewById(R.id.edtPassDK);
        edtXacNhan = (EditText) findViewById(R.id.edtXacNhan);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USER = edtNameDK.getText().toString();
                PASS = edtPassDK.getText().toString();
                xacnhan = edtXacNhan.getText().toString();
                if (!PASS.equals(xacnhan))
                    Toast.makeText(DangKy.this, "Mật khẩu xác nhận không đúng.", Toast.LENGTH_SHORT).show();
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new insertNGUOI_DUNG().execute("http://huficlass.com/insert.php");
                        }
                    });
                }
            }
        });
    }

    class insertNGUOI_DUNG extends AsyncTask<String, Integer, String> {
        Hashtable<String, String> data = new Hashtable<>();

        @Override
        protected void onPreExecute() {
            data.put("USER", USER);
            data.put("PASS", PASS);
        }

        @Override
        protected String doInBackground(String... strings) {
            return XuLy.insertData(strings[0], "NGUOI_DUNG", data);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(DangKy.this, "Tạo tài khoản thành công.", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(DangKy.this, Login.class);
            startActivity(intent);
        }
    }
}
