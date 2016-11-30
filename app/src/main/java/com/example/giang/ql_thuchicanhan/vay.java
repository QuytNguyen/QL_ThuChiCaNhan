package com.example.giang.ql_thuchicanhan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class vay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vay);
        loadTab();
    }
    void loadTab()
    {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhostVay);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Thêm khoản cho vay");
        spec1.setContent(R.id.tab1vay);
        spec1.setIndicator("Thêm khoản cho vay");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Danh sách cho vay");
        spec2.setIndicator("Danh sách cho vay");
        spec2.setContent(R.id.tab2vay);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }
}
