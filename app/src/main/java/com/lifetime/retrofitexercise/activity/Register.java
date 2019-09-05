package com.lifetime.retrofitexercise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lifetime.retrofitexercise.R;
import com.lifetime.retrofitexercise.model.EmployeeResponse;
import com.lifetime.retrofitexercise.retrofit.GetDataService;
import com.lifetime.retrofitexercise.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    TextView registerName, registerSalary, registerAge;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.editTextName);
        registerAge = findViewById(R.id.editTextAge);
        registerSalary = findViewById(R.id.editTextSalary);
        buttonSave = findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmployee();
            }
        });
    }

    private void saveEmployee() {
        final String sName = registerName.getText().toString().trim();
        final String sSalary = registerSalary.getText().toString().trim();
        final String sAge = registerAge.getText().toString().trim();

        if(sName.isEmpty()){
            registerName.setError("name required");
            registerName.requestFocus();
            return;
        }

        if(sAge.isEmpty()){
            registerAge.setError("age required");
            registerAge.requestFocus();
        }

        if(sSalary.isEmpty()){
            registerSalary.setError("salary required");
            registerSalary.requestFocus();
            return;
        }

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<EmployeeResponse> call = service.createEmployee(new EmployeeResponse(sName,sSalary,sAge));
        call.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                finish();
                startActivity(new Intent(getApplication(), List.class));
                Toast.makeText(Register.this, "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Toast.makeText(Register.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.d("BBB:", t.getMessage());
            }
        });
    }
}
