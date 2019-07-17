package com.lvh.phan6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lvh.phan6.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends AlertDialog implements View.OnClickListener {
    private CustomDiaLogListener listener;
    private Context context;
    private Student student;
    private EditText edSuaTenHS;
    private EditText edSuaMSHS;
    private EditText edSuaLop;
    private Button btnLuu;
    private Button btnHuy;
    private String strMS, strTen, strLop;


    public CustomDialog(Context context, Student student) {
        super(context);
        this.context = context;
        this.student = student;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sua);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initView();
    }

    private void initView() {
        edSuaMSHS = findViewById(R.id.edSuaMSHS);
        edSuaTenHS = findViewById(R.id.edSuaTenHS);
        edSuaLop = findViewById(R.id.edSuaLop);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

     //   edSuaTenHS.setText(studentList.get(1).getMshs());
        btnLuu.setOnClickListener(this);
        btnHuy.setOnClickListener(this);

        initData();
    }


    private void initData(){
        if (student != null){
            edSuaMSHS.setText(student.getMshs());
            edSuaTenHS.setText(student.getTenHS());
            edSuaLop.setText(student.getLop());
        }
    }


    private boolean verifyData() {
        strMS = edSuaMSHS.getText().toString();
        strTen = edSuaTenHS.getText().toString();
        strLop = edSuaLop.getText().toString();

        if (strMS.isEmpty() || strTen.isEmpty() || strLop.isEmpty()) {
            Toast.makeText(context, "nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLuu:
                if (!verifyData()) {
                    Toast.makeText(context, "Luu thanh cong", Toast.LENGTH_SHORT).show();
                    return;
                }
                student.setMshs(strMS);
                student.setTenHS(strTen);
                student.setLop(strLop);

                listener.onSaveClicked(student);
                dismiss();
                break;
            case R.id.btnHuy:
                dismiss();
                break;
        }
    }

    public interface CustomDiaLogListener {
        void onSaveClicked(Student student);
    }

    public void setListener(CustomDiaLogListener customDiaLogListener) {
        this.listener = customDiaLogListener;
    }

}
