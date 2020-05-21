package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.ListOfUniversitiesAdapter;
import com.dsetanzania.dse.models.UniversityModel;

import java.util.ArrayList;

public class SearchUniversityActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView listofuniversities;
    ListOfUniversitiesAdapter listOfUniversitiesAdapters;
    ArrayList<UniversityModel> universityModel;
    String[] universities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_university);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Select university");
        }

        getUniversities();
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    private void  getUniversities(){

        universities = getResources().getStringArray(R.array.university_list);
        universityModel = new ArrayList<UniversityModel>();

        for(int i = 0;i<universities.length; i++)
        {
            universityModel.add(new UniversityModel(universities[i]));
        }
        listofuniversities = findViewById(R.id.listofuniversitiesrecycler);
        listofuniversities.setHasFixedSize(true);
        listofuniversities.setLayoutManager(new LinearLayoutManager(this));
        listOfUniversitiesAdapters = new ListOfUniversitiesAdapter(SearchUniversityActivity.this, universityModel);
        listofuniversities.setAdapter(listOfUniversitiesAdapters);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.registermenu, menu);
        menu.findItem(R.id.searchuniversity).setVisible(true);
        MenuItem searchItem = menu.findItem(R.id.searchuniversity);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listOfUniversitiesAdapters.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
