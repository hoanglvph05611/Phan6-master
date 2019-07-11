package com.lvh.phan6;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SharedPreference extends AppCompatActivity {
    private static final String TAG ="SharedPreference";
    private EditText edName, edPass;
    private Button btnLogin;
    private CheckBox checkBox;

    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        edName = findViewById(R.id.edName);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.checkbox);

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreference.edit();

//        editor.putString("key","mitch");
//        editor.commit();
//
//        String name = sharedPreference.getString("some_other_key","default");
//        Log.d(TAG,"onCreate: name: "+name);
            checlSharedPreference();
    }

    public void btnLogin(View view) {
        if (checkBox.isChecked()){
            editor.putString(getString(R.string.checkbox),"True");
            editor.commit();
            // save the name
            String name  = edName.getText().toString();
            editor.putString(getString(R.string.name),name);
            editor.commit();

            //sava the pass
            String password = edPass.getText().toString();
            editor.putString(getString(R.string.password),password);
            editor.commit();
        }else {
            editor.putString(getString(R.string.checkbox),"False");
            editor.commit();
            // save the name
            String name  = edName.getText().toString();
            editor.putString(getString(R.string.name),"");
            editor.commit();

            //sava the pass
            String password = edPass.getText().toString();
            editor.putString(getString(R.string.password),"");
            editor.commit();
        }
    }
    private void checlSharedPreference(){
       String checkbox = sharedPreference.getString(getString(R.string.checkbox),"False");
       String name = sharedPreference.getString(getString(R.string.name),"");
       String passWord = sharedPreference.getString(getString(R.string.password),"");
       edName.setText(name);
       edPass.setText(passWord);
       if (checkbox.equals("True")){
           checkBox.setChecked(true);
       }else {
           checkBox.setChecked(false);
       }
    }

}
