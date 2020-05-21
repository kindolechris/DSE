package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.BondHoldingsModel;
import com.dsetanzania.dse.models.PersonalBondHoldingsModel;

import java.util.List;

public class ListOfBondsHoldingsAdapter extends RecyclerView.Adapter<ListOfBondsHoldingsAdapter.ViewHolder> {

    private List<PersonalBondHoldingsModel> bondholdings;
    Context context;
    Dialog dialog1,dialog2,dialog3;

    int index;

    public ListOfBondsHoldingsAdapter(Context context, List<PersonalBondHoldingsModel> bondholdings) {
        this.bondholdings = bondholdings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofbondholdings, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtbondnumber;
        TextView txtinterestrate;
        TextView txtprice;
        TextView txtquantity;
        TextView txtduration;
        LinearLayout bongholdingLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbondnumber = (TextView)itemView.findViewById(R.id.txtdsebondnumber);
            txtinterestrate = (TextView)itemView.findViewById(R.id.txtinterestrate);
            txtprice = (TextView)itemView.findViewById(R.id.txtbondprice);
            txtquantity = (TextView)itemView.findViewById(R.id.txtquantity);
            txtduration = (TextView)itemView.findViewById(R.id.txtdurationdate);
            bongholdingLayout = (LinearLayout) itemView.findViewById(R.id.bongholdingLayout);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemView.setTag(bondholdings.get(position));
        holder.txtbondnumber.setText("No. " + String.valueOf(bondholdings.get(position).getBondnumber()));
        holder.txtinterestrate.setText("Interest per month : " + String.valueOf(bondholdings.get(position).getInterestRate() + "%"));
        holder.txtprice.setText("Price : "+ String.valueOf(bondholdings.get(position).getPrice()));
        holder.txtquantity.setText("Quantity : "+ String.valueOf(bondholdings.get(position).getAmount()));
        holder.txtduration.setText("Untill : " + String.valueOf(bondholdings.get(position).getDuration().substring(0,10)));

        holder.bongholdingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return bondholdings.size();
    }

}
