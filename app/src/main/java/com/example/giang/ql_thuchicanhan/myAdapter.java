package com.example.giang.ql_thuchicanhan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by nguye on 30/11/2016.
 */
class TaiKhoanAdapter extends BaseAdapter {
    Context context;
    private ArrayList<TAI_KHOAN> lst;
    private LayoutInflater inflater;//dùng để lấy ra file giao diện đã thiết kế (res/layot/

    public TaiKhoanAdapter(Context context, ArrayList<TAI_KHOAN> list) {
        lst = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public static String formatNumber(double number) {
        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replaceAll(",", ".");
            return resp;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public int getCount() {//sl cần để vẽ danh sách
        return lst.size();
    }

    @Override
    public Object getItem(int i) {//lấy phần tử
        return null;
    }

    @Override
    public long getItemId(int i) {//
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {//vẽ giao diện 1 dòng
        ViewHolder viewHolder = null;//thiêt lập liên kết đến các đối tượng trong 1 item của listview
        //tránh việc findviewbyid quá nhiều
        if (view == null) {
            view = inflater.inflate(R.layout.tai_khoan_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTen = (TextView) view.findViewById(R.id.tvTen);
            viewHolder.tvTienCon = (TextView) view.findViewById(R.id.tvTienCon);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
            view.setTag(viewHolder);//dùng để lưu viewholder vào một item của listview
        } else {
            viewHolder = (ViewHolder) view.getTag();//lấy viewholder đã thiết lặp từ trước
        }
        TAI_KHOAN data = lst.get(i);
        viewHolder.tvTen.setText(data.TEN_TAI_KHOAN);
        viewHolder.btnEdit.setTag(i);
        //viewHolder.tvTienCon.setText("");
//        int resID = context.getResources().getIdentifier(data.getHinh(),
//                "drawable", context.getPackageName());
//        viewHolder.imgv.setImageResource(resID);//v.setImageResource(resID);

        return view;
    }

    private class ViewHolder {
        TextView tvTen, tvTienCon;
        ImageView imageView;
        ImageButton btnEdit;
    }
}

class AdapterSpinnerKhoanChi extends BaseAdapter {
    Activity context;
    ArrayList<DM_CHI> list;

    public AdapterSpinnerKhoanChi(ArrayList<DM_CHI> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.itemspinerkhoanchi, null);
        TextView txtTenKhoanChi = (TextView) row.findViewById(R.id.textView22);
        ImageView listView = (ImageView) row.findViewById(R.id.imageView);
        listView.setImageResource(R.drawable.dm);
        final DM_CHI chi = list.get(i);
        txtTenKhoanChi.setText(chi.MUC_CHI);
        return row;
    }
}

class AdapterSpinnerTaiKhoan extends BaseAdapter {
    Activity context;
    ArrayList<TAI_KHOAN> list;

    public AdapterSpinnerTaiKhoan(Activity context, ArrayList<TAI_KHOAN> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.itemspinertaikhoan, null);
        TextView txtTenKhoanChi = (TextView) row.findViewById(R.id.textView123);
        ImageView listView = (ImageView) row.findViewById(R.id.imageView123);
        listView.setImageResource(R.drawable.bankk);
        final TAI_KHOAN chi = list.get(i);
        txtTenKhoanChi.setText(chi.TEN_TAI_KHOAN);
        return row;
    }

}

class AdapterChi extends BaseAdapter {
    Activity context;
    ArrayList<ND_CHI> list;
    ArrayList<DM_CHI> listdm;
    ArrayList<TAI_KHOAN> listtk;

    public AdapterChi(Activity context, ArrayList<ND_CHI> list, ArrayList<DM_CHI> listdm, ArrayList<TAI_KHOAN> listtk) {
        this.context = context;
        this.list = list;
        this.listdm = listdm;
        this.listtk = listtk;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.itemlistchi, null);
        TextView txtTenKhoanChi = (TextView) row.findViewById(R.id.textViewTenKhoanChiItem);
        TextView txtSoTien = (TextView) row.findViewById(R.id.textviewSoTienItem);
        TextView txtTaiKhoanChi = (TextView) row.findViewById(R.id.textViewItemTentaiKhoa);
        ImageView listView = (ImageView) row.findViewById(R.id.imageViewItemChi);
        listView.setImageResource(R.drawable.vi);
        final ND_CHI chi = list.get(i);
        txtTenKhoanChi.setText(tenmucchi(listdm, chi.ID_MUC_CHI));
        txtSoTien.setText(TaiKhoanAdapter.formatNumber(chi.SO_TIEN));
        txtTaiKhoanChi.setText(TaiKhoanChi(listtk, chi.TAI_KHOAN));
        return row;
    }

    private String tenmucchi(ArrayList<DM_CHI> lst, int x) {
        String kq = "";
        for (DM_CHI dmchi : lst
                ) {
            if (x == dmchi.ID)
                kq = dmchi.MUC_CHI;
        }
        return kq;
    }

    private String TaiKhoanChi(ArrayList<TAI_KHOAN> lst, int x) {
        String kq = "";
        for (TAI_KHOAN dmchi : lst
                ) {
            if (x == dmchi.ID)
                kq = dmchi.TEN_TAI_KHOAN;
        }
        return kq;
    }


}

class AdapterLMC extends BaseAdapter {
    Activity context;
    ArrayList<LOAI_DM_CHI> list;

    public AdapterLMC(Activity context, ArrayList<LOAI_DM_CHI> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.itemspinerkhoanchi, null);
        TextView txtTenKhoanChi = (TextView) row.findViewById(R.id.textView22);
        ImageView listView = (ImageView) row.findViewById(R.id.imageView);
        listView.setImageResource(R.drawable.book);
        final LOAI_DM_CHI chi = list.get(i);
        txtTenKhoanChi.setText(chi.TEN_LOAI);
        return row;
    }
}