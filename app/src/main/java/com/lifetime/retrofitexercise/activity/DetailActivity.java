package com.lifetime.retrofitexercise.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lifetime.retrofitexercise.R;
import com.lifetime.retrofitexercise.model.Employee;
import com.lifetime.retrofitexercise.model.EmployeeResponse;
import com.lifetime.retrofitexercise.retrofit.GetDataService;
import com.lifetime.retrofitexercise.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    EditText editTextName, editTextAge, editTextSalary;
    TextView currentId;
    Button buttonUpdate, buttonDelete;
    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        editTextName = findViewById(R.id.update_name);
        editTextAge = findViewById(R.id.update_age);
        editTextSalary = findViewById(R.id.update_salary);
        currentId = findViewById(R.id.current_id);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);

        employee = (Employee) getIntent().getSerializableExtra("employee");

        loadEmployee(employee);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateEmployee(employee);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Are your sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee(employee);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

    }

    private void deleteEmployee(final Employee employee) {
        Call<Void> call = service.deleteEmployee(employee.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                startActivity(new Intent(getApplication(), ListActivity.class));
                Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Fail here", t.getMessage());
            }
        });
    }

    private void loadEmployee(Employee employee) {
        editTextName.setText(employee.getName());
        editTextSalary.setText(employee.getSalary());
        editTextAge.setText(employee.getAge());
        currentId.setText("CurrentID: " + employee.getId());
    }

    private void updateEmployee(final Employee employee) {
        final String sName = editTextName.getText().toString().trim();
        final String sSalary = editTextSalary.getText().toString().trim();
        final String sAge = editTextAge.getText().toString().trim();

        if (sName.isEmpty()) {
            editTextName.setError("name required");
            editTextName.requestFocus();
            return;
        }

        if (sSalary.isEmpty()) {
            editTextSalary.setError("salary required");
            editTextSalary.requestFocus();
            return;
        }

        if (sAge.isEmpty()) {
            editTextAge.setError("age required");
            editTextAge.requestFocus();
            return;
        }

        Call<EmployeeResponse> call = service.updateEmployee(employee.getId(), new EmployeeResponse(sName, sSalary, sAge));
        call.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                startActivity(new Intent(getApplication(), ListActivity.class));
                Toast.makeText(DetailActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.d("Fail here", t.getMessage());
            }
        });


    }
}
