package com.lifetime.retrofitexercise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lifetime.retrofitexercise.R;
import com.lifetime.retrofitexercise.model.Employee;

public class Detail extends AppCompatActivity {

    private EditText editTextName,editTextAge,editTextSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        editTextName = findViewById(R.id.update_name);
        editTextAge = findViewById(R.id.update_age);
        editTextSalary = findViewById(R.id.update_salary);

        final Employee employee = (Employee) getIntent().getSerializableExtra("employee");

        loadEmployee(employee);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(employee);
            }
        });
    }

    private void loadEmployee(Employee employee){
        editTextName.setText(employee.getName());
        editTextSalary.setText(employee.getSalary());
        editTextAge.setText(employee.getAge());
    }

    private void updateEmployee(final Employee employee){
        final String sName = editTextName.getText().toString().trim();
        final String sSalary = editTextSalary.getText().toString().trim();
        final String sAge = editTextAge.getText().toString().trim();

        if(sName.isEmpty()){
            editTextName.setError("name required");
            editTextName.requestFocus();
            return;
        }

        if(sSalary.isEmpty()){
            editTextSalary.setError("salary required");
            editTextSalary.requestFocus();
            return;
        }

        if(sAge.isEmpty()){
            editTextAge.setError("age required");
            editTextAge.requestFocus();
            return;
        }
    }
}
