package com.dsetanzania.dse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.activities.BuyShareActivity;
import com.dsetanzania.dse.activities.SellShareActivity;
import com.dsetanzania.dse.models.MarketSimulator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class SimulatedMarketToEditAdapter extends RecyclerView.Adapter<SimulatedMarketToEditAdapter.ViewHolder>  {

    List<MarketSimulator> marketSimulator;
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public SimulatedMarketToEditAdapter(Context context, List<MarketSimulator> list) {
        this.marketSimulator = list;
        this.mycontext =  context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcompanyname;
        TextView txtMarketCap;
        TextView txtOpeningPrice;
        Button sellbtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompany);
            txtMarketCap = (TextView)itemView.findViewById(R.id.txtmarketcap);
            txtOpeningPrice = (TextView)itemView.findViewById(R.id.txtopeningprice);
            //sellbtn = (Button) itemView.findViewById(R.id.btnSell);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofmarketstoedit, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        _position = position;
        holder.itemView.setTag(marketSimulator.get(position));
        holder.txtcompanyname.setText(marketSimulator.get(position).getCompany());
        holder.txtOpeningPrice.setText("Opened at " + marketSimulator.get(position).getOpeningPrice().toString()  + " Price");
        holder.txtMarketCap.setText(marketSimulator.get(position).getMarketCap().toString());
    }

    @Override
    public int getItemCount() {
        return marketSimulator.size();
    }

    public  String formatExponential(String value){
        double _c = Double.parseDouble(value);
        NumberFormat formatter = new DecimalFormat("###.##");
        String f = formatter.format(_c);
        return f;
    }

 /*   public void pushMarkets(int index){

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        String id = reference.push().getKey();
        MarketSimulator livemarket = new MarketSimulator( LivesecurityPrices.get(position).Board,LivesecurityPrices.get(position).Change.doubleValue(),LivesecurityPrices.get(position).Close.doubleValue(),LivesecurityPrices.get(position).Company, LivesecurityPrices.get(position).High.doubleValue(),LivesecurityPrices.get(position).LastDealPrice.doubleValue(), LivesecurityPrices.get(position).LastTradedQuantity.longValue(),LivesecurityPrices.get(position).Low.doubleValue(), LivesecurityPrices.get(position).MarketCap.doubleValue(), LivesecurityPrices.get(position).OpeningPrice.doubleValue(), LivesecurityPrices.get(position).Volume);
        reference.child(id).setValue(livemarket).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mycontext,"Markets pushed.",Toast.LENGTH_LONG).show();
            }
        });
    }*/
}
