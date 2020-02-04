package com.dsetanzania.dse.activities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.dsetanzania.dse.R;


public class FaqsParentViewHolder extends ParentViewHolder {
    public TextView _textView;
    public ImageButton _imageButton;
    //public ImageButton _imageView;

    public FaqsParentViewHolder(View itemView) {
        super(itemView);
        _textView = (TextView)itemView.findViewById(R.id.parentTitle);
        _imageButton = (ImageButton) itemView.findViewById(R.id.ticketIcon);
       // _imageView = (ImageButton) itemView.findViewById(R.id.fagsicon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton _imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //currentSelectedServer = itemView.findViewById(R.id.currentConnectedServer);
           /* servername = itemView.findViewById(R.id.servername);
            status = itemView.findViewById(R.id.serverSstatus);
            serverIcon = itemView.findViewById(R.id.serverIcon);
            parentlayout = itemView.findViewById(R.id.serverlistLayout);
*/
        }
    }
}
