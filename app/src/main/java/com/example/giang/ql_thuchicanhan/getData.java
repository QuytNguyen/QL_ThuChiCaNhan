package com.example.giang.ql_thuchicanhan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nguye on 01/12/2016.
 */
public class getData extends AsyncTask<String, Integer, String> {
    String table_name;
    ArrayList list;
    ProgressDialog progressDialog;
    Context context;

    public getData(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Progress Dialog Title Text", "Process Description Text", true);
    }

    @Override
    protected String doInBackground(String... strings) {

        table_name = strings[0];
        String url = strings[1];
        return XuLy.displayDataFromTable(url, table_name);
    }

    @Override
    protected void onPostExecute(String s) {
        if (table_name == "NGUOI_DUNG") {
            list = getNguoiDungArrayList(s);
        }
        if (table_name == "TAI_KHOAN") {
            list = getTaiKhoanArrayList(s);
        }
        progressDialog.dismiss();
    }

    public ArrayList<NGUOI_DUNG> getNguoiDungArrayList(String s) {
        ArrayList<NGUOI_DUNG> list = new ArrayList<>();
        try {
            int id;
            String user, pass;

            JSONArray arr = new JSONArray(s);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json_object = arr.getJSONObject(i);
                id = json_object.getInt("ID");
                user = json_object.getString("USER");
                pass = json_object.getString("PASS");

                list.add(new NGUOI_DUNG(id, user, pass));
            }
        } catch (JSONException e) {
        }
        return list;
    }

    public ArrayList<TAI_KHOAN> getTaiKhoanArrayList(String s) {
        ArrayList<TAI_KHOAN> list = new ArrayList<>();
        try {
            int ID;
            String TEN_TAI_KHOAN;
            int ID_NGUOI_DUNG;
            double SO_TIEN;
            Date NGAY_TAO;
            int LOAI_TAI_KHOAN;
            String GHI_CHU;
            int ofUser;

            JSONArray arr = new JSONArray(s);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json_object = arr.getJSONObject(i);
                ID = json_object.getInt("ID");
                TEN_TAI_KHOAN = json_object.getString("TEN_TAI_KHOAN");
                ID_NGUOI_DUNG = json_object.getInt("ID_NGUOI_DUNG");
                SO_TIEN = json_object.getInt("SO_TIEN");
                NGAY_TAO = new Date(2016, 1, 1);//json_object.getString("NGAY_TAO");
                LOAI_TAI_KHOAN = json_object.getInt("LOAI_TAI_KHOAN");
                GHI_CHU = json_object.getString("GHI_CHU");
                ofUser = json_object.getInt("ofUser");

                list.add(new TAI_KHOAN(ID, TEN_TAI_KHOAN, ID_NGUOI_DUNG, SO_TIEN, NGAY_TAO, LOAI_TAI_KHOAN, GHI_CHU, ofUser));
            }
        } catch (JSONException e) {
        }
        return list;
    }

}
