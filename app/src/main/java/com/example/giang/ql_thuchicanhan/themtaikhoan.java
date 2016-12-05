package com.example.giang.ql_thuchicanhan;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class themtaikhoan extends AppCompatActivity {

    final String DATABASE_NAME = "misa.sqlite";
    SQLiteDatabase database;
    Button btnLuuTK, btnHuyTK;
    EditText edtTen, edtGhiChu, edtSoTien;
    Spinner spinner;
    ArrayList<LOAI_TAI_KHOAN> list;
    TAI_KHOAN i = new TAI_KHOAN();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themtaikhoan);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new getLoaiTaiKhoan().execute("http://huficlass.com/json.php");
//            }
//        });
        btnLuuTK = (Button) findViewById(R.id.buttonLuuTK);
        btnHuyTK = (Button) findViewById(R.id.buttonHuyTK);
        edtTen = (EditText) findViewById(R.id.edtTenTK);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        edtSoTien = (EditText) findViewById(R.id.edtSoTien);
        spinner = (Spinner) findViewById(R.id.spinnerLoai);
        LOAI_TAI_KHOAN loai = new LOAI_TAI_KHOAN();
        list = loai.getLOAI_TAI_KHOAN(themtaikhoan.this, database, DATABASE_NAME);
        final ArrayList<String> tenLoai = new ArrayList<>();
        ArrayList<Integer> listID = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            tenLoai.add(list.get(i).TEN_LOAI);
            listID.add(list.get(i).ID);
        }
        ArrayAdapter myAdapter = new ArrayAdapter<>(themtaikhoan.this, android.R.layout.simple_spinner_item, tenLoai);
        spinner.setAdapter(myAdapter);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        i = (TAI_KHOAN) bundle.getSerializable("item");
        if (i != null)//load dữ liệu sửa
        {
            edtTen.setText(i.TEN_TAI_KHOAN);
            spinner.setSelection(listID.indexOf(i.ID));//spinner
            edtSoTien.setText(i.SO_TIEN + "");
            edtGhiChu.setText(i.GHI_CHU);
        }
        btnLuuTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        new insertTAI_KHOAN().execute("http://huficlass.com/insert.php");
//                    }
//                });
                String TEN_TAI_KHOAN = edtTen.getText().toString();
                int ID_NGUOI_DUNG = Login.idUser;
                double SO_TIEN = Double.parseDouble(edtSoTien.getText().toString());
                Date dt = Calendar.getInstance().getTime();

                LOAI_TAI_KHOAN loaiTaiKhoan = list.get(spinner.getSelectedItemPosition());
                int LOAI_TAI_KHOAN = loaiTaiKhoan.ID;
                String GHI_CHU = edtGhiChu.getText().toString();
                if (i == null) {
                    TAI_KHOAN tk = new TAI_KHOAN(0, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, dt, LOAI_TAI_KHOAN, GHI_CHU);
                    tk.insertTAI_KHOAN(themtaikhoan.this, DATABASE_NAME, tk);
                } else {
                    i.TEN_TAI_KHOAN = TEN_TAI_KHOAN;
                    i.SO_TIEN = SO_TIEN;
                    i.LOAI_TAI_KHOAN = LOAI_TAI_KHOAN;
                    i.GHI_CHU = GHI_CHU;
                    i.updateTAI_KHOAN(themtaikhoan.this, DATABASE_NAME, i);
                }
                finish();
                Intent intent = new Intent(themtaikhoan.this, danhmuctaikhoan.class);
                startActivity(intent);
            }

        });
        btnHuyTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

//    public class getLoaiTaiKhoan extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//
//            return XuLy.displayDataFromTable(strings[0], "LOAI_TAI_KHOAN");
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            list = new ArrayList<>();
//            try {
//                int ID;
//                String TEN_LOAI;
//
//                JSONArray arr = new JSONArray(s);
//                for (int i = 0; i < arr.length(); i++) {
//                    JSONObject json_object = arr.getJSONObject(i);
//                    ID = json_object.getInt("ID");
//                    TEN_LOAI = json_object.getString("TEN_LOAI");
//
//                    list.add(new LOAI_TAI_KHOAN(ID, TEN_LOAI));
//                }
//            } catch (JSONException e) {
//            }
//
//            ArrayList<String> tenLoai=new ArrayList<>();
//            for(int i=0; i<list.size();i++)
//                tenLoai.add(list.get(i).TEN_LOAI);
//            ArrayAdapter myAdapter = new ArrayAdapter<>(themtaikhoan.this,android.R.layout.simple_spinner_item,tenLoai);
//            spinner.setAdapter(myAdapter);
//        }
//
//    }
//
//    class insertTAI_KHOAN extends AsyncTask<String, Integer, String> {
//        String TEN_TAI_KHOAN,ID_NGUOI_DUNG,SO_TIEN,NGAY_TAO,LOAI_TAI_KHOAN,GHI_CHU;
//        Hashtable<String, String> data = new Hashtable<>();
//
//        @Override
//        protected void onPreExecute() {
//
//            TEN_TAI_KHOAN=edtTen.getText().toString();
//            ID_NGUOI_DUNG=Login.idUser+"";
//            SO_TIEN=edtSoTien.getText().toString();
//
//            Date dt = Calendar.getInstance().getTime();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String currentTime = sdf.format(dt);
//            NGAY_TAO=currentTime;
//
//            LOAI_TAI_KHOAN loaiTaiKhoan= list.get(spinner.getSelectedItemPosition());
//            LOAI_TAI_KHOAN=loaiTaiKhoan.ID+"";
//            GHI_CHU=edtGhiChu.getText().toString();
//
//            data.put("TEN_TAI_KHOAN", TEN_TAI_KHOAN);
//            data.put("ID_NGUOI_DUNG", ID_NGUOI_DUNG);
//            data.put("SO_TIEN", SO_TIEN);
//            data.put("NGAY_TAO", NGAY_TAO);
//            data.put("LOAI_TAI_KHOAN", LOAI_TAI_KHOAN);
//            data.put("GHI_CHU", GHI_CHU);
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            return XuLy.insertData(strings[0], "TAI_KHOAN", data);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Toast.makeText(themtaikhoan.this,s,Toast.LENGTH_SHORT).show();
//        }
//    }


}
