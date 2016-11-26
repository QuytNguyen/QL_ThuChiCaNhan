package com.example.giang.ql_thuchicanhan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class chi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi);
        loadTab();
    }
    void loadTab()
    {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Thêm khoản chi");
        spec1.setContent(R.id.tab1chi);
        spec1.setIndicator("Thêm khoản chi");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Danh sách chi");
        spec2.setIndicator("Danh sách chi");
        spec2.setContent(R.id.tab2chi);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }
}
