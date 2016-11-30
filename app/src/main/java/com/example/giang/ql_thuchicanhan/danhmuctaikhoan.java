package com.example.giang.ql_thuchicanhan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class danhmuctaikhoan extends AppCompatActivity {

    Button btnThemTk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuctaikhoan);
        btnThemTk=(Button)findViewById(R.id.buttonThemTK);
        btnThemTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(danhmuctaikhoan.this,themtaikhoan.class);
                startActivity(intent);
            }
        });

    }
}
