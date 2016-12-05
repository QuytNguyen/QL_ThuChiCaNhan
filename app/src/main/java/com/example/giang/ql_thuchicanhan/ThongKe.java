package com.example.giang.ql_thuchicanhan;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class ThongKe extends AppCompatActivity {

    Spinner spinnerTK, spinnerNgay;
    ListView listView;
    TextView tvThu, tvChi;
    ArrayList<ND_CHI> listCHi = new ArrayList<>();
    ArrayList<ND_THU> listTHu = new ArrayList<>();
    AdapterlistThongkeHientai adapterlistThongkeHientai;
    ArrayList<TAI_KHOAN> listTK;
    ArrayList<String> listTenTK = new ArrayList<>();
    ArrayList<Integer> listIDTK = new ArrayList<>();
    String DATABASE_NAME = "misa.sqlite";
    ArrayList<String> listTheo = new ArrayList<>();
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        spinnerNgay = (Spinner) findViewById(R.id.spinner3);
        spinnerTK = (Spinner) findViewById(R.id.spinner2);
        listView = (ListView) findViewById(R.id.listView2);
        tvChi = (TextView) findViewById(R.id.textView144);
        tvThu = (TextView) findViewById(R.id.textView143);
        database = Database.initDatabase(this, DATABASE_NAME);
        listTK = getTAI_KHOAN(ThongKe.this, database, DATABASE_NAME);
        for (TAI_KHOAN a : listTK
                ) {
            listIDTK.add(a.ID);
            listTenTK.add(a.TEN_TAI_KHOAN);

        }
        listTheo.add("Hiện tại");
        listTheo.add("Tuần");
        listTheo.add("Tháng");
        listTheo.add("Năm");
        ArrayAdapter adapterTheo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listTheo);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listTenTK);
        spinnerTK.setAdapter(adapter);
        spinnerNgay.setAdapter(adapterTheo);

        spinnerTK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Readdata();
                    loadND_THU();
                    adapterlistThongkeHientai = new AdapterlistThongkeHientai(ThongKe.this, listCHi, listTHu, tvChi, tvThu);
                    listView.setAdapter(adapterlistThongkeHientai);
                } else {
                    Readdata(listIDTK.get(i));
                    loadND_THU(listIDTK.get(i));
                    adapterlistThongkeHientai = new AdapterlistThongkeHientai(ThongKe.this, listCHi, listTHu, tvChi, tvThu);
                    listView.setAdapter(adapterlistThongkeHientai);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void Readdata()// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ND_CHI.* FROM ND_CHI LEFT JOIN TAI_KHOAN ON ND_CHI.ID_TAI_KHOAN= TAI_KHOAN.ID where ID_NGUOI_DUNG = ?", new String[]{Login.idUser + ""});
        listCHi.clear();
        int ID;
        int ID_MUC_CHI;
        double SO_TIEN;
        int TAI_KHOAN;
        String CHI_CHO;
        String GHI_CHU;
        for (int i = 0; i < cursor.getCount(); i++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_MUC_CHI = cursor.getInt(1);
            SO_TIEN = cursor.getInt(2);
            TAI_KHOAN = cursor.getInt(3);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_CHI = new Date(temp);
            CHI_CHO = cursor.getString(5);
            GHI_CHU = cursor.getString(6);
            listCHi.add(new ND_CHI(ID, ID_MUC_CHI, SO_TIEN, TAI_KHOAN, NGAY_CHI, CHI_CHO, GHI_CHU));
        }
    }

    private void loadND_THU() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ND_THU.* FROM ND_THU LEFT JOIN TAI_KHOAN ON ND_THU.ID_TAI_KHOAN= TAI_KHOAN.ID where ID_NGUOI_DUNG = ?", new String[]{Login.idUser + ""});
        int ID;
        listTHu.clear();
        int ID_MUC_THU;
        int ID_TAI_KHOAN;
        double SO_TIEN;
        String GHI_CHU;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_MUC_THU = cursor.getInt(1);
            SO_TIEN = cursor.getInt(3);
            ID_TAI_KHOAN = cursor.getInt(2);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_THU = new Date(temp);

            GHI_CHU = cursor.getString(5);
            listTHu.add(new ND_THU(ID, ID_MUC_THU, SO_TIEN, ID_TAI_KHOAN, NGAY_THU, GHI_CHU));
        }
    }

    private void Readdata(int k)// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ND_CHI.* FROM ND_CHI LEFT JOIN TAI_KHOAN ON ND_CHI.ID_TAI_KHOAN= TAI_KHOAN.ID where ID_NGUOI_DUNG = ? and ID_TAI_KHOAN= ?", new String[]{Login.idUser + "", k + ""});
        listCHi.clear();
        int ID;
        int ID_MUC_CHI;
        double SO_TIEN;
        int TAI_KHOAN;
        String CHI_CHO;
        String GHI_CHU;
        for (int i = 0; i < cursor.getCount(); i++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_MUC_CHI = cursor.getInt(1);
            SO_TIEN = cursor.getInt(2);
            TAI_KHOAN = cursor.getInt(3);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_CHI = new Date(temp);
            CHI_CHO = cursor.getString(5);
            GHI_CHU = cursor.getString(6);
            listCHi.add(new ND_CHI(ID, ID_MUC_CHI, SO_TIEN, TAI_KHOAN, NGAY_CHI, CHI_CHO, GHI_CHU));
        }
    }

    private void loadND_THU(int k) {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ND_THU.* FROM ND_THU LEFT JOIN TAI_KHOAN ON ND_THU.ID_TAI_KHOAN= TAI_KHOAN.ID where ID_NGUOI_DUNG = ? and ID_TAI_KHOAN = ? ", new String[]{Login.idUser + "", k + ""});
        int ID;
        listTHu.clear();
        int ID_MUC_THU;
        int ID_TAI_KHOAN;
        double SO_TIEN;
        String GHI_CHU;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_MUC_THU = cursor.getInt(1);
            SO_TIEN = cursor.getInt(3);
            ID_TAI_KHOAN = cursor.getInt(2);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_THU = new Date(temp);

            GHI_CHU = cursor.getString(5);
            listTHu.add(new ND_THU(ID, ID_MUC_THU, SO_TIEN, ID_TAI_KHOAN, NGAY_THU, GHI_CHU));
        }
    }

    public ArrayList<TAI_KHOAN> getTAI_KHOAN(Activity activity, SQLiteDatabase database, String DATABASE_NAME) {
        ArrayList<TAI_KHOAN> list = new ArrayList<>();
        database = Database.initDatabase(activity, DATABASE_NAME);
        String idUser = Login.idUser + "";
        list.add(new TAI_KHOAN(0, "Tất cả"));
        Cursor cursor = database.rawQuery("SELECT * FROM TAI_KHOAN where ID_NGUOI_DUNG = ?", new String[]{idUser});
        for (int i = 0; i < cursor.getCount(); i++)// cho ch?y cursor là con tro
        {
            cursor.moveToPosition(i);
            int ID = cursor.getInt(0);
            String TEN_TAI_KHOAN = cursor.getString(1);
            int ID_NGUOI_DUNG = cursor.getInt(2);
            double SO_TIEN = cursor.getDouble(3);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_TAO = new Date(temp);
            int LOAI_TAI_KHOAN = cursor.getInt(5);
            String GHI_CHU = cursor.getString(6);
            list.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU));
        }
        return list;
    }


}
