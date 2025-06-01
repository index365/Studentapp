package cn.itcast.studentapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface StudentApi {
    @GET("/api/students")
    Call<List<Student>> getAll();

    @POST("/api/students")
    Call<Student> add(@Body Student student);

    @PUT("/api/students/{id}")
    Call<Student> update(@Path("id") long id, @Body Student student);

    @DELETE("/api/students/{id}")
    Call<Void> delete(@Path("id") long id);
}