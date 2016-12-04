package com.example.giang.ql_thuchicanhan;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by nguye on 30/11/2016.
 */
public class XuLy {
    //TRuyền dữ liệu lên trang web
    //TRung gian để thêm, sửa, xóa
    public static String displayDataFromTable(String url, String table_name) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(1);
        nameValuePair.add(new BasicNameValuePair("table_name", table_name));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }

    //Đọc dữ liệu trên trang web về
    public static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object

            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static String insertData(String url, String table_name, Hashtable<String, String> data) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair;

        if (table_name == "TAI_KHOAN") {
            nameValuePair = new ArrayList(7);
            nameValuePair.add(new BasicNameValuePair("table_name", table_name));
            nameValuePair.add(new BasicNameValuePair("TEN_TAI_KHOAN", data.get("TEN_TAI_KHOAN")));
            nameValuePair.add(new BasicNameValuePair("ID_NGUOI_DUNG", data.get("ID_NGUOI_DUNG")));
            nameValuePair.add(new BasicNameValuePair("SO_TIEN", data.get("SO_TIEN")));
            nameValuePair.add(new BasicNameValuePair("NGAY_TAO", data.get("NGAY_TAO")));
            nameValuePair.add(new BasicNameValuePair("LOAI_TAI_KHOAN", data.get("LOAI_TAI_KHOAN")));
            nameValuePair.add(new BasicNameValuePair("GHI_CHU", data.get("GHI_CHU")));
            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String kq = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                kq = EntityUtils.toString(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return kq;
        }
        if (table_name == "NGUOI_DUNG") {
            nameValuePair = new ArrayList(3);
            nameValuePair.add(new BasicNameValuePair("table_name", table_name));
            nameValuePair.add(new BasicNameValuePair("USER", data.get("USER")));
            nameValuePair.add(new BasicNameValuePair("PASS", data.get("PASS")));
            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String kq = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                kq = EntityUtils.toString(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return kq;
        }
        return null;
    }


    public static String updateData(String url, String table_name, Hashtable<String, String> data, int id) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair;

        if (table_name == "TAI_KHOAN") {
            nameValuePair = new ArrayList(8);
            nameValuePair.add(new BasicNameValuePair("table_name", table_name));
            nameValuePair.add(new BasicNameValuePair("ID", id + ""));
            nameValuePair.add(new BasicNameValuePair("TEN_TAI_KHOAN", data.get("TEN_TAI_KHOAN")));
            nameValuePair.add(new BasicNameValuePair("ID_NGUOI_DUNG", data.get("ID_NGUOI_DUNG")));
            nameValuePair.add(new BasicNameValuePair("SO_TIEN", data.get("SO_TIEN")));
            nameValuePair.add(new BasicNameValuePair("NGAY_TAO", data.get("NGAY_TAO")));
            nameValuePair.add(new BasicNameValuePair("LOAI_TAI_KHOAN", data.get("LOAI_TAI_KHOAN")));
            nameValuePair.add(new BasicNameValuePair("GHI_CHU", data.get("GHI_CHU")));
            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String kq = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                kq = EntityUtils.toString(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return kq;
        }
        return null;
    }

    public static void syncSQLiteMySQLDB() {
        //Create AsycHttpClient object
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();
//        if(userList.size()!=0){
//            if(controller.dbSyncCount() != 0){
//                prgDialog.show();
//                params.put("usersJSON", controller.composeJSONfromSQLite());
//                client.post("http://192.168.2.4:9000/sqlitemysqlsync/insertuser.php",params ,new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                        System.out.println(response);
//                        prgDialog.hide();
//                        try {
//                            JSONArray arr = new JSONArray(response);
//                            System.out.println(arr.length());
//                            for(int i=0; i<arr.length();i++){
//                                JSONObject obj = (JSONObject)arr.get(i);
//                                System.out.println(obj.get("id"));
//                                System.out.println(obj.get("status"));
//                                controller.updateSyncStatus(obj.get("id").toString(),obj.get("status").toString());
//                            }
//                            Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            Toast.makeText(getApplicationContext(), "Error Occured" +
//                                    " [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Throwable error,
//                                          String content) {
//                        // TODO Auto-generated method stub
//                        prgDialog.hide();
//                        if(statusCode == 404){
//                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
//                        }else if(statusCode == 500){
//                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }else{
//                Toast.makeText(getApplicationContext(), "SQLite and Remote MySQL DBs are in Sync!", Toast.LENGTH_LONG).show();
//            }
//        }else{
//            Toast.makeText(getApplicationContext(), "No data in SQLite DB, please do enter User name to perform Sync action", Toast.LENGTH_LONG).show();
//        }
    }
}
