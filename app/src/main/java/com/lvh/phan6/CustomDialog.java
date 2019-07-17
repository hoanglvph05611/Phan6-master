package com.lvh.phan6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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
    private List<Student> studentList;


    public CustomDialog(Context context, Student student) {
        super(context);
        this.context = context;
        this.student = student;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sua);
        initView();
    }

    private void initView() {
        studentList = new ArrayList<>();
        edSuaMSHS = findViewById(R.id.edSuaMSHS);
        edSuaTenHS = findViewById(R.id.edSuaTenHS);
        edSuaLop = findViewById(R.id.edSuaLop);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

     //   edSuaTenHS.setText(studentList.get(1).getMshs());
        btnLuu.setOnClickListener(this);
        btnHuy.setOnClickListener(this);

    }


    private boolean verifyData() {
        boolean check1 = true;
        strMS = edSuaMSHS.getText().toString();
        strTen = edSuaTenHS.getText().toString();
        strLop = edSuaLop.getText().toString();

        if (strMS.isEmpty() || strTen.isEmpty() || strLop.isEmpty()) {
            Toast.makeText(context, "nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check1 = false;
        }
        return check1 = true;
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
                student.setMshs(edSuaTenHS.getText().toString().trim());
                student.setTenHS(edSuaTenHS.getText().toString().trim());
                student.setLop(edSuaLop.getText().toString().trim());

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

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            listener = (CustomDiaLogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
