package com.dsetanzania.dse.activities;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.dsetanzania.dse.R;


public class FaqsChildViewHolder extends ChildViewHolder {
    public TextView option1,option2;
    public FaqsChildViewHolder(View itemView) {
        super(itemView);
        option1 = (TextView)itemView.findViewById(R.id.option1);
    }
}
