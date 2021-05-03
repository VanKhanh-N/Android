package com.example.ontap_sqlite_filter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    Button update,quaylai;
    TextView gadi,gaden,gia;
    RadioButton motchieu,khuhoi;
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
        setContentView(R.layout.activity_second);

        KhaiBao();
        dbHelper = new MyDBHelper(SecondActivity.this);
        dbHelper.openDB();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            gadi.setText(bundle.getString("GADI"));
            gaden.setText(bundle.getString("GADEN"));
            gia.setText(String.valueOf(bundle.getFloat("DONGIA")));
            boolean loai = bundle.getBoolean("KHUHOI");
            if(loai){
                khuhoi.setChecked(true);
            }
            else{
                motchieu.setChecked(true);
            }
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loai=0;
                if(khuhoi.isChecked()){
                    loai=1;
                }
                dbHelper.Update(bundle.getInt("ID"),gadi.getText().toString(),gaden.getText().toString(),
                        Float.parseFloat(gia.getText().toString()),loai);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();



            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();



            }
        });
    }
    void KhaiBao(){
        update = findViewById(R.id.btnUpdate_2);
        quaylai = findViewById(R.id.btnQuayLai_2);
        gadi = findViewById(R.id.txtGaDi_2);
        gaden = findViewById(R.id.txtGaDen_2);
        gia = findViewById(R.id.txtGiaTien_2);
        khuhoi = findViewById(R.id.radioButtonKhuHoi);
        motchieu = findViewById(R.id.radioButtonMotChieu);
    }

}