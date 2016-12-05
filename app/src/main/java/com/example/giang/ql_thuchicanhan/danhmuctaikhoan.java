package com.example.giang.ql_thuchicanhan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class danhmuctaikhoan extends AppCompatActivity {

    final String DATABASE_NAME = "misa.sqlite";
    SQLiteDatabase database;
    Button btnThemTk;
    ListView lstTaiKhoan;
    ArrayList<TAI_KHOAN> list;
    ArrayList<LOAI_TAI_KHOAN> listLTK;
    Spinner spinner;

    public static void TextTongTien(Activity activity, String DATABASE_NAME, ArrayList<TAI_KHOAN> list) {
        double tongtien = 0;
        TextView txtTongTien = (TextView) activity.findViewById(R.id.textViewKQ);
        for (int i = 0; i < list.size(); i++) {
            tongtien += TAI_KHOAN.Tiencon(activity, DATABASE_NAME, list.get(i).ID + "");
        }
        txtTongTien.setText(TaiKhoanAdapter.formatNumber(tongtien));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuctaikhoan);

        btnThemTk = (Button) findViewById(R.id.buttonThemTK);
        btnThemTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                TAI_KHOAN tk = null;
                bundle.putSerializable("item", tk);
                finish();
                Intent intent = new Intent(danhmuctaikhoan.this, themtaikhoan.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        lstTaiKhoan = (ListView) findViewById(R.id.lstTaiKhoan);
        final TAI_KHOAN tk = new TAI_KHOAN();
        list = tk.getTAI_KHOAN(danhmuctaikhoan.this, database, DATABASE_NAME);
        spinner = (Spinner) findViewById(R.id.spinner);
        loadSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    list = tk.getTAI_KHOAN(danhmuctaikhoan.this, database, DATABASE_NAME);
                    TaiKhoanAdapter myAdapter = new TaiKhoanAdapter(danhmuctaikhoan.this, list);
                    lstTaiKhoan.setAdapter(myAdapter);
                } else {
                    list = tk.getTAI_KHOAN(danhmuctaikhoan.this, database, DATABASE_NAME, i);
                    TaiKhoanAdapter myAdapter = new TaiKhoanAdapter(danhmuctaikhoan.this, list);
                    lstTaiKhoan.setAdapter(myAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final TaiKhoanAdapter myAdapter = new TaiKhoanAdapter(danhmuctaikhoan.this, list);
        lstTaiKhoan.setAdapter(myAdapter);
        TextTongTien(danhmuctaikhoan.this, DATABASE_NAME, list);
        lstTaiKhoan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idrow = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(danhmuctaikhoan.this);
                builder.setTitle("Xóa tài khoản này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                        TAI_KHOAN tk = new TAI_KHOAN();
                        tk.deleteTAI_KHOAN(danhmuctaikhoan.this, DATABASE_NAME, list.get(idrow));
                        list.remove(idrow);
                        myAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        TextTongTien(danhmuctaikhoan.this, DATABASE_NAME, list);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }

    public void editTaiKhoan(View v) {
        ImageButton btn = (ImageButton) v;
        Bundle bundle = new Bundle();
        TAI_KHOAN tk = list.get((int) btn.getTag());
        bundle.putSerializable("item", tk);

        finish();
        Intent intent = new Intent(danhmuctaikhoan.this, themtaikhoan.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    void loadSpinner() {
        LOAI_TAI_KHOAN loai = new LOAI_TAI_KHOAN();
        listLTK = loai.getLOAI_TAI_KHOAN(danhmuctaikhoan.this, database, DATABASE_NAME);
        final ArrayList<String> tenLoai = new ArrayList<>();
        ArrayList<Integer> listID = new ArrayList<>();
        tenLoai.add("Tất cả");
        listID.add(0);
        for (int i = 0; i < listLTK.size(); i++) {
            tenLoai.add(listLTK.get(i).TEN_LOAI);
            listID.add(listLTK.get(i).ID);
        }
        ArrayAdapter myAdapter = new ArrayAdapter<>(danhmuctaikhoan.this, android.R.layout.simple_spinner_item, tenLoai);
        spinner.setAdapter(myAdapter);

    }




}
