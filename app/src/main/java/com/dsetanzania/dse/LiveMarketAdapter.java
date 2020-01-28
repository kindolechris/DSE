package com.dsetanzania.dse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Vector;

public class LiveMarketAdapter extends RecyclerView.Adapter<LiveMarketAdapter.ViewHolder>  {

    private OOUArrayOfSecurityLivePrice LivesecurityPrices;
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;

    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public LiveMarketAdapter(Context context,OOUArrayOfSecurityLivePrice list) {
        this.LivesecurityPrices = list;
        this.mycontext =  context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView company;
        TextView marketcap;
        TextView OpeningPrice;
        LinearLayout parentlayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //currentSelectedServer = itemView.findViewById(R.id.currentConnectedServer);
            company = itemView.findViewById(R.id.txtxompany);
            marketcap = itemView.findViewById(R.id.txtmarketcap);
            OpeningPrice = itemView.findViewById(R.id.txtopeningprice);
            //parentlayout = itemView.findViewById(R.id.serverlistLayout);
        }
    }

    final Vector<ViewHolder> serverListViewHold = new Vector<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livemarketprices, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull LiveMarketAdapter.ViewHolder holder,int position) {

        _position = position;
        holder.itemView.setTag(LivesecurityPrices.get(position));
        holder.company.setText(LivesecurityPrices.get(position).Company);
        holder.marketcap.setText(LivesecurityPrices.get(position).MarketCap.toString());
        holder.OpeningPrice.setText(LivesecurityPrices.get(position).OpeningPrice.toString());
        //holder.selectedServer = LivesecurityPrices.get(position).isSelected();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return LivesecurityPrices.size();
    }


}
