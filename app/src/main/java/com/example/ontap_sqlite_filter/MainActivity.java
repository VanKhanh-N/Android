package com.example.ontap_sqlite_filter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button Add,Update;
    ListView lstDanhSach;
    EditText search;
    ArrayList<VeTau> lstVeTau = new ArrayList<>();
    static public int MARK_REQUEST=100;
    int idPosition = 0;
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
        setContentView(R.layout.activity_main);

        KhaiBao();
        dbHelper = new MyDBHelper(MainActivity.this);
        dbHelper.openDB();
        setList();

        //nut update
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                VeTau vetau = new VeTau();
                vetau=lstVeTau.get(idPosition);
//                for(int i=0;i<lstVeTau.size();i++){
//                    if(lstVeTau.get(i).getId()==idPosition){
//                        vetau = lstVeTau.get(i);
//                        break;
//                    }
//                }
                intent.putExtra("ID",vetau.getId());
                intent.putExtra("GADI",vetau.getGadi());
                intent.putExtra("GADEN",vetau.getGaden());
                intent.putExtra("DONGIA",vetau.getDongia());
                intent.putExtra("KHUHOI",vetau.isKhuhoi());
                startActivityForResult(intent,MARK_REQUEST);
            }
        });

        //nut add
       Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
                startActivityForResult(intent,MARK_REQUEST);

            }
        });

        //long click listview
        lstDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                idPosition=position;
                registerForContextMenu(lstDanhSach);

                return false;
            }
        });

        lstDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idPosition=position;
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                VeTauAdapter adapter = new VeTauAdapter(MainActivity.this,lstVeTau);
                lstDanhSach.setAdapter(adapter);
                adapter.getFilter().filter(s);


            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Tùy chọn");
        menu.add(0, v.getId(), 0, "Add");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Update");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Delete") {

            VeTau vetau = new VeTau();
            vetau=lstVeTau.get(idPosition);
            showAlertDialog(vetau.getId());

        }
        if(item.getTitle()=="Update"){
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            VeTau vetau = new VeTau();
            vetau=lstVeTau.get(idPosition);
//            for(int i=0;i<lstVeTau.size();i++){
//                if(lstVeTau.get(i).getId()==idPosition){
//                    vetau = lstVeTau.get(i);
//                    break;
//                }
//            }
            intent.putExtra("ID",vetau.getId());
            intent.putExtra("GADI",vetau.getGadi());
            intent.putExtra("GADEN",vetau.getGaden());
            intent.putExtra("DONGIA",vetau.getDongia());
            intent.putExtra("KHUHOI",vetau.isKhuhoi());
            startActivityForResult(intent,MARK_REQUEST);

        }
        if (item.getTitle() == "Add")
        {
            Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
            startActivityForResult(intent,MARK_REQUEST);
        }
        else {
            return false;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==MARK_REQUEST) && (resultCode==RESULT_OK)){
            dbHelper.openDB();
            setList();
        }
    }

    void KhaiBao(){
        Add = findViewById(R.id.buttonAdd);
        Update = findViewById(R.id.buttonUpdate);
        search = findViewById(R.id.editTextSearch);
        lstDanhSach = findViewById(R.id.lstDanhSach);
    }

    void setList(){
        lstVeTau = dbHelper.ListAll();
        Cursor cursor = dbHelper.GetAllRecord();
        VeTauAdapter adapter = new VeTauAdapter(MainActivity.this,lstVeTau);
        lstDanhSach.setAdapter(adapter);
    }

    public void showAlertDialog(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbHelper.Delete(id);
               setList();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}