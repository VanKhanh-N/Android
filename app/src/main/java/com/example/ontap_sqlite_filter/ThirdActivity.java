package com.example.ontap_sqlite_filter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    Button add, quaylai;
    TextView gadi, gaden, gia;
    Switch khuhoi;
    MyDBHelper dbHelper;

    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        KhaiBao();
        dbHelper = new MyDBHelper(ThirdActivity.this);
        dbHelper.openDB();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loai = 0;
                if (khuhoi.isChecked()) {
                    loai = 1;
                }
                if (!gadi.getText().toString().trim().equals("") &&
                        !gaden.getText().toString().trim().equals("") && Float.parseFloat(gia.getText().toString()) > 0) {
                    dbHelper.Add(gadi.getText().toString(), gaden.getText().toString(), Float.parseFloat(gia.getText().toString()), loai);
                }
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }

    void KhaiBao() {
        add = findViewById(R.id.btnAdd_3);
        quaylai = findViewById(R.id.btnQuayLai_3);
        gadi = findViewById(R.id.txtGaDi_3);
        gaden = findViewById(R.id.txtGaDen_3);
        gia = findViewById(R.id.txtGia_3);
        khuhoi = findViewById(R.id.switchKhuHoi);
    }
}