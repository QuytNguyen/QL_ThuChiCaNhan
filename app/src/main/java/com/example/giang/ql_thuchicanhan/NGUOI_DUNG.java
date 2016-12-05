package com.example.giang.ql_thuchicanhan;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nguye on 30/11/2016.
 */
class NGUOI_DUNG {
    int ID;
    String USER;
    String PASS;

    public NGUOI_DUNG(int ID, String USER, String PASS) {
        this.ID = ID;
        this.USER = USER;
        this.PASS = PASS;
    }
}

class LOAI_TAI_KHOAN {
    int ID;
    String TEN_LOAI;

    public LOAI_TAI_KHOAN() {

    }


    public LOAI_TAI_KHOAN(int ID, String TEN_LOAI) {
        this.ID = ID;
        this.TEN_LOAI = TEN_LOAI;
    }

    public ArrayList<LOAI_TAI_KHOAN> getLOAI_TAI_KHOAN(Activity activity, SQLiteDatabase database, String DATABASE_NAME) {
        ArrayList<LOAI_TAI_KHOAN> list = new ArrayList<>();
        database = Database.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM LOAI_TAI_KHOAN", null);
        for (int i = 0; i < cursor.getCount(); i++)// cho ch?y cursor là con tro
        {
            cursor.moveToPosition(i);
            int ID = cursor.getInt(0);
            String TEN_LOAI = cursor.getString(1);
            list.add(new LOAI_TAI_KHOAN(ID, TEN_LOAI));
        }
        return list;
    }
}

class TAI_KHOAN implements Serializable {
    ////////////////edit
    int ID;
    String TEN_TAI_KHOAN;
    int ID_NGUOI_DUNG;
    double SO_TIEN;
    Date NGAY_TAO;
    int LOAI_TAI_KHOAN;
    String GHI_CHU;

    public TAI_KHOAN(int ID, String TEN_TAI_KHOAN, int ID_NGUOI_DUNG, double SO_TIEN, Date NGAY_TAO, int LOAI_TAI_KHOAN, String GHI_CHU) {
        this.ID = ID;
        this.TEN_TAI_KHOAN = TEN_TAI_KHOAN;
        this.ID_NGUOI_DUNG = ID_NGUOI_DUNG;
        this.SO_TIEN = SO_TIEN;
        this.NGAY_TAO = NGAY_TAO;
        this.LOAI_TAI_KHOAN = LOAI_TAI_KHOAN;
        this.GHI_CHU = GHI_CHU;
    }

    public TAI_KHOAN(int ID, String TEN_TAI_KHOAN) {
        this.ID = ID;
        this.TEN_TAI_KHOAN = TEN_TAI_KHOAN;
    }

    public TAI_KHOAN() {
    }

    public static double Tiencon(Activity activity, String DATABASE_NAME, String ID_TAI_KHOAN) {
        SQLiteDatabase database = Database.initDatabase(activity, DATABASE_NAME);
        Cursor sotienChi = database.rawQuery("SELECT sum(SO_TIEN) from ND_CHI Where ID_TAI_KHOAN = ?", new String[]{ID_TAI_KHOAN});
        double asotienCHi = 0;
        try {

            sotienChi.moveToPosition(0);
            asotienCHi = sotienChi.getDouble(0);
        } catch (Exception ex) {
            asotienCHi = 0;
        }


        Cursor sotienThu = database.rawQuery("SELECT sum(SO_TIEN) from ND_THU Where ID_TAI_KHOAN = ?", new String[]{ID_TAI_KHOAN});
        double asotienthu = 0;
        try {

            sotienThu.moveToPosition(0);
            asotienthu = sotienThu.getDouble(0);
        } catch (Exception ex) {
            asotienthu = 0;
        }
        Cursor tongtien = database.rawQuery("SELECT * FROM TAI_KHOAN where ID =?", new String[]{ID_TAI_KHOAN});
        double tongtiena = 0;
        try {

            tongtien.moveToPosition(0);
            tongtiena = tongtien.getDouble(3);
        } catch (Exception ex) {
            tongtiena = 0;
        }
        return tongtiena - asotienCHi + asotienthu;

    }

    public ArrayList<TAI_KHOAN> getTAI_KHOAN(Activity activity, SQLiteDatabase database, String DATABASE_NAME) {
        ArrayList<TAI_KHOAN> list = new ArrayList<>();
        database = Database.initDatabase(activity, DATABASE_NAME);
        String idUser = Login.idUser + "";
        Cursor cursor = database.rawQuery("SELECT * FROM TAI_KHOAN where ID_NGUOI_DUNG = ?", new String[]{idUser});
        for (int i = 0; i < cursor.getCount(); i++)// cho ch?y cursor là con tro
        {
            cursor.moveToPosition(i);
            int ID = cursor.getInt(0);
            String TEN_TAI_KHOAN = cursor.getString(1);
            this.ID_NGUOI_DUNG = cursor.getInt(2);
            this.SO_TIEN = cursor.getDouble(3);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_TAO = new Date(temp);
            this.LOAI_TAI_KHOAN = cursor.getInt(5);
            this.GHI_CHU = cursor.getString(6);
            list.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU));
        }
        return list;
    }

    public ArrayList<TAI_KHOAN> getTAI_KHOAN(Activity activity, SQLiteDatabase database, String DATABASE_NAME, int k) {
        ArrayList<TAI_KHOAN> list = new ArrayList<>();
        database = Database.initDatabase(activity, DATABASE_NAME);
        String idUser = Login.idUser + "";
        Cursor cursor = database.rawQuery("SELECT * FROM TAI_KHOAN where ID_NGUOI_DUNG = ? and LOAI_TAI_KHOAN =?", new String[]{idUser, k + ""});
        for (int i = 0; i < cursor.getCount(); i++)// cho ch?y cursor là con tro
        {
            cursor.moveToPosition(i);
            int ID = cursor.getInt(0);
            String TEN_TAI_KHOAN = cursor.getString(1);
            this.ID_NGUOI_DUNG = cursor.getInt(2);
            this.SO_TIEN = cursor.getDouble(3);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String temp = cursor.getString(4);
            Date NGAY_TAO = new Date(temp);
            this.LOAI_TAI_KHOAN = cursor.getInt(5);
            this.GHI_CHU = cursor.getString(6);
            list.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU));
        }
        return list;
    }

    public void insertTAI_KHOAN(Activity activity, String DATABASE_NAME, TAI_KHOAN tk) {
        SQLiteDatabase database = Database.initDatabase(activity, DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put("TEN_TAI_KHOAN", tk.TEN_TAI_KHOAN);
        contentValues.put("ID_NGUOI_DUNG", tk.ID_NGUOI_DUNG);
        contentValues.put("SO_TIEN", tk.SO_TIEN);
        contentValues.put("NGAY_TAO", tk.NGAY_TAO.toString());
        contentValues.put("LOAI_TAI_KHOAN", tk.LOAI_TAI_KHOAN);
        contentValues.put("GHI_CHU", tk.GHI_CHU);
        contentValues.put("ofUser", Login.idUser);
        database.insert("TAI_KHOAN", null, contentValues);
    }

    public void updateTAI_KHOAN(Activity activity, String DATABASE_NAME, TAI_KHOAN tk) {
        SQLiteDatabase database = Database.initDatabase(activity, DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put("TEN_TAI_KHOAN", tk.TEN_TAI_KHOAN);
        contentValues.put("SO_TIEN", tk.SO_TIEN);
        contentValues.put("LOAI_TAI_KHOAN", tk.LOAI_TAI_KHOAN);
        contentValues.put("GHI_CHU", tk.GHI_CHU);
        database.update("TAI_KHOAN", contentValues, "ID = ?", new String[]{String.valueOf(tk.ID)});
    }

    public void deleteTAI_KHOAN(Activity activity, String DATABASE_NAME, TAI_KHOAN tk) {
        SQLiteDatabase database = Database.initDatabase(activity, DATABASE_NAME);
        database.delete("TAI_KHOAN", "ID= ?", new String[]{String.valueOf(tk.ID)});
    }
}

class LOAI_DM_CHI {
    int ID;
    String TEN_LOAI;

    public LOAI_DM_CHI(int ID, String TEN_LOAI) {
        this.ID = ID;
        this.TEN_LOAI = TEN_LOAI;
    }
}

class DM_CHI {
    int ID;
    int ID_LOAI;
    String MUC_CHI;
    int ofUser;

    public DM_CHI(int ID, int ID_LOAI, String MUC_CHI, int ofUser) {
        this.ID = ID;
        this.ID_LOAI = ID_LOAI;
        this.MUC_CHI = MUC_CHI;
        this.ofUser = ofUser;
    }
}

class DM_THU {
    int ID;
    String MUC_THU;
    int ofUser;


    public DM_THU(int ID, String MUC_THU, int ofUser) {
        this.ID = ID;
        this.MUC_THU = MUC_THU;
        this.ofUser = ofUser;
    }
}

class ND_CHI {
    int ID;
    int ID_MUC_CHI;
    double SO_TIEN;
    int TAI_KHOAN;
    Date NGAY_CHI;
    String CHI_CHO;
    String GHI_CHU;

    public ND_CHI(int ID, int ID_MUC_CHI, double SO_TIEN, int TAI_KHOAN, Date NGAY_CHI, String CHI_CHO, String GHI_CHU) {
        this.ID = ID;
        this.ID_MUC_CHI = ID_MUC_CHI;
        this.SO_TIEN = SO_TIEN;
        this.TAI_KHOAN = TAI_KHOAN;
        this.NGAY_CHI = NGAY_CHI;
        this.CHI_CHO = CHI_CHO;
        this.GHI_CHU = GHI_CHU;
    }

    public ND_CHI(int ID, int ID_MUC_CHI, double SO_TIEN, int TAI_KHOAN, String CHI_CHO, String GHI_CHU) {
        this.ID = ID;
        this.ID_MUC_CHI = ID_MUC_CHI;
        this.SO_TIEN = SO_TIEN;
        this.TAI_KHOAN = TAI_KHOAN;
        this.CHI_CHO = CHI_CHO;
        this.GHI_CHU = GHI_CHU;
    }
}

class ND_THU {
    int ID;
    int ID_MUC_THU;
    double SO_TIEN;
    int ID_TAI_KHOAN;
    Date NGAY_THU;
    String GHI_CHU;

    public ND_THU(int ID, int ID_MUC_THU, double SO_TIEN, int ID_TAI_KHOAN, Date NGAY_THU, String GHI_CHU) {
        this.ID = ID;
        this.ID_MUC_THU = ID_MUC_THU;
        this.SO_TIEN = SO_TIEN;
        this.ID_TAI_KHOAN = ID_TAI_KHOAN;
        this.NGAY_THU = NGAY_THU;
        this.GHI_CHU = GHI_CHU;
    }
}

