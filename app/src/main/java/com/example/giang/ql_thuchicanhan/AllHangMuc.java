package com.example.giang.ql_thuchicanhan;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AllHangMuc extends AppCompatActivity {

    Button btnThemChi;
    Button btnThemThu;
    Spinner spinnerChi;
    EditText txtMucChi, txtMucThu, txtMucChis, txtMucThus;
    Button btnLuuMucCHi, btnHuyMucCHi, btnLuuMucThu, btnHuyMucThu, btnLuuMucCHis, btnHuyMucCHis, btnLuuMucThus, btnHuyMucThus;
    ArrayList<LOAI_DM_CHI> lstldm = new ArrayList<>();
    AdapterLMC adapterLMC;

    ListView listView;

    ArrayList<String> lst;
    ArrayAdapter<String> arrayAdapter;

    ListView listViewDMThu;
    ArrayList<DM_CHI> lstdmc = new ArrayList<>();
    ArrayList<DM_THU> lstdmt = new ArrayList<>();
    ArrayList<String> lstDMT;
    ArrayAdapter<String> arrayAdapterDMT;
    String DATABASE_NAME = "misa.sqlite";
    SQLiteDatabase database;
    Spinner spinnerThemMUCCHi, spinnerThemMUCCHis;

    ArrayAdapter<AdapterLMC> adapterLMCArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hang_muc);
        spinnerChi = (Spinner) findViewById(R.id.spinnerLoaiMucChi);
        loadTab();
        adapterLMC = new AdapterLMC(this, lstldm);
        spinnerChi.setAdapter(adapterLMC);
        btnThemChi=(Button)findViewById(R.id.buttonThemHangMucChi);
        btnThemThu=(Button)findViewById(R.id.buttonThemHangMucThu);
        listView = (ListView) findViewById(R.id.listViewHangMucCHi);
        listViewDMThu = (ListView) findViewById(R.id.listViewHangMucThu);
        lst = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst);
        listView.setAdapter(arrayAdapter);

        lstDMT = new ArrayList<String>();
        arrayAdapterDMT = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstDMT);
        listViewDMThu.setAdapter(arrayAdapterDMT);

        spinnerChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {
                    ReaddataDM_CHI();
                } else
                    setSpinnerChi(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        spinnerChi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
//                Cursor cursor = database.rawQuery("SELECT * FROM DM_CHI WHERE ID_LOAI = ?", new String[]{lstldm.get(i).ID+""});
//                lstdmc.clear();
//                lst.clear();
//                int ID;
//                int ID_LOAI;
//                String MUC_CHI;
//                for (int j = 0; j < cursor.getCount(); j++)// cho chạy cursor là con tro
//                {
//                    cursor.moveToPosition(j);
//                    ID = cursor.getInt(0);
//                    ID_LOAI = cursor.getInt(2);
//                    MUC_CHI = cursor.getString(1);
//                    lstdmc.add(new DM_CHI(ID,ID_LOAI,MUC_CHI));
//                    lst.add(MUC_CHI);
//                }
//                arrayAdapter.notifyDataSetChanged();
//            }
//        });
        ReaddataDM_CHI();
        ReaddataDM_THU();
        ReaddataLoaiDM_CHI();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(AllHangMuc.this);
                dialog.setTitle("Sửa mục chi");
                dialog.setContentView(R.layout.activity_them_hangmucchi);
                dialog.setCancelable(false);
                btnLuuMucCHis = (Button) dialog.findViewById(R.id.buttonLuuHMC);
                btnHuyMucCHis = (Button) dialog.findViewById(R.id.buttonHuyHMC);
                spinnerThemMUCCHis = (Spinner) dialog.findViewById(R.id.spinnerHangMucChi);
                txtMucChis = (EditText) dialog.findViewById(R.id.editTextHangMucChi);
                spinnerThemMUCCHis.setAdapter(adapterLMC);
                txtMucChis.setText(lstdmc.get(i).MUC_CHI);
                final int j = i;
                spinnerThemMUCCHis.setSelection(Find(lstldm, lstdmc.get(i).ID_LOAI));
                btnLuuMucCHis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //////////////////////////////////sửa Mục chi/////////////////////////
                        try {
                            int ID_LOAI = spinnerThemMUCCHis.getSelectedItemPosition();
                            String MUC_CHI = txtMucChis.getText().toString();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("ID_LOAI", ID_LOAI);
                            contentValues.put("MUC_CHI", MUC_CHI);
                            SQLiteDatabase database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
                            database.update("DM_CHI", contentValues, "ID = ?", new String[]{lstdmc.get(j).ID + ""});
                            Toast.makeText(AllHangMuc.this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(AllHangMuc.this, "Sửa không thành công!!!", Toast.LENGTH_SHORT).show();
                        }
                        ReaddataDM_CHI();
                        dialog.dismiss();
                    }
                });
                btnHuyMucCHis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        ///////////////////////////////////////////////////////////////
        btnThemChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AllHangMuc.this);
                dialog.setTitle("Thêm mục chi");
                dialog.setContentView(R.layout.activity_them_hangmucchi);
                dialog.setCancelable(false);
                btnLuuMucCHi = (Button) dialog.findViewById(R.id.buttonLuuHMC);
                btnHuyMucCHi = (Button) dialog.findViewById(R.id.buttonHuyHMC);
                spinnerThemMUCCHi = (Spinner) dialog.findViewById(R.id.spinnerHangMucChi);
                txtMucChi = (EditText) dialog.findViewById(R.id.editTextHangMucChi);
                spinnerThemMUCCHi.setAdapter(adapterLMC);
                btnLuuMucCHi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ///////// viết code thêm o đây///////////////////////////////////
                        try {
                            int ID_LOAI = spinnerThemMUCCHi.getSelectedItemPosition();
                            String MUC_CHI = txtMucChi.getText().toString();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("ID_LOAI", ID_LOAI);
                            contentValues.put("MUC_CHI", MUC_CHI);

                            contentValues.put("ofUser", Login.idUser);
                            SQLiteDatabase database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
                            database.insert("DM_CHI", null, contentValues);
                            Toast.makeText(AllHangMuc.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(AllHangMuc.this, "Thêm không thành công!!!", Toast.LENGTH_SHORT).show();
                        }
                        ReaddataDM_CHI();
                        dialog.dismiss();
                    }
                });
                btnHuyMucCHi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        listViewDMThu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog1 = new Dialog(AllHangMuc.this);
                dialog1.setTitle("Sửa mục thu");
                dialog1.setContentView(R.layout.activity_them_hangmucthu);
                dialog1.setCancelable(false);
                btnLuuMucThus = (Button) dialog1.findViewById(R.id.btnluu1);
                btnHuyMucThus = (Button) dialog1.findViewById(R.id.btnHuy1);
                txtMucThus = (EditText) dialog1.findViewById(R.id.txtTenMucThu1);
                final int j = i;
                btnLuuMucThus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ////////// VIet phan code vao đây lưu/////////
                        try {

                            String MUC_CHI = txtMucThus.getText().toString();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("MUC_THU", MUC_CHI);
                            SQLiteDatabase database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
                            database.update("DM_THU", contentValues, "ID = ?", new String[]{lstdmc.get(j).ID + ""});
                            Toast.makeText(AllHangMuc.this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(AllHangMuc.this, "Sửa không thành công!!!", Toast.LENGTH_SHORT).show();
                        }
                        ReaddataDM_THU();
                        dialog1.dismiss();
                    }
                });
                btnHuyMucThus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });



        btnThemThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(AllHangMuc.this);
                dialog1.setTitle("Thêm mục thu");
                dialog1.setContentView(R.layout.activity_them_hangmucthu);
                dialog1.setCancelable(false);
                btnLuuMucThu = (Button) dialog1.findViewById(R.id.btnluu1);
                btnHuyMucThu = (Button) dialog1.findViewById(R.id.btnHuy1);
                txtMucThu = (EditText) dialog1.findViewById(R.id.txtTenMucThu1);
                btnLuuMucThu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ////////// VIet phan code vao đây lưu/////////
                        try {

                            String MUC_CHI = txtMucThu.getText().toString();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("MUC_THU", MUC_CHI);
                            contentValues.put("ofUser", Login.idUser);
                            SQLiteDatabase database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
                            database.insert("DM_THU", null, contentValues);
                            Toast.makeText(AllHangMuc.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(AllHangMuc.this, "Thêm không thành công!!!", Toast.LENGTH_SHORT).show();
                        }
                        ReaddataDM_THU();
                        dialog1.dismiss();
                    }
                });
                btnHuyMucThu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final Dialog dialog= new Dialog(AllHangMuc.this);
//                dialog.setTitle("Question");
//                dialog.setContentView(R.layout.diologxoa);
//                dialog.setCancelable(false);
//                Button btndongy=(Button)dialog.findViewById(R.id.btnDY);
//                Button btnkdy=(Button)dialog.findViewById(R.id.btnKhongDY);
//                final int j=i;
//                btndongy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        database = Database.initDatabase(AllHangMuc.this,DATABASE_NAME);
//                        database.delete("DM_CHI", "ID= ?",new String[]{lstdmc.get(j).ID+""});
//                        ReaddataDM_CHI();
//                        dialog.dismiss();
//                    }
//                });
//                btnkdy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                final int j = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(AllHangMuc.this);
                builder.setTitle("Xóa tài khoản này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                        database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
                        database.delete("DM_CHI", "ID= ?", new String[]{lstdmc.get(j).ID + ""});
                        ReaddataDM_CHI();
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
        listViewDMThu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(AllHangMuc.this);
                dialog.setTitle("Question");
                dialog.setContentView(R.layout.diologxoa);
                dialog.setCancelable(false);
                Button btndongy = (Button) dialog.findViewById(R.id.btnDY);
                Button btnkdy = (Button) dialog.findViewById(R.id.btnKhongDY);
                final int j = i;
                btndongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
                        database.delete("DM_THU", "ID= ?", new String[]{lstdmt.get(j).ID + ""});
                        ReaddataDM_THU();
                        dialog.dismiss();
                    }
                });
                btnkdy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });

    }


    private void ReaddataDM_CHI()// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        int kk = Login.idUser;
        Cursor cursor = database.rawQuery("SELECT * FROM DM_CHI WHERE ofUser = ? or ofUser = ?", new String[]{0 + "", Login.idUser + ""});
        lstdmc.clear();
        lst.clear();
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
            lstdmc.add(new DM_CHI(ID, ID_LOAI, MUC_CHI, ofUser));
            lst.add(MUC_CHI);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    void setSpinnerChi(int i) {
        database = Database.initDatabase(AllHangMuc.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DM_CHI WHERE ID_LOAI = ? and ( ofUser = ? or ofUser = ?)", new String[]{lstldm.get(i).ID + "", 0 + "", Login.idUser + ""});
        lstdmc.clear();
        lst.clear();
        int ID;
        int ID_LOAI;
        String MUC_CHI;
        int ofUser;
        for (int j = 0; j < cursor.getCount(); j++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(j);
            ID = cursor.getInt(0);
            ID_LOAI = cursor.getInt(2);
            MUC_CHI = cursor.getString(1);
            ofUser = Integer.parseInt(cursor.getString(3));
            lstdmc.add(new DM_CHI(ID, ID_LOAI, MUC_CHI, ofUser));
            lst.add(MUC_CHI);
        }
        arrayAdapter.notifyDataSetChanged();
    }
    private void ReaddataLoaiDM_CHI()// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM LOAI_DM_CHI", null);
        lstldm.clear();
        lstldm.add(new LOAI_DM_CHI(0, "Tất Cả"));
        int ID;
        String TEN_LOAI;
        for (int i = 0; i < cursor.getCount(); i++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            TEN_LOAI = cursor.getString(1);
            lstldm.add(new LOAI_DM_CHI(ID, TEN_LOAI));
        }
        adapterLMC.notifyDataSetChanged();
    }

    private void ReaddataDM_THU()// load dư lieu len list
    {
        database = Database.initDatabase(this, DATABASE_NAME);
        int kkk = Login.idUser;
        Cursor cursor = database.rawQuery("SELECT * FROM DM_THU WHERE ofUser = ? or ofUser = ?", new String[]{0 + "", Login.idUser + ""});
        lstdmt = new ArrayList<>();
        lstDMT.clear();
        int ID;
        String MUC_THU;
        int ofUser;
        for (int i = 0; i < cursor.getCount(); i++)// cho chạy cursor là con tro
        {
            cursor.moveToPosition(i);
            ID = cursor.getInt(0);
            MUC_THU = cursor.getString(1);
            ofUser = cursor.getInt(2);
            lstdmt.add(new DM_THU(ID, MUC_THU, ofUser));
            lstDMT.add(MUC_THU);
        }
        arrayAdapterDMT.notifyDataSetChanged();
    }

    void loadTab()
    {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhosthangmuc);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Hạng mục chi");
        spec1.setContent(R.id.tabhangchi);
        spec1.setIndicator("Hạng mục chi");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Hạng mục thu");
        spec2.setIndicator("Hạng mục thu");
        spec2.setContent(R.id.tabhangthu);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }

    int Find(ArrayList<LOAI_DM_CHI> a, int x) {
        for (int i = 0; i < a.size(); i++
                ) {
            if (a.get(i).ID == x)
                return i;

        }
        return -1;
    }


}
