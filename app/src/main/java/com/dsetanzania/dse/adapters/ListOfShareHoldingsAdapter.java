package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.SellShareActivity;
import com.dsetanzania.dse.models.PersonalShareHoldingsModel;

import java.util.List;

public class ListOfShareHoldingsAdapter extends RecyclerView.Adapter<ListOfShareHoldingsAdapter.ViewHolder> {

    private List<PersonalShareHoldingsModel> personalShareHoldingsModel;
    Context context;
    Dialog dialog1,dialog2,dialog3;

    int index;

    public ListOfShareHoldingsAdapter(Context context, List<PersonalShareHoldingsModel> bondholdings) {
        this.personalShareHoldingsModel = bondholdings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofsharesholdings, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcompanyname;
        TextView txttotalamount;
        TextView txtdateupdated;
        TextView txtavailable;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompanyname);
            txttotalamount = (TextView)itemView.findViewById(R.id.txttotalamount);
            txtavailable = (TextView)itemView.findViewById(R.id.txtavailable);
            txtdateupdated = (TextView)itemView.findViewById(R.id.txtdateupdated);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.shareholdinglinearlayout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemView.setTag(personalShareHoldingsModel.get(position));
        holder.txtcompanyname.setText(personalShareHoldingsModel.get(position).getCompany());
        holder.txtavailable.setText("Available : " + String.valueOf(personalShareHoldingsModel.get(position).getAvailableSharesAmount()));
        holder.txttotalamount.setText("Total : " + String.valueOf(personalShareHoldingsModel.get(position).getSharesamount()));
        holder.txtdateupdated.setText("Last updated : " + personalShareHoldingsModel.get(position).getUpdatedAt().substring(0,10));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, SellShareActivity.class);
                myIntent.putExtra("companyname",personalShareHoldingsModel.get(position).getCompany());
                myIntent.putExtra("availableSharesAmount",personalShareHoldingsModel.get(position).getAvailableSharesAmount());
                myIntent.putExtra("boardshareid",personalShareHoldingsModel.get(position).getBoardshareId());
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personalShareHoldingsModel.size();
    }

}
