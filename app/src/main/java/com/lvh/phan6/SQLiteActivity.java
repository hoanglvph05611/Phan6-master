package com.lvh.phan6;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lvh.phan6.adapter.AdapterStudent;
import com.lvh.phan6.database.StudentDao;
import com.lvh.phan6.model.Student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity implements AdapterStudent.IItemClick, CustomDialog.CustomDiaLogListener {
    private EditText edMSHS, edTenHS, edLop;
    private RecyclerView recyclerView;
    private AdapterStudent adapterStudent;
    private StudentDao studentDao;
    private List<Student> studentList;
    private String strMS, strTen, strLop;
    private int posSelected = 0;

    private static final String TAG = "Logra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        studentList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        studentDao = new StudentDao(this);
        try {
            studentList = studentDao.getAllStudent();
            Log.d(TAG, "day roi ne" + studentList.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapterStudent = new AdapterStudent(this, (ArrayList<Student>) studentList, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterStudent);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SQLiteActivity.this);
                builder.setTitle("Thêm học sinh ");
                View view1 = getLayoutInflater().inflate(R.layout.dialog_them, null);
                builder.setView(view1);
                builder.setCancelable(false);
                edTenHS = view1.findViewById(R.id.edTenHS);
                edMSHS = view1.findViewById(R.id.edMSHS);
                edLop = view1.findViewById(R.id.edLop);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentDao = new StudentDao(SQLiteActivity.this);
                        if (checkChi() > 0) {

                            Student student = new Student(0, edMSHS.getText().toString(), edTenHS.getText().toString(), edLop.getText().toString());
                            if (studentDao.insert(student) > 0) {
                                try {
                                    studentList = studentDao.getAllStudent();
                                    Toast.makeText(SQLiteActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    onResume();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setTitle("Thêm HS");
                dialog.show();

            }
        });

    }

    public void onResume() {
        super.onResume();

        try {
            studentList = studentDao.getAllStudent();
            adapterStudent.changeDataset(studentDao.getAllStudent());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int checkChi() {
        int check1 = 1;
        strMS = edMSHS.getText().toString();
        strTen = edTenHS.getText().toString();
        strLop = edLop.getText().toString();

        if (strMS.isEmpty() || strTen.isEmpty() || strLop.isEmpty()) {
            Toast.makeText(this, "nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check1 = -1;
        }
        return check1;
    }


    @Override
    public void onItemEdit(final int pos) {
        posSelected = pos;
        customDialog(pos);


    }

    @Override
    public void onItemDelete(int pos) {
        studentDao = new StudentDao(SQLiteActivity.this);
        studentDao.delete(studentList.get(pos).getId());
        studentList.remove(pos);
        adapterStudent.notifyDataSetChanged();
    }

    public void customDialog(int pos) {

        CustomDialog customDialog = new CustomDialog(this, studentList.get(pos));
        customDialog.show();
        customDialog.setListener(this);
    }

    @Override
    public void onSaveClicked(Student student) {
        studentDao = new StudentDao(SQLiteActivity.this);

        int result = studentDao.update(student);
        if (result > 0) {
            Toast.makeText(SQLiteActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
        }
        try {
            studentList = studentDao.getAllStudent();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        studentList.get(posSelected).setMshs(student.getMshs());
        studentList.get(posSelected).setTenHS(student.getTenHS());
        studentList.get(posSelected).setLop(student.getLop());
        adapterStudent.notifyDataSetChanged();
    }
}

