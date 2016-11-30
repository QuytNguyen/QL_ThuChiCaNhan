package com.example.giang.ql_thuchicanhan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class Thu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu);
        TabHost tabHost=(TabHost)findViewById(R.id.tabhosthu);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Thêm khoản thu");
        spec1.setContent(R.id.tab1thu);
        spec1.setIndicator("Thêm khoản thu");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Danh mục thu");
        spec2.setIndicator("Danh sách khoản thu");
        spec2.setContent(R.id.tab2thu);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }
}
