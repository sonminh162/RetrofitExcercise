package com.lifetime.retrofitexercise.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifetime.retrofitexercise.R;
import com.lifetime.retrofitexercise.adapter.ListAdapter;
import com.lifetime.retrofitexercise.model.Employee;
import com.lifetime.retrofitexercise.retrofit.GetDataService;
import com.lifetime.retrofitexercise.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private ListAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        loadView();

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.floating_button_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadView();
            }
        });


    }

    private void loadView(){
        progressDialog.show();
        Call<java.util.List<Employee>> call = service.getAllEmployees();
        call.enqueue(new Callback<java.util.List<Employee>>() {
            @Override
            public void onResponse(Call<java.util.List<Employee>> call, Response<java.util.List<Employee>> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<java.util.List<Employee>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(java.util.List<Employee> employeeList){
        recyclerView = findViewById(R.id.recycler_list);
        adapter = new ListAdapter(employeeList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Employee employee) {
                Call<Employee> call = service.getEmployeeById(employee.getId());
                call.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",response.body().getId());
                        intent.putExtra("package",bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadView();
    }


}
