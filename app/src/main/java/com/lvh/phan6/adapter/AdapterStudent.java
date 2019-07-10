package com.lvh.phan6.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lvh.phan6.R;
import com.lvh.phan6.database.StudentDao;
import com.lvh.phan6.model.Student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.ViewHolder> {
    private Context context;
    private List<Student> studentList;
    private StudentDao studentDao;

    public AdapterStudent(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        studentDao = new StudentDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_student, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Student student = studentList.get(i);
        viewHolder.tvMSHS.setText(studentList.get(i).getMshs());
        viewHolder.tvTenHS.setText(studentList.get(i).getTenHS());
        viewHolder.tvLop.setText(studentList.get(i).getLop());
        viewHolder.imgKhoanChi.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.xoaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDao = new StudentDao(context);
                studentDao.delete(studentList.get(i).getId());
                studentList.remove(i);
                notifyDataSetChanged();
            }
        });
        viewHolder.suaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View dialog = View.inflate(context, R.layout.dialog_sua, null);
                builder.setView(dialog);
                final EditText edSuaMSHS = dialog.findViewById(R.id.edSuaMSHS);
                final EditText edSuaTenHS = dialog.findViewById(R.id.edSuaTenHS);
                final EditText edSuaLop = dialog.findViewById(R.id.edSuaLop);
                edSuaMSHS.setText(studentList.get(i).getMshs());
                edSuaTenHS.setText(studentList.get(i).getTenHS());
                edSuaLop.setText(studentList.get(i).getLop());
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                        studentDao = new StudentDao(context);
                        try {
                            int result = studentDao.update(0,edSuaMSHS.getText().toString(), edSuaTenHS.getText().toString(), edSuaLop.getText().toString());
                            if (result > 0) {
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            }
                            studentList.clear();
                            studentList = (ArrayList<Student>) studentDao.getAllStudent();
                            notifyDataSetChanged();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgKhoanChi;
        public TextView tvMSHS;
        public TextView tvTenHS;
        public ImageView xoaKhoanChi;
        public ImageView suaKhoanChi;
        public TextView tvLop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKhoanChi = itemView.findViewById(R.id.imgKhoanChi);
            tvMSHS = itemView.findViewById(R.id.tvMSHS);
            tvTenHS = itemView.findViewById(R.id.tvTenHS);
            xoaKhoanChi = itemView.findViewById(R.id.xoaKhoanChi);
            suaKhoanChi = itemView.findViewById(R.id.suaKhoanChi);
            tvLop = itemView.findViewById(R.id.tvLop);
        }
    }

    public void changeDataset(List<Student> studentList) {
        this.studentList = studentList;
        notifyDataSetChanged();
    }
}
