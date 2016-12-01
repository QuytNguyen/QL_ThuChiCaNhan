package com.example.giang.ql_thuchicanhan;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;

public class chi extends AppCompatActivity {
    public ArrayList<DM_CHI> listdm = new ArrayList<>();
    public ArrayList<TAI_KHOAN> listtk = new ArrayList<>();
    ListView listView;
    Button btnCHon, btnThem;
    EditText txtSoTien, txtNgayChi;
    Spinner spinnerKC, spinnerTK;
    Calendar cal;
    AdapterSpinnerKhoanChi adapterDMCHI;
    AdapterSpinnerTaiKhoan adapterSpinnerTaiKhoan;
    private Date dateFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi);
        listView = (ListView) findViewById(R.id.listchi);
        btnThem = (Button) findViewById(R.id.button);
        btnCHon = (Button) findViewById(R.id.buttonDataChi);
        spinnerKC = (Spinner) findViewById(R.id.editTaiKhoanChi);
        txtSoTien = (EditText) findViewById(R.id.editSTT);
        txtNgayChi = (EditText) findViewById(R.id.editNgayChi);
        txtSoTien.setText("123");
        spinnerTK = (Spinner) findViewById(R.id.spinnerTaiKhoanChi);

        adapterDMCHI = new AdapterSpinnerKhoanChi(listdm, this);
        spinnerKC.setAdapter(adapterDMCHI);

        adapterSpinnerTaiKhoan = new AdapterSpinnerTaiKhoan(this, listtk);
        spinnerTK.setAdapter(adapterSpinnerTaiKhoan);

        btnCHon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDefaultInforkc();
                showDatePickerDialogkc();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getDM_CHI().execute("https://nguyenquyt95.000webhostapp.com/json.php");
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getTAI_KHOAN().execute("https://nguyenquyt95.000webhostapp.com/json.php");
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getnoidung().execute("https://nguyenquyt95.000webhostapp.com/json.php");
            }
        });

        loadTab();
    }
    void loadTab()
    {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Thêm khoản chi");
        spec1.setContent(R.id.tab1chi);
        spec1.setIndicator("Thêm khoản chi");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Danh sách chi");
        spec2.setIndicator("Danh sách chi");
        spec2.setContent(R.id.tab2chi);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }

    public void getDefaultInforkc() {
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        txtNgayChi.setText(strDate);
        dateFinish = cal.getTime();
    }

    public void showDatePickerDialogkc() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtNgayChi.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish = cal.getTime();
            }
        };
        String s1 = txtNgayChi.getText() + "";
        String strArrtmp1[] = s1.split("/");
        int ngayv = Integer.parseInt(strArrtmp1[0]);
        int thangv = Integer.parseInt(strArrtmp1[1]) - 1;
        int namv = Integer.parseInt(strArrtmp1[2]);
        DatePickerDialog pic1 = new DatePickerDialog(chi.this, callback, namv, thangv, ngayv);
        pic1.setTitle("Chọn ngày hoàn thành");
        pic1.show();
    }

    class getnoidung extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return XuLy.displayDataFromTable(strings[0], "ND_CHI");
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(chi.this,s,Toast.LENGTH_LONG).show();
            ArrayList<ND_CHI> list = new ArrayList<>();
            AdapterChi adapterChi;
            try {
                int ID;
                int ID_MUC_CHI;
                double SO_TIEN;
                int TAI_KHOAN;
                //Date NGAY_CHI;
                String CHI_CHO;
                String GHI_CHU;

                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json_object = arr.getJSONObject(i);
                    ID = json_object.getInt("ID");
                    ID_MUC_CHI = json_object.getInt("ID_MUC_CHI");
                    SO_TIEN = json_object.getDouble("SO_TIEN");
                    TAI_KHOAN = json_object.getInt("TAI_KHOAN");
                    String stringDate = json_object.getString("NGAY_CHI");
                    CHI_CHO = json_object.getString("CHI_CHO");
                    GHI_CHU = json_object.getString("GHI_CHU");
                    list.add(new ND_CHI(ID, ID_MUC_CHI, SO_TIEN, TAI_KHOAN, CHI_CHO, GHI_CHU));
                }
                adapterChi = new AdapterChi(chi.this, list, listdm, listtk);
                listView.setAdapter(adapterChi);
            } catch (JSONException e) {
            }
        }
    }

    class getDM_CHI extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return XuLy.displayDataFromTable(strings[0], "DM_CHI");
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                int ID;
                int ID_LOAI;
                String MUC_CHI;
                listdm.add(new DM_CHI());
                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json_object = arr.getJSONObject(i);
                    ID = json_object.getInt("ID");
                    ID_LOAI = json_object.getInt("ID_LOAI");
                    MUC_CHI = json_object.getString("MUC_CHI");
                    listdm.add(new DM_CHI(ID, ID_LOAI, MUC_CHI));
                }
                adapterDMCHI.notifyDataSetChanged();
            } catch (JSONException e) {
            }
        }
    }

    class getTAI_KHOAN extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return XuLy.displayDataFromTable(strings[0], "TAI_KHOAN");
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                int ID;
                String TEN_TAI_KHOAN;
                JSONArray arr = new JSONArray(s);
                listtk.add(new TAI_KHOAN());
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json_object = arr.getJSONObject(i);
                    ID = json_object.getInt("ID");
                    TEN_TAI_KHOAN = json_object.getString("TEN_TAI_KHOAN");
                    listtk.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN));
                }
                adapterSpinnerTaiKhoan.notifyDataSetChanged();
            } catch (JSONException e) {
            }
        }
    }
}
