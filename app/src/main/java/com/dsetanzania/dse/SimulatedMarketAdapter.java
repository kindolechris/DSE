package com.dsetanzania.dse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.activities.BuyShareActivity;
import com.dsetanzania.dse.activities.SellShareActivity;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUSecurityLivePrice;
import com.dsetanzania.dse.models.MarketSimulator;
import com.dsetanzania.dse.models.Transactions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

public class SimulatedMarketAdapter extends RecyclerView.Adapter<SimulatedMarketAdapter.ViewHolder>  {

    List<MarketSimulator> marketSimulator;
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public SimulatedMarketAdapter(Context context,List<MarketSimulator> list) {
        this.marketSimulator = list;
        this.mycontext =  context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcompanyname;
        TextView txtlastdealvalue;
        TextView txtlasttradequantity;
        TextView txtvolume;
        TextView txtchange;
        TextView txtprice;
        Button buybtn;
        Button sellbtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompanyname);
            txtlasttradequantity = (TextView)itemView.findViewById(R.id.lasttradequality);
            txtvolume = (TextView)itemView.findViewById(R.id.txtvolume);
            txtchange = (TextView)itemView.findViewById(R.id.txtchange);
            txtlastdealvalue = (TextView)itemView.findViewById(R.id.txtlastdealvalue);
            txtprice = (TextView)itemView.findViewById(R.id.openingPrice);
            buybtn = (Button) itemView.findViewById(R.id.btnbuy);
            sellbtn = (Button) itemView.findViewById(R.id.btnSell);
            //LinearLayout closeModal = dialog.findViewById(R.id.layoutclose);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_company_details, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        _position = position;
        NumberFormat formatter = new DecimalFormat("###.##");
        holder.itemView.setTag(marketSimulator.get(position));
        holder.txtcompanyname.setText(marketSimulator.get(position).getCompany());
        holder.txtprice.setText("Opened at " + marketSimulator.get(position).getOpeningPrice().toString()  + " Price");
        holder.txtlasttradequantity.setText(marketSimulator.get(position).getLastTradedQuantity().toString());
        holder.txtvolume.setText(marketSimulator.get(position).getVolume().toString());
        holder.txtchange.setText(marketSimulator.get(position).getChange().toString());
        holder.txtlastdealvalue.setText(formatExponential(marketSimulator.get(position).getLastDealPrice().toString()));

        holder.buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, BuyShareActivity.class);
                myIntent.putExtra("OpeningPrice",marketSimulator.get(position).getOpeningPrice().toString());
                myIntent.putExtra("Companyname",marketSimulator.get(position).getCompany());
                mycontext.startActivity(myIntent);
            }
        });

        holder.sellbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, SellShareActivity.class);
                myIntent.putExtra("OpeningPrice",marketSimulator.get(position).getOpeningPrice().toString());
                mycontext.startActivity(myIntent);
            }
        });
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
