package com.example.hw03_clientdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw03_clientdb.Network.NetworkGet;
import com.example.hw03_clientdb.Network.NetworkInsert;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnCurrentMap, refreshBtn,addBtn;
    private ListView listView;
    private Custom_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new Custom_Adapter( MainActivity.this,
                                        R.layout.adapter_userinfo,
                                        new ArrayList<UserInfo>());
        listView.setAdapter(adapter);

        btnCurrentMap = findViewById(R.id.btnCurrentMap);
        btnCurrentMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        refreshBtn = findViewById(R.id.btnRefresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new NetworkGet((Custom_Adapter)listView.getAdapter()).execute("");
                Custom_Adapter adapter1 = (Custom_Adapter)listView.getAdapter();
                NetworkGet net1 = new NetworkGet(adapter1);
                net1.execute("");
            }
        });
        addBtn = findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = getLayoutInflater().inflate(R.layout.dialog_add,null);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("멤버 추가")
                        .setView(v)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = ((EditText)v.findViewById(R.id.edtID)).getText().toString();
                                String name = ((EditText)v.findViewById(R.id.edtNAME)).getText().toString();
                                String phone = ((EditText)v.findViewById(R.id.edtPhone)).getText().toString();
                                String grade = ((EditText)v.findViewById(R.id.edtGrade)).getText().toString();

                                new NetworkInsert(adapter).execute(id,name,phone,grade);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });
        new NetworkGet((Custom_Adapter)listView.getAdapter()).execute("");

        EditText edtSearch = findViewById(R.id.edtSearch);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_id = edtSearch.getText().toString();
                new NetworkGet((Custom_Adapter)listView.getAdapter()).execute(search_id);
            }
        });

    }
}
