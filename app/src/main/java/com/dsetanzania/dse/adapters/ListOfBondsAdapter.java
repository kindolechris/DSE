package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.BuyBondActivity;
import com.dsetanzania.dse.models.BondModel;
import com.dsetanzania.dse.models.BondsModel;
import java.util.List;

public class ListOfBondsAdapter extends RecyclerView.Adapter<ListOfBondsAdapter.ViewHolder> {

    private List<BondModel> bonds;
    Context context;
    Dialog dialog1,dialog2,dialog3;

    int index;

    public ListOfBondsAdapter(Context context, List<BondModel> bonds) {
        this.bonds = bonds;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofbonds, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtbonnumber;
        TextView txtbondmonth;
        TextView txtbondrate;
        TextView txtbonddate;
        LinearLayout bondlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbonnumber = (TextView)itemView.findViewById(R.id.txtbondnumber);
            txtbondmonth = (TextView)itemView.findViewById(R.id.txtbondvolume);
            txtbondrate = (TextView)itemView.findViewById(R.id.txtbondrate);
            txtbonddate = (TextView)itemView.findViewById(R.id.txtbonddate);
            bondlayout = (LinearLayout) itemView.findViewById(R.id.bondlayout);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemView.setTag(bonds.get(position));
        holder.txtbonnumber.setText("Auction date. " + String.valueOf(bonds.get(position).getBondnumber()));
        //holder.txtbonddate.setText(bonds.get(position).getCreatedAt().substring(0,10));
        holder.txtbondmonth.setText("Bond tenure : "+ String.valueOf(bonds.get(position).getDuration()) + " Year (s)");
        holder.txtbondrate.setText("Coupon rate : " + String.valueOf(bonds.get(position).getInterestRate()) + " %");

        holder.bondlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, BuyBondActivity.class);
                myIntent.putExtra("Bondnumber",bonds.get(position).getBondnumber());
                myIntent.putExtra("Rate",String.valueOf(bonds.get(position).getInterestRate()));
                myIntent.putExtra("Duration",bonds.get(position).getDuration());
                myIntent.putExtra("bondid",(bonds.get(position).getId()));
                context.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bonds.size();
    }

}
