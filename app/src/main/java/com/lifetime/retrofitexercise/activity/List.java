package com.lifetime.retrofitexercise.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.lifetime.retrofitexercise.R;
import com.lifetime.retrofitexercise.adapter.ListAdapter;
import com.lifetime.retrofitexercise.model.Employee;
import com.lifetime.retrofitexercise.retrofit.GetDataService;
import com.lifetime.retrofitexercise.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List extends AppCompatActivity {
    private ListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<java.util.List<Employee>> call = service.getAllEmployees();
        call.enqueue(new Callback<java.util.List<Employee>>() {
            @Override
            public void onResponse(Call<java.util.List<Employee>> call, Response<java.util.List<Employee>> response) {
                Log.d("ThanhCong:",response.body().toString());
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<java.util.List<Employee>> call, Throwable t) {
                Toast.makeText(List.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("BBB",t.getMessage());
            }
        });

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List.this,Register.class);
                startActivity(intent);
            }
        });
    }
    private void generateDataList(java.util.List<Employee> employeeList){
        recyclerView = findViewById(R.id.recycler_list);
        adapter = new ListAdapter(employeeList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(List.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
}
