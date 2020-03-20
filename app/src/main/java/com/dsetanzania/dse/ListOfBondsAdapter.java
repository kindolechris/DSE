package com.dsetanzania.dse;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dsetanzania.dse.models.Bonds;
import java.util.List;

public class ListOfBondsAdapter extends RecyclerView.Adapter<ListOfBondsAdapter.ViewHolder> {

    private List<Bonds> bonds;
    Context context;
    Dialog dialog1,dialog2,dialog3;

    int index;

    public ListOfBondsAdapter(Context context, List<Bonds> bonds) {
        this.bonds = bonds;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfBondsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofbonds, parent, false);

        ListOfBondsAdapter.ViewHolder viewHolder = new ListOfBondsAdapter.ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtbonnumber;
        TextView txtbondmonth;
        TextView txtbondrate;
        TextView txtbonddate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbonnumber = (TextView)itemView.findViewById(R.id.txtbondnumber);
            txtbondmonth = (TextView)itemView.findViewById(R.id.txtbondmonth);
            txtbondrate = (TextView)itemView.findViewById(R.id.txtbondrate);
            txtbonddate = (TextView)itemView.findViewById(R.id.txtbonddate);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfBondsAdapter.ViewHolder holder, final int position) {

        holder.itemView.setTag(bonds.get(position));
        holder.txtbonnumber.setText("No. " + String.valueOf(bonds.get(position).getBondnumber()));
        holder.txtbonddate.setText(bonds.get(position).getDate().substring(0,10));
        holder.txtbondmonth.setText("Duration : "+ String.valueOf(bonds.get(position).getMonth()) + " Month (s)");
        holder.txtbondrate.setText("Rate : " + String.valueOf(bonds.get(position).getRate()) + " %");

    }

    @Override
    public int getItemCount() {
        return bonds.size();
    }

}
