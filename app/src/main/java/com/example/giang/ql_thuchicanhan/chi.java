package com.example.giang.ql_thuchicanhan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;

public class chi extends AppCompatActivity {
    public ArrayList<DM_CHI> listdm = new ArrayList<>();
    public ArrayList<TAI_KHOAN> listtk = new ArrayList<>();
    ListView listView;
    Button btnCHon, btnThem, btnCHons, btnluu, btnhuy;
    EditText txtSoTien, txtNgayChi, txtChiCho, txtGhiChu, txtSoTiens, txtNgayChis, txtChiChos, txtGhiChus;
    Spinner spinnerKC, spinnerTK, spinnerKCs, spinnerTKs;
    Calendar cal;
    String DATABASE_NAME = "misa.sqlite";
    SQLiteDatabase database;
    AdapterSpinnerKhoanChi adapterDMCHI;
    AdapterSpinnerTaiKhoan adapterSpinnerTaiKhoan;
    ArrayList<ND_CHI> list = new ArrayList<>();
    AdapterChi adapterChi;
    ArrayList<Integer> listIntegerTK = new ArrayList<>();
    ArrayList<Integer> listIntegerDMC = new ArrayList<>();
    private Date dateFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi);
        listView = (ListView) findViewById(R.id.listchi);
        btnThem = (Button) findViewById(R.id.button);

        spinnerKC = (Spinner) findViewById(R.id.editTaiKhoanChi);
        txtSoTien = (EditText) findViewById(R.id.editSTT);
        txtNgayChi = (EditText) findViewById(R.id.editNgayChi);
        btnCHon = (Button) findViewById(R.id.buttonDataChi);
        txtChiCho = (EditText) findViewById(R.id.editText4);
        txtGhiChu = (EditText) findViewById(R.id.editText5);
        txtSoTien.setText("0");
        spinnerTK = (Spinner) findViewById(R.id.spinnerTaiKhoanChi);
        loadTab();
        adapterDMCHI = new AdapterSpinnerKhoanChi(listdm, this);
        spinnerKC.setAdapter(adapterDMCHI);

        adapterSpinnerTaiKhoan = new AdapterSpinnerTaiKhoan(this, listtk);
        spinnerTK.setAdapter(adapterSpinnerTaiKhoan);

        adapterChi = new AdapterChi(this, list, listdm, listtk);
        listView.setAdapter(adapterChi);

        ReaddataDM_CHI();
        ReaddataTaiKhoan();
        Readdata();
        btnCHon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDefaultInforkc();
                showDatePickerDialogkc();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Them();
                Readdata();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(chi.this);
                dialog.setTitle("Sửa thông tin chi");
                dialog.setContentView(R.layout.diologsuachi);
                dialog.setCancelable(false);
                txtSoTiens = (EditText) dialog.findViewById(R.id.editSTT);
                spinnerKCs = (Spinner) dialog.findViewById(R.id.editTaiKhoanChi);
                txtChiChos = (EditText) dialog.findViewById(R.id.editText4);
                txtGhiChus = (EditText) dialog.findViewById(R.id.editText5);
                spinnerTKs = (Spinner) dialog.findViewById(R.id.spinnerTaiKhoanChi);
                txtNgayChis = (EditText) dialog.findViewById(R.id.editNgayChi);
                spinnerTKs.setAdapter(adapterSpinnerTaiKhoan);
                btnluu = (Button) dialog.findViewById(R.id.button);
                btnhuy = (Button) dialog.findViewById(R.id.buttonhuy);
                btnCHons = (Button) dialog.findViewById(R.id.buttonDataChi);
                spinnerKCs.setAdapter(adapterDMCHI);
                ReaddataDM_CHI();
                ReaddataTaiKhoan();
                double tien = list.get(i).SO_TIEN;
                txtChiChos.setText(list.get(i).CHI_CHO);
                txtGhiChus.setText(list.get(i).GHI_CHU);
                txtSoTiens.setText(tien + "");
                SimpleDateFormat dft = null;
                dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String strDate = dft.format(list.get(i).NGAY_CHI.getTime());
                txtNgayChis.setText(strDate);
                final int j = i;
                int vitriKC = Find(listdm, list.get(i).ID_MUC_CHI);
                int vitriTK = FindTK(listtk, list.get(i).TAI_KHOAN);
                spinnerKCs.setSelection(vitriKC);
                spinnerTKs.setSelection(vitriTK);
                btnluu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            int ID_MUC_CHI = spinnerKCs.getSelectedItemPosition();
                            ID_MUC_CHI = listdm.get(ID_MUC_CHI).ID;
                            int TAI_KHOAN = spinnerTKs.getSelectedItemPosition();
                            TAI_KHOAN = listtk.get(TAI_KHOAN).ID;
                            double SO_TIEN = Double.parseDouble(txtSoTiens.getText().toString());
                            String CHI_CHO = txtChiChos.getText().toString();
                            String GHI_CHU = txtGhiChus.getText().toString();

                            ContentValues contentValues = new ContentValues();
                            contentValues.put("ID_MUC_CHI", ID_MUC_CHI);
                            contentValues.put("SO_TIEN", SO_TIEN);
                            contentValues.put("ID_TAI_KHOAN", TAI_KHOAN);
                            contentValues.put("NGAY_CHI", txtNgayChis.getText().toString());
                            contentValues.put("CHI_CHO", CHI_CHO);
                            contentValues.put("GHI_CHU", GHI_CHU);
                            database.update("ND_CHI", contentValues, "ID = ?", new String[]{list.get(j).ID + ""});
                            Toast.makeText(chi.this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(chi.this, "Sủa không thành công!!!", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        Readdata();
                    }
                });
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int j = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(chi.this);
                builder.setTitle("Xóa khoản chi này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                        database = Database.initDatabase(chi.this, DATABASE_NAME);
                        database.delete("ND_CHI", "ID= ?", new String[]{list.get(j).ID + ""});
                        Readdata();
                        dialog.dismiss();
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

    void loadTab() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Thêm khoản chi");
        spec1.setContent(R.id.tab1chi);
        spec1.setIndicator("Thêm khoản chi");
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Danh sách chi");
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

    private void Readdata()// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM ND_CHI", null);
        list.clear();
        int ID;
        int ID_MUC_CHI;
        double SO_TIEN;
        int TAI_KHOAN;
        Date NGAY_CHI = new Date(System.currentTimeMillis());
        String CHI_CHO;
        String GHI_CHU;
        for (int i = 0; i < cursor.getCount(); i++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_MUC_CHI = cursor.getInt(1);
            SO_TIEN = cursor.getInt(2);
            TAI_KHOAN = cursor.getInt(3);
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            try {
                NGAY_CHI = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            CHI_CHO = cursor.getString(5);
            GHI_CHU = cursor.getString(6);
            list.add(new ND_CHI(ID, ID_MUC_CHI, SO_TIEN, TAI_KHOAN, NGAY_CHI, CHI_CHO, GHI_CHU));
        }
        adapterChi.notifyDataSetChanged();
    }

    private void ReaddataDM_CHI()// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DM_CHI", null);
        listdm.clear();
        int ID;
        int ID_LOAI;
        String MUC_CHI;
        int ofUser;
        for (int i = 0; i < cursor.getCount(); i++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_LOAI = cursor.getInt(2);
            MUC_CHI = cursor.getString(1);
            ofUser = cursor.getInt(3);
            listdm.add(new DM_CHI(ID, ID_LOAI, MUC_CHI, ofUser));
        }
        adapterDMCHI.notifyDataSetChanged();
    }

    void ReaddataTaiKhoan() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TAI_KHOAN", null);
        listtk.clear();
        for (int i = 0; i < cursor.getCount(); i++)// cho ch?y cursor là con tro
        {

            cursor.moveToPosition(i);
            int ID = cursor.getInt(0);
            String TEN_TAI_KHOAN = cursor.getString(1);
            int ID_NGUOI_DUNG = cursor.getInt(2);
            double SO_TIEN = cursor.getDouble(3);
            String date = cursor.getString(4);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String currentTime = sdf.format(date);
            Date NGAY_TAO = new Date(System.currentTimeMillis());
            try {
                NGAY_TAO = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int LOAI_TAI_KHOAN = cursor.getInt(5);
            String GHI_CHU = cursor.getString(6);
            int ofUser = cursor.getInt(7);
            listtk.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU, ofUser));
        }
        adapterSpinnerTaiKhoan.notifyDataSetChanged();
    }

    void Them() {
        try {
            int ID_MUC_CHI = spinnerKC.getSelectedItemPosition();
            ID_MUC_CHI = listdm.get(ID_MUC_CHI).ID;
            int TAI_KHOAN = spinnerTK.getSelectedItemPosition();
            TAI_KHOAN = listtk.get(TAI_KHOAN).ID;
            double SO_TIEN = Double.parseDouble(txtSoTien.getText().toString());
            String CHI_CHO = txtChiCho.getText().toString();
            String GHI_CHU = txtGhiChu.getText().toString();
            Date dt = Calendar.getInstance().getTime();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID_MUC_CHI", ID_MUC_CHI);
            contentValues.put("SO_TIEN", SO_TIEN);
            contentValues.put("ID_TAI_KHOAN", TAI_KHOAN);
            contentValues.put("NGAY_CHI", txtNgayChi.getText().toString());
            contentValues.put("CHI_CHO", CHI_CHO);
            contentValues.put("GHI_CHU", GHI_CHU);
            database.insert("ND_CHI", null, contentValues);
            Toast.makeText(chi.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(chi.this, "Thêm không thành công!!!", Toast.LENGTH_SHORT).show();
        }
    }

    int Find(ArrayList<DM_CHI> a, int x) {
        for (int i = 0; i < a.size(); i++
                ) {
            if (a.get(i).ID == x)
                return i;

        }
        return -1;
    }

    int FindTK(ArrayList<TAI_KHOAN> a, int x) {
        for (int i = 0; i < a.size(); i++
                ) {
            if (a.get(i).ID == x)
                return i;

        }
        return -1;
    }

}
//
