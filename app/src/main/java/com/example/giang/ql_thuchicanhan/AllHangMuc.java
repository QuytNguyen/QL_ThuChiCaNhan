package com.example.giang.ql_thuchicanhan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class AllHangMuc extends AppCompatActivity {

    Button btnThemChi;
    Button btnThemThu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hang_muc);
        loadTab();
        btnThemChi=(Button)findViewById(R.id.buttonThemHangMucChi);
        btnThemThu=(Button)findViewById(R.id.buttonThemHangMucThu);
        btnThemChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllHangMuc.this,them_hangmucchi.class);
                startActivity(intent);
            }
        });
        btnThemThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllHangMuc.this,them_hangmucthu.class);
                startActivity(intent);
            }
        });
    }
    void loadTab()
    {
        TabHost tabHost=(TabHost)findViewById(R.id.tabhosthangmuc);
        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("Hạng mục chi");
        spec1.setContent(R.id.tabhangchi);
        spec1.setIndicator("Hạng mục chi");
        TabHost.TabSpec spec2=tabHost.newTabSpec("Hạng mục thu");
        spec2.setIndicator("Hạng mục chi");
        spec2.setContent(R.id.tabhangthu);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }
}
