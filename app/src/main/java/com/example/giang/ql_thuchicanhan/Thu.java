package com.example.giang.ql_thuchicanhan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Thu extends AppCompatActivity {
    final String DATABASE_NAME = "misa.sqlite";
    public ArrayList<DM_THU> listdm = new ArrayList<>();
    public ArrayList<TAI_KHOAN> listtk = new ArrayList<>();
    ArrayList<ND_THU> listndt = new ArrayList<>();
    Button btnCHon, btnThem, btnCHons, btnluu, btnhuy;
    EditText txtSoTien, txtNgayThu, txtGhiChu, txtSoTienThu, txtNgayThu1, txtGhiChuThu;
    Spinner spinnerKThu, spinnerTKhoan, spinnerKT, spinnerTK;
    ListView listView;
    Calendar cal;
    SQLiteDatabase database;
    AdapterSpinnerKhoanThu adapterDMThu;
    AdapterSpinnerTaiKhoan adapterSpinnerTaiKhoan;
    AdapterThu adapterThu;
    private Date dateFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu);

        listView = (ListView) findViewById(R.id.listthu);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnCHon = (Button) findViewById(R.id.buttonDataThu);
        spinnerKThu = (Spinner) findViewById(R.id.spTenKhoanThu);
        txtSoTien = (EditText) findViewById(R.id.editSTThu);
        txtNgayThu = (EditText) findViewById(R.id.editNgayThu);
        txtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        txtSoTien.setText("");


        loadTab();
        spinnerTKhoan = (Spinner) findViewById(R.id.spinnerTaiKhoanThu);

        adapterDMThu = new AdapterSpinnerKhoanThu(listdm, this);
        spinnerKThu.setAdapter(adapterDMThu);

        adapterSpinnerTaiKhoan = new AdapterSpinnerTaiKhoan(this, listtk);
        spinnerTKhoan.setAdapter(adapterSpinnerTaiKhoan);

        adapterThu = new AdapterThu(this, listndt, listdm, listtk);
        listView.setAdapter(adapterThu);
        loadDM_THU();
        loadTAI_KHOAN();
        loadND_THU();
        getDefaultInforkc(txtNgayThu);
        btnCHon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialogkc(txtNgayThu);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
                loadND_THU();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idrow = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(Thu.this);
                builder.setTitle("Xóa mục thu này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                        database = Database.initDatabase(Thu.this, DATABASE_NAME);
                        database.delete("ND_THU", "ID= ?", new String[]{listndt.get(idrow).ID + ""});
                        listndt.remove(idrow);
                        adapterThu.notifyDataSetChanged();
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

    void loadTAI_KHOAN() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TAI_KHOAN where ID_NGUOI_DUNG = ?", new String[]{Login.idUser + ""});
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
            listtk.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU));
        }
        adapterSpinnerTaiKhoan.notifyDataSetChanged();
    }

    void loadTab() {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhosthu);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Thêm khoản thu");
        spec1.setContent(R.id.tab1thu);
        spec1.setIndicator("Thêm khoản thu");
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Danh sách thu");
        spec2.setIndicator("Danh sách thu");
        spec2.setContent(R.id.tab2thu);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }

    public void getDefaultInforkc(EditText txtNgay) {
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        txtNgay.setText(strDate);
        dateFinish = cal.getTime();
    }

    public void showDatePickerDialogkc(final EditText txtNgay) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtNgay.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish = cal.getTime();
            }
        };
        String s1 = txtNgay.getText() + "";
        String strArrtmp1[] = s1.split("/");
        int ngayv = Integer.parseInt(strArrtmp1[0]);
        int thangv = Integer.parseInt(strArrtmp1[1]) - 1;
        int namv = Integer.parseInt(strArrtmp1[2]);
        DatePickerDialog pic1 = new DatePickerDialog(Thu.this, callback, namv, thangv, ngayv);
        pic1.setTitle("Chọn ngày hoàn thành");
        pic1.show();
    }

    private void loadND_THU() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ND_THU.* FROM ND_THU LEFT JOIN TAI_KHOAN ON ND_THU.ID_TAI_KHOAN= TAI_KHOAN.ID where ID_NGUOI_DUNG = ?", new String[]{Login.idUser + ""});
        int ID;
        int ID_MUC_THU;
        int ID_TAI_KHOAN;
        double SO_TIEN;
        Date NGAY_THU = new Date(System.currentTimeMillis());
        String GHI_CHU;
        for (int i = listndt.size(); i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            ID_MUC_THU = cursor.getInt(1);
            SO_TIEN = cursor.getInt(3);
            ID_TAI_KHOAN = cursor.getInt(2);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            NGAY_THU = new Date(temp);
            GHI_CHU = cursor.getString(5);
            listndt.add(new ND_THU(ID, ID_MUC_THU, SO_TIEN, ID_TAI_KHOAN, NGAY_THU, GHI_CHU));
        }
        adapterThu.notifyDataSetChanged();
    }

    void loadDM_THU() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DM_THU", null);
        listdm.clear();
        int ID;
        String MUC_THU;
        int ofUser;
        for (int i = 0; i < cursor.getCount(); i++)//
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            MUC_THU = cursor.getString(1);
            ofUser = cursor.getInt(2);
            listdm.add(new DM_THU(ID, MUC_THU, ofUser));
        }
        adapterDMThu.notifyDataSetChanged();
    }

    void insert() {
        try {
            int ID_MUC_THU = spinnerKThu.getSelectedItemPosition();
            ID_MUC_THU = listdm.get(ID_MUC_THU).ID;
            int ID_TAI_KHOAN = listtk.get(spinnerTKhoan.getSelectedItemPosition()).ID;
            double SO_TIEN = Double.parseDouble(txtSoTien.getText().toString());
            String GHI_CHU = txtGhiChu.getText().toString();
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateAsString = txtNgayThu.getText().toString();
            Date NGAY_THU = sourceFormat.parse(dateAsString);
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID_MUC_THU", ID_MUC_THU);
            contentValues.put("ID_TAI_KHOAN", ID_TAI_KHOAN);
            contentValues.put("SO_TIEN", SO_TIEN);
            contentValues.put("NGAY_THU", NGAY_THU.toString());
            contentValues.put("GHI_CHU", GHI_CHU);
            database.insert("ND_THU", null, contentValues);
            Toast.makeText(Thu.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(Thu.this, "Thêm thất bại!!!", Toast.LENGTH_SHORT).show();
        }
    }

    int Find(ArrayList<DM_THU> a, int x) {
        for (int i = 0; i < a.size(); i++
                ) {
            if (a.get(i).ID == x)
                return i;

        }
        return -1;
    }

    public void editNDThu(View v) {
        Button btn = (Button) v;
        int i = (int) btn.getTag();
        final Dialog dialog = new Dialog(Thu.this);

        dialog.setContentView(R.layout.suathu);
        dialog.setCancelable(false);
        txtSoTienThu = (EditText) dialog.findViewById(R.id.editST);
        spinnerKT = (Spinner) dialog.findViewById(R.id.spTenKT);
        txtGhiChuThu = (EditText) dialog.findViewById(R.id.editGC);
        txtNgayThu = (EditText) dialog.findViewById(R.id.editNT);
        btnluu = (Button) dialog.findViewById(R.id.buttonThem);
        btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
        btnCHons = (Button) dialog.findViewById(R.id.buttonDataNT);
        spinnerKT.setAdapter(adapterDMThu);
        spinnerTK = (Spinner) dialog.findViewById(R.id.spinner2);
        spinnerTK.setAdapter(adapterSpinnerTaiKhoan);


        loadDM_THU();
        loadTAI_KHOAN();
        double tien = listndt.get(i).SO_TIEN;
        txtGhiChuThu.setText(listndt.get(i).GHI_CHU);
        txtSoTienThu.setText(tien + "");
        Date d = listndt.get(i).NGAY_THU;
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(d);

        txtNgayThu.setText(strDate);
        final int j = i;
        int vitriKT = Find(listdm, listndt.get(i).ID_MUC_THU);
        int vitriTK = FindTK(listtk, listndt.get(i).ID_TAI_KHOAN);
        spinnerKT.setSelection(vitriKT);
        spinnerTK.setSelection(vitriTK);
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int ID = listndt.get(j).ID;
                    int ID_MUC_THU = listdm.get(spinnerKT.getSelectedItemPosition()).ID;
                    int ID_TAI_KHOAN = listtk.get(spinnerTK.getSelectedItemPosition()).ID;
                    double SO_TIEN = Double.parseDouble(txtSoTienThu.getText().toString());
                    DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateAsString = txtNgayThu.getText().toString();
                    Date NGAY_THU = sourceFormat.parse(dateAsString);
                    String GHI_CHU = txtGhiChuThu.getText().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ID_MUC_THU", ID_MUC_THU);
                    contentValues.put("ID_TAI_KHOAN", ID_TAI_KHOAN);
                    contentValues.put("SO_TIEN", SO_TIEN);
                    contentValues.put("NGAY_THU", NGAY_THU.toString());
                    contentValues.put("GHI_CHU", GHI_CHU);
                    database.update("ND_THU", contentValues, "ID = ?", new String[]{ID + ""});
                    adapterThu.notifyDataSetChanged();
                    Toast.makeText(Thu.this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(Thu.this, "Sửa  thất bại!!!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnCHons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialogkc(txtNgayThu);
            }
        });
        dialog.show();
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


