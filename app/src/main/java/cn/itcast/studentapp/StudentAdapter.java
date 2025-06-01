package cn.itcast.studentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> students;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(Student student);
        void onDelete(Student student);
    }

    public StudentAdapter(List<Student> students, OnItemClickListener listener) {
        this.students = students;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student s = students.get(position);
        holder.tvName.setText(s.getName());
        holder.tvStudentId.setText("学号: " + s.getStudentNumber());
        holder.tvPhone.setText("电话: " + s.getPhone());
        holder.tvMajor.setText("专业: " + s.getMajor());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(s));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(s));
    }

    @Override
    public int getItemCount() { return students.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStudentId, tvPhone, tvMajor;
        Button btnEdit, btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStudentId = itemView.findViewById(R.id.tv_studentId);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvMajor = itemView.findViewById(R.id.tv_major);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}