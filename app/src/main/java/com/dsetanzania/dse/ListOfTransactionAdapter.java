package com.dsetanzania.dse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.models.Transactions;

import java.util.List;

public class ListOfTransactionAdapter  extends RecyclerView.Adapter<ListOfTransactionAdapter.ViewHolder> {

    private List<Transactions> transactions;
    Context context;

    public ListOfTransactionAdapter(Context context, List<Transactions> transactions) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listoftransactions, parent, false);

        ListOfTransactionAdapter.ViewHolder viewHolder = new ListOfTransactionAdapter.ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsatus;
        TextView txtdate;
        TextView txtid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtsatus = (TextView)itemView.findViewById(R.id.txtstatus);
            txtdate = (TextView)itemView.findViewById(R.id.txttransactiondate);
            txtid = (TextView)itemView.findViewById(R.id.txttransactionId);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfTransactionAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(transactions.get(position));
        holder.txtdate.setText(transactions.get(position).getDate());
        holder.txtid.setText(transactions.get(position).getId());
        holder.txtsatus.setText(transactions.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
