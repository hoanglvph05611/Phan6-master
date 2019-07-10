package com.lvh.phan6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnDemoSQLite(View view) {
        Intent intent = new Intent(this,SQLiteActivity.class);
        startActivity(intent);
    }
}
