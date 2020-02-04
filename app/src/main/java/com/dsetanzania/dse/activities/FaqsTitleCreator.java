package com.dsetanzania.dse.activities;

import android.content.Context;
import android.content.res.Resources;

import com.dsetanzania.dse.R;

import java.util.ArrayList;
import java.util.List;

public class FaqsTitleCreator {
    static FaqsTitleCreator _titleCreator;
    List<FaqsParentLayout> _titleParents;
    Context context;

    public FaqsTitleCreator(Context context) {
        _titleParents = new ArrayList<>();
        context = context;


        Resources res = context.getResources();
        String[] headings = res.getStringArray(R.array.Faqs_heading);

        for(int i=0;i<headings.length;i++)
        {
            FaqsParentLayout title = new FaqsParentLayout(headings[i]);
            _titleParents.add(title);
        }
    }


    public static FaqsTitleCreator get(Context context)
    {
        if(_titleCreator == null)
            _titleCreator = new FaqsTitleCreator(context);
        return _titleCreator;
    }

    public List<FaqsParentLayout> getAll() {
        return _titleParents;
    }
}
