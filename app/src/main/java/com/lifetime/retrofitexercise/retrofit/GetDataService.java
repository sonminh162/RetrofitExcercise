package com.lifetime.retrofitexercise.retrofit;

import com.lifetime.retrofitexercise.model.Employee;
import com.lifetime.retrofitexercise.model.EmployeeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @POST("/api/v1/create")
    Call<EmployeeResponse> createEmployee(@Body EmployeeResponse employee);

    @GET("/api/v1/employees")
    Call<List<Employee>> getAllEmployees();

}
