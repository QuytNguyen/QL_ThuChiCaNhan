package com.example.giang.ql_thuchicanhan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Itemlist> listitem = GetSearchResults();
        listView= (ListView)findViewById(R.id.list);
        listView.setAdapter(new BinderData(this,listitem));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent chi = new Intent(Main.this,chi.class);
                        startActivity(chi);
                        break;
                    case 1:
                        Intent thu = new Intent(Main.this,Thu.class);
                        startActivity(thu);
                        break;
                    case 2:
                        Intent no = new Intent(Main.this,No.class);
                        startActivity(no);
                        break;
                    case 3:
                        Intent vay = new Intent(Main.this,vay.class);
                        startActivity(vay);
                        break;
                    case 4:
                        Intent taikhoan = new Intent(Main.this,danhmuctaikhoan.class);
                        startActivity(taikhoan);
                        break;
                    case 5:
                        Intent hangmuc = new Intent(Main.this,AllHangMuc.class);
                        startActivity(hangmuc);
                        break;
                    case 6:
                        Intent danhmucmuasam = new Intent(Main.this,DanhMucMuaSam.class);
                        startActivity(danhmucmuasam);
                        break;
                    case 7:
                        Intent thongke = new Intent(Main.this,ThongKe.class);
                        startActivity(thongke);
                        break;
                    case 8:
                        Intent thietlap = new Intent(Main.this,ThietLap.class);
                        startActivity(thietlap);
                        break;
                    case 9:
                        Intent huongdan = new Intent(Main.this,huongdan.class);
                        startActivity(huongdan);
                        break;


                }
            }
        });
    }

    private ArrayList<Itemlist> GetSearchResults() {
        ArrayList<Itemlist> results = new ArrayList<Itemlist>();

        Itemlist item_details = new Itemlist();
        item_details.setName("Sổ ghi chi");
        item_details.setImageNumber(1);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Sổ ghi thu");
        item_details.setImageNumber(2);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Sổ ghi nợ");
        item_details.setImageNumber(3);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Sổ ghi vay");
        item_details.setImageNumber(4);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Tài khoản");
        item_details.setImageNumber(5);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Hạng mục thu/chi");
        item_details.setImageNumber(6);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Danh mục mua sắm");
        item_details.setImageNumber(7);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Thống kế");
        item_details.setImageNumber(8);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Thiết lặp");
        item_details.setImageNumber(9);
        results.add(item_details);

        item_details = new Itemlist();
        item_details.setName("Hướng dẫn sử dụng");
        item_details.setImageNumber(10);
        results.add(item_details);
        return results;
    }

}
