package com.lvh.phan6.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lvh.phan6.model.Student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_NAME = "Student";
    public static final String SQL_STUDENT = "CREATE TABLE Student (id INTEGER PRIMARY KEY AUTOINCREMENT,mshs text,tenHS text, lop text)";
    public static final String TAG = "StudentDao";

    public StudentDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    // insert
    public int insert(Student student) {
        ContentValues values = new ContentValues();

        values.put("mshs", student.getMshs());
        values.put("tenHS", student.getTenHS());
        values.put("lop", student.getLop());
        try {
            if (sqLiteDatabase.insert(TABLE_NAME, null, values)==-1) {
                return -1;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public List<Student> getAllStudent() throws ParseException {
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Student student1 = new Student();
            student1.setId(cursor.getInt(0));
            student1.setMshs(cursor.getString(1));
            student1.setTenHS(cursor.getString(2));
            student1.setLop(cursor.getString(3));
            studentList.add(student1);
            Log.d("//=========", student1.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return studentList;
    }

    // update
    public int update(int id ,String mshs, String tenhs, String lop) {
        ContentValues values = new ContentValues();
        values.put("mshs", mshs);
        values.put("tenHS", tenhs);
        values.put("lop", lop);
        int result = sqLiteDatabase.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int delete(int id) {
        int result = sqLiteDatabase.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        if (result == 0) {
            return -1;
        }
        return 1;
    }
}
