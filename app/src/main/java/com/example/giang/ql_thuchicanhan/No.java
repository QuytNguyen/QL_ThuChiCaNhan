package com.example.giang.ql_thuchicanhan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class No extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no);
        loadTab();
    }
    void loadTab()
    {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhostNo);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Thêm nợ");
        spec1.setContent(R.id.tab1no);
        spec1.setIndicator("Thêm nợ");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Danh sách mượn nợ");
        spec2.setIndicator("Danh sách mượn nợ");
        spec2.setContent(R.id.tab2no);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }
}
