package com.dsetanzania.dse.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.helperClasses.FaqsChildLayout;
import com.dsetanzania.dse.viewHolders.FaqsChildViewHolder;
import com.dsetanzania.dse.helperClasses.FaqsParentLayout;
import com.dsetanzania.dse.viewHolders.FaqsParentViewHolder;


import java.util.List;

public class FaqsAdapter extends ExpandableRecyclerAdapter<FaqsParentViewHolder, FaqsChildViewHolder> {

    LayoutInflater inflater;
    Context mycontext;
    int selectedPosition=-1;
    boolean isColorChanged=false;
    ImageButton imgButtonFavourite;


    public FaqsAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        this.mycontext = context;
    }


    @Override
    public FaqsParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_parent,viewGroup,false);
        return new FaqsParentViewHolder(view);
    }

    @Override
    public FaqsChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_child,viewGroup,false);
        return new FaqsChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(FaqsParentViewHolder titleParentViewHolder, final int i, Object o) {
        FaqsParentLayout title = (FaqsParentLayout)o;
        titleParentViewHolder._textView.setText(title.getTitle());
        //titleParentViewHolder._imageView.setBackgroundColor(Color.rgb(100,100,50));

/*
            if(selectedPosition == i){
                if(isColorChanged == false) {
                    titleParentViewHolder._imageButton.setColorFilter(ContextCompat.getColor(mycontext,R.color.colorBgForButton));
                    isColorChanged = true;
                }
                else {
                    titleParentViewHolder._imageButton.setColorFilter(ContextCompat.getColor(mycontext,R.color.colorBgForTextColor1));
                    isColorChanged = false;
                }
            }
            else if(selectedPosition !=1){

                titleParentViewHolder._imageButton.setColorFilter(ContextCompat.getColor(mycontext,R.color.colorBgForButton));
            }

            */


        titleParentViewHolder._imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = i;
                notifyDataSetChanged();

            }

    });

    }


    @Override
    public void onBindChildViewHolder(FaqsChildViewHolder titleChildViewHolder, int i, Object o) {
        FaqsChildLayout title = (FaqsChildLayout)o;
        titleChildViewHolder.option1.setText(title.getOption1());
        //titleChildViewHolder.option2.setText(title.getOption2());

    }
}