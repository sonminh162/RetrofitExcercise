package com.lifetime.retrofitexercise.retrofit;

import com.lifetime.retrofitexercise.model.Employee;
import com.lifetime.retrofitexercise.model.EmployeeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GetDataService {
    @POST("/api/v1/create")
    Call<EmployeeResponse> createEmployee(@Body EmployeeResponse employee);

    @GET("/api/v1/employees")
    Call<List<Employee>> getAllEmployees();

    @GET("/api/v1/employee/{id}")
    Call<Employee> getEmployeeById(@Path("id") int id);

    @PUT("/api/v1/update/{id}")
    Call<EmployeeResponse> updateEmployee(@Path("id") int id, @Body EmployeeResponse employee);

    @DELETE("/api/v1/delete/{id}")
    Call<Void> deleteEmployee(@Path("id") int id);
}
