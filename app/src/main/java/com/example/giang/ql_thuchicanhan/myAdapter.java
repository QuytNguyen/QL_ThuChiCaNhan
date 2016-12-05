package com.example.giang.ql_thuchicanhan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nguye on 30/11/2016.
 */
class TaiKhoanAdapter extends BaseAdapter {
    Activity context;
    String DATABASE_NAME = "misa.sqlite";
    private ArrayList<TAI_KHOAN> lst;
    private LayoutInflater inflater;//dùng để lấy ra file giao diện đã thiết kế (res/layot/

    public TaiKhoanAdapter(Activity context, ArrayList<TAI_KHOAN> list) {
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
        viewHolder.tvTienCon.setText(formatNumber(TAI_KHOAN.Tiencon(context, DATABASE_NAME, data.ID + "")));
        viewHolder.btnEdit.setImageResource(R.drawable.edit);

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
        TextView txtTaiKhoanChi = (TextView) row.findViewById(R.id.textViewItemTentaiKhoanChi);
        ImageView listView = (ImageView) row.findViewById(R.id.imageViewItemThu);
        ImageButton btnEditChi = (ImageButton) row.findViewById(R.id.btnEditChi);
        listView.setImageResource(R.drawable.vi);
        final ND_CHI chi = list.get(i);
        txtTenKhoanChi.setText(tenmucchi(listdm, chi.ID_MUC_CHI));
        txtSoTien.setText(TaiKhoanAdapter.formatNumber(chi.SO_TIEN));
        String tk = TaiKhoanChi(listtk, chi.TAI_KHOAN);
        txtTaiKhoanChi.setText(tk);
        btnEditChi.setTag(i);
        btnEditChi.setImageResource(R.drawable.edit);
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

class AdapterSpinnerKhoanThu extends BaseAdapter {
    Activity context;
    ArrayList<DM_THU> list;

    public AdapterSpinnerKhoanThu(ArrayList<DM_THU> list, Activity context) {
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
        View row = inflater.inflate(R.layout.spinnerkhoanthu, null);
        TextView txtTenKhoanThu = (TextView) row.findViewById(R.id.textViewSpinnerThu);
        // ImageView listView=(ImageView) row.findViewById(R.id.imageView);
        //listView.setImageResource(R.drawable.dm);
        final DM_THU thu = list.get(i);
        txtTenKhoanThu.setText(thu.MUC_THU);
        return row;
    }
}

class AdapterThu extends BaseAdapter {
    Activity context;
    ArrayList<ND_THU> list;
    ArrayList<DM_THU> listdm;
    ArrayList<TAI_KHOAN> listtk;

    public AdapterThu(Activity context, ArrayList<ND_THU> list, ArrayList<DM_THU> listdm, ArrayList<TAI_KHOAN> listtk) {
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
        View row = inflater.inflate(R.layout.itemlistthu, null);
        ImageButton btnSuaThu = (ImageButton) row.findViewById(R.id.btnSuaThu);
        TextView txtTenKhoanThu = (TextView) row.findViewById(R.id.textViewTenKhoanThuItem);
        TextView txtSoTien = (TextView) row.findViewById(R.id.textviewSoTienItemThu);
        TextView txtTaiKhoanThu = (TextView) row.findViewById(R.id.textViewItemTentaiKhoanThu);
        ImageView listView = (ImageView) row.findViewById(R.id.imageViewItemThu);
        listView.setImageResource(R.drawable.vi);
        final ND_THU thu = list.get(i);
        txtTenKhoanThu.setText(tenmucthu(listdm, thu.ID_MUC_THU));
        txtSoTien.setText(thu.SO_TIEN + "");
        txtTaiKhoanThu.setText(TaiKhoanThu(listtk, (int) thu.ID_TAI_KHOAN));
        btnSuaThu.setTag(i);
        btnSuaThu.setImageResource(R.drawable.edit);
        return row;
    }

    private String tenmucthu(ArrayList<DM_THU> lst, int x) {
        String kq = "";
        for (DM_THU dmthu : lst
                ) {
            if (x == dmthu.ID)
                kq = dmthu.MUC_THU;
        }
        return kq;
    }

    private String TaiKhoanThu(ArrayList<TAI_KHOAN> lst, int x) {
        String kq = "";
        for (TAI_KHOAN dmthu : lst
                ) {
            if (x == dmthu.ID)
                kq = dmthu.TEN_TAI_KHOAN;
        }
        return kq;
    }


}

class AdapterDMT extends BaseAdapter {
    Activity context;
    ArrayList<DM_THU> list;

    public AdapterDMT(Activity context, ArrayList<DM_THU> list) {
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
        View row = inflater.inflate(R.layout.spinnerkhoanthu, null);
        TextView txtTenKhoanThu = (TextView) row.findViewById(R.id.textViewSpinnerThu);
        final DM_THU thu = list.get(i);
        txtTenKhoanThu.setText(thu.MUC_THU);
        return row;
    }
}
//class  AdapterLoaiTK extends  BaseAdapter {
//    Activity context;
//    ArrayList<LOAI_TAI_KHOAN> list;
//
//    public AdapterLoaiTK(Activity context, ArrayList<LOAI_TAI_KHOAN> list) {
//        this.context = context;
//        this.list = list;
//    }
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = inflater.inflate(R.layout.itemlistloaitk, null);
//        TextView txtTenKhoanThu = (TextView) row.findViewById(R.id.textViewTKhoan);
////        listView.setImageResource(imgid[i]);
//        final LOAI_TAI_KHOAN thu = list.get(i);
//        txtTenKhoanThu.setText(thu.TEN_LOAI);
//        return row;
//    }
//}
//
//class AdapterTaiKHoan extends  BaseAdapter{
//    Activity context;
//    ArrayList<TAI_KHOAN> list;
//
//    public AdapterTaiKHoan(Activity context, ArrayList<TAI_KHOAN> list) {
//        this.context = context;
//        this.list = list;
//    }
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = inflater.inflate(R.layout.itemlisttaikhoan, null);
//        TextView txtTenKhoanThu = (TextView) row.findViewById(R.id.textViewTK);
//        TextView txtSoTien = (TextView) row.findViewById(R.id.textView2);
//        //ImageView listView = (ImageView) row.findViewById(R.id.imageView2);
//        final TAI_KHOAN thu = list.get(i);
//        txtTenKhoanThu.setText(thu.TEN_TAI_KHOAN);
//        txtSoTien.setText(formatNumber(thu.SO_TIEN));
//
//        return row;
//    }
//    public static String formatNumber(double number) {
//        try {
//            NumberFormat formatter = new DecimalFormat("###,###");
//            String resp = formatter.format(number);
//            resp = resp.replaceAll(",", ".");
//            return resp;
//        } catch (Exception e) {
//            return "";
//        }
//    }
//}
class AdapterlistThongkeHientai extends BaseAdapter {

    Activity context;
    ArrayList<ND_CHI> listCHI;
    ArrayList<ND_THU> listTHU;
    int[] lstImg = new int[]{R.drawable.date1, R.drawable.date7, R.drawable.date30, R.drawable.date365};
    TextView tvTongChi, tvTongThu;

    public AdapterlistThongkeHientai(Activity context, ArrayList<ND_CHI> listCHI, ArrayList<ND_THU> listTHU, TextView tvTongChi, TextView tvTongThu) {
        this.context = context;
        this.listCHI = listCHI;
        this.listTHU = listTHU;
        this.tvTongChi = tvTongChi;
        this.tvTongThu = tvTongThu;

    }

    @Override
    public int getCount() {
        return 4;
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
        View row = inflater.inflate(R.layout.itemslistviewthongke, null);
        TextView tvTenNgay = (TextView) row.findViewById(R.id.textView32);
        TextView tvThu = (TextView) row.findViewById(R.id.textView37);
        TextView tvChi = (TextView) row.findViewById(R.id.textView38);

        ImageView listView = (ImageView) row.findViewById(R.id.imageView2);
        switch (i) {
            case 0:
                listView.setImageResource(lstImg[0]);
                tvTenNgay.setText(HienTai());
                tvChi.setText(TaiKhoanAdapter.formatNumber(TongChiHienTai()));
                tvThu.setText(TaiKhoanAdapter.formatNumber(TongChiHienTaiThu()));
                break;
            case 1:
                listView.setImageResource(lstImg[1]);
                tvTenNgay.setText(Tuan());
                tvChi.setText(TaiKhoanAdapter.formatNumber(TOngChiTuan()));
                tvThu.setText(TaiKhoanAdapter.formatNumber(TOngChiTuanThu()));
                break;
            case 2:
                listView.setImageResource(lstImg[2]);
                tvTenNgay.setText(Thang());
                tvChi.setText(TaiKhoanAdapter.formatNumber(TongChiThang()));
                tvThu.setText(TaiKhoanAdapter.formatNumber(TongChiThangThu()));
                break;
            case 3:
                listView.setImageResource(lstImg[3]);
                tvTenNgay.setText(Nam());
                tvChi.setText(TaiKhoanAdapter.formatNumber(TongChiNam()));
                tvThu.setText(TaiKhoanAdapter.formatNumber(TongChiNamThu()));
                break;
        }
        tvTongChi.setText(TaiKhoanAdapter.formatNumber(TongChiNam()));
        tvTongThu.setText(TaiKhoanAdapter.formatNumber(TongChiNamThu()));
        return row;
    }

    String HienTai() {
        Date ngay = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return "HienTai" + " (" + sdf.format(ngay) + " )";
    }

    String Tuan() {
        java.util.Date ngay = new Date();
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setTime(ngay);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //String currentWeek = sdf.format(calendar);
        /////
        Calendar calendar1 = Calendar.getInstance(Locale.GERMANY);
        calendar1.clear();
        calendar1.set(Calendar.WEEK_OF_YEAR, week);
        calendar1.set(Calendar.YEAR, 2016);

// Now get the first day of week.
        Date date = calendar1.getTime();
        calendar1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String chunhat = sdf.format(calendar1.getTime());
        ///
        return "Tuần" + "( " + sdf.format(date) + " - " + chunhat + " )";
    }

    String Thang() {
        java.util.Date ngay = new Date();
        Calendar cal = Calendar.getInstance(Locale.GERMANY);
        cal.setTime(ngay);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return "Tháng" + " (" + month + "-" + year + " )";
    }

    String Nam() {
        java.util.Date ngay = new Date();
        Calendar cal = Calendar.getInstance(Locale.GERMANY);
        cal.setTime(ngay);
        int year = cal.get(Calendar.YEAR);
        return "Năm" + " (" + year + " )";
    }

    double TongChiHienTai() {
        double tong = 0;
        for (ND_CHI chi : listCHI
                ) {
            Date ngay = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if (sdf.format(ngay).compareTo(sdf.format(chi.NGAY_CHI)) == 0)
                tong += chi.SO_TIEN;
        }
        return tong;
    }

    double TongChiNam() {
        double tong = 0;
        for (ND_CHI chi : listCHI
                ) {
            java.util.Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int namnow = cal.get(Calendar.YEAR);
            cal.setTime(chi.NGAY_CHI);
            int nam = cal.get(Calendar.YEAR);
            if (nam == namnow) {
                tong += chi.SO_TIEN;
            }
        }
        return tong;
    }

    double TongChiThang() {
        double tong = 0;
        for (ND_CHI chi : listCHI
                ) {
            java.util.Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int monthnow = cal.get(Calendar.MONTH);
            cal.setTime(chi.NGAY_CHI);
            int month = cal.get(Calendar.MONTH);
            if (month == monthnow) {
                tong += chi.SO_TIEN;
            }
        }
        return tong;
    }

    double TOngChiTuan() {
        double tong = 0;
        for (ND_CHI chi : listCHI
                ) {
            java.util.Date ngay = new Date();
            Calendar calendar = Calendar.getInstance(Locale.GERMANY);
            calendar.setTime(ngay);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            int year = calendar.get(calendar.YEAR);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //String currentWeek = sdf.format(calendar);
            /////
            Calendar calendar1 = Calendar.getInstance(Locale.GERMANY);
            calendar1.clear();
            calendar1.set(Calendar.WEEK_OF_YEAR, week);
            calendar1.set(Calendar.YEAR, 2016);

// Now get the first day of week.
            Date date = calendar1.getTime();
            calendar1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date date1 = calendar1.getTime();
            // ngayabc = Calendar.getInstance().getTime();
            if (chi.NGAY_CHI.after(date) && chi.NGAY_CHI.before(date1)) {
                // In between
                tong += chi.SO_TIEN;
            }
        }
        return tong;
    }

    double TongChiHienTaiThu() {
        double tong = 0;
        for (ND_THU chi : listTHU
                ) {
            Date ngay = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if (sdf.format(ngay).compareTo(sdf.format(chi.NGAY_THU)) == 0)
                tong += chi.SO_TIEN;
        }
        return tong;
    }

    double TongChiNamThu() {
        double tong = 0;
        for (ND_THU chi : listTHU
                ) {
            java.util.Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int namnow = cal.get(Calendar.YEAR);
            cal.setTime(chi.NGAY_THU);
            int nam = cal.get(Calendar.YEAR);
            if (nam == namnow) {
                tong += chi.SO_TIEN;
            }
        }
        return tong;
    }

    double TongChiThangThu() {
        double tong = 0;
        for (ND_THU chi : listTHU
                ) {
            java.util.Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int monthnow = cal.get(Calendar.MONTH);
            cal.setTime(chi.NGAY_THU);
            int month = cal.get(Calendar.MONTH);
            if (month == monthnow) {
                tong += chi.SO_TIEN;
            }
        }
        return tong;
    }

    double TOngChiTuanThu() {
        double tong = 0;
        for (ND_THU chi : listTHU
                ) {
            java.util.Date ngay = new Date();
            Calendar calendar = Calendar.getInstance(Locale.GERMANY);
            calendar.setTime(ngay);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            int year = calendar.get(calendar.YEAR);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //String currentWeek = sdf.format(calendar);
            /////
            Calendar calendar1 = Calendar.getInstance(Locale.GERMANY);
            calendar1.clear();
            calendar1.set(Calendar.WEEK_OF_YEAR, week);
            calendar1.set(Calendar.YEAR, 2016);

// Now get the first day of week.
            Date date = calendar1.getTime();
            calendar1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date date1 = calendar1.getTime();
            // ngayabc = Calendar.getInstance().getTime();
            if (chi.NGAY_THU.after(date) && chi.NGAY_THU.before(date1)) {
                // In between
                tong += chi.SO_TIEN;
            }
        }
        return tong;
    }

}

