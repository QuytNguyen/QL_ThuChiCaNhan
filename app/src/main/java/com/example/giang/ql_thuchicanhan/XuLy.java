package com.example.giang.ql_thuchicanhan;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
}
