package com.example.giang.ql_thuchicanhan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ThietLap extends AppCompatActivity {

    Button btnDoiTK, btnDoiNN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap);

        btnDoiTK = (Button) findViewById(R.id.btnDoiTK);
        btnDoiNN = (Button) findViewById(R.id.btnDoiNN);

        btnDoiTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isSave", false);
                editor.putInt("idUser", 0);
                editor.commit();
                finish();
                Intent intent = new Intent(ThietLap.this, Login.class);
                startActivity(intent);
            }
        });

        btnDoiNN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
