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

import java.util.List;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.ViewHolder> {
    private Context context;
    private List<Student> studentList;
    private StudentDao studentDao;
    private IItemClick iItemClick;


    public AdapterStudent(Context context, List<Student> studentList, IItemClick iItemClick) {
        this.context = context;
        this.studentList = studentList;
        studentDao = new StudentDao(context);
        this.iItemClick = iItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_student, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        final Student student = studentList.get(pos);
        viewHolder.tvMSHS.setText(student.getMshs());
        viewHolder.tvTenHS.setText(student.getTenHS());
        viewHolder.tvLop.setText(student.getLop());
        viewHolder.imgKhoanChi.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.xoaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iItemClick != null) iItemClick.onItemDelete(pos);
            }
        });
        viewHolder.suaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iItemClick != null) iItemClick.onItemEdit(pos);
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

    public interface IItemClick{
        void onItemEdit(int pos);
        void onItemDelete(int pos);
    }
}
