package cn.itcast.studentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList = new ArrayList<>();
    private StudentApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentList, new StudentAdapter.OnItemClickListener() {
            @Override
            public void onEdit(Student student) {
                showEditDialog(student);
            }
            @Override
            public void onDelete(Student student) {
                deleteStudent(student);
            }
        });
        recyclerView.setAdapter(adapter);

        api = ApiClient.getClient().create(StudentApi.class);

        findViewById(R.id.fab_add).setOnClickListener(v -> showAddDialog());

        loadStudents();
    }

    private void loadStudents() {
        api.getAll().enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("StudentApp", "学生列表: " + response.body().toString());
                    studentList.clear();
                    studentList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "加载失败: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "加载失败: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("StudentApp", "网络请求失败", t);
            }
        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_student, null);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etStudentNumber = view.findViewById(R.id.et_studentId);
        EditText etPhone = view.findViewById(R.id.et_phone);
        EditText etMajor = view.findViewById(R.id.et_major);

        new AlertDialog.Builder(this)
                .setTitle("添加学生")
                .setView(view)
                .setPositiveButton("确定", (dialog, which) -> {
                    Student s = new Student();
                    s.setName(etName.getText().toString());
                    s.setStudentNumber(etStudentNumber.getText().toString());
                    s.setPhone(etPhone.getText().toString());
                    s.setMajor(etMajor.getText().toString());


                    addStudent(s); // 发送POST请求
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void addStudent(Student student) {
        api.add(student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) loadStudents();
            }
            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditDialog(Student student) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_student, null);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etStudentId = view.findViewById(R.id.et_studentId);
        EditText etPhone = view.findViewById(R.id.et_phone);
        EditText etMajor = view.findViewById(R.id.et_major);

        etName.setText(student.getName());
        etStudentId.setText(student.getStudentNumber());
        etPhone.setText(student.getPhone());
        etMajor.setText(student.getMajor());

        new AlertDialog.Builder(this)
                .setTitle("编辑学生")
                .setView(view)
                .setPositiveButton("确定", (dialog, which) -> {
                    student.setName(etName.getText().toString());
                    student.setStudentNumber(etStudentId.getText().toString());
                    student.setPhone(etPhone.getText().toString());
                    student.setMajor(etMajor.getText().toString());
                    updateStudent(student);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void updateStudent(Student student) {
        api.update(student.getId(), student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) loadStudents();
            }
            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(MainActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteStudent(Student student) {
        api.delete(student.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) loadStudents();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}