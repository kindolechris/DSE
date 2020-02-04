package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.dsetanzania.dse.R;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class FaqsActivity extends AppCompatActivity {


    private String title;
    private int page;
    RecyclerView recyclerView;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.faqsrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FaqsAdapter adapter = new FaqsAdapter(getApplicationContext(),initData());
        adapter.setCustomParentAnimationViewId(R.id.expandArrow);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Faqs");
        }
    }



    private List<ParentObject> initData() {
        FaqsTitleCreator titleCreator = FaqsTitleCreator.get(getApplicationContext());
        List<FaqsParentLayout> titles = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        Resources res = getResources();
        String[] content = res.getStringArray(R.array.Faqs_content);
        int contentIndex = 0;
        for(FaqsParentLayout title:titles)
        {

            List<Object> childList = new ArrayList<>();
            childList.add(new FaqsChildLayout(content[contentIndex]));
            title.setChildObjectList(childList);
            parentObject.add(title);
            contentIndex++;
        }
        return parentObject;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
