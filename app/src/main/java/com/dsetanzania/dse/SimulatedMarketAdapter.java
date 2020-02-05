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
import java.util.Vector;

public class SimulatedMarketAdapter extends RecyclerView.Adapter<SimulatedMarketAdapter.ViewHolder>  {

    private OOUArrayOfSecurityLivePrice LivesecurityPrices;
    final Vector<ViewHolder> securityPriceListViewHold = new Vector<>();
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public SimulatedMarketAdapter(Context context,OOUArrayOfSecurityLivePrice list) {
        this.LivesecurityPrices = list;
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
        final String openingPrice = formatter.format(LivesecurityPrices.get(position).OpeningPrice);
        final String companyname = (LivesecurityPrices.get(position).Company);
        holder.itemView.setTag(LivesecurityPrices.get(position));
        holder.txtcompanyname.setText(LivesecurityPrices.get(position).Company);
        holder.txtprice.setText("Opened at " + openingPrice  + " Price");
        holder.txtlasttradequantity.setText(LivesecurityPrices.get(position).LastTradedQuantity.toString());
        holder.txtvolume.setText(LivesecurityPrices.get(position).Volume.toString());
        String change = (LivesecurityPrices.get(position).Change.toString());
        String Ldp = (LivesecurityPrices.get(position).LastDealPrice.toString());

        if(change.contains("0E-9") || Ldp.contains("0E-9")){

            holder.txtchange.setText(formatExponential(change));
            holder.txtlastdealvalue.setText(formatExponential(Ldp));
        }

        holder.buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, BuyShareActivity.class);
                myIntent.putExtra("OpeningPrice",openingPrice);
                myIntent.putExtra("Companyname",companyname);
                mycontext.startActivity(myIntent);
            }
        });

        holder.sellbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, SellShareActivity.class);
                myIntent.putExtra("OpeningPrice",LivesecurityPrices.get(position).OpeningPrice);
                mycontext.startActivity(myIntent);
            }
        });
        //pushMarkets(_position);
        //_position = position + 1;
    }

    @Override
    public int getItemCount() {
        return LivesecurityPrices.size();
    }

    public  String formatExponential(String value){
        double _c = Double.parseDouble(value);
        NumberFormat formatter = new DecimalFormat("###.##");
        String f = formatter.format(_c);
        return f;
    }

    public void pushMarkets(int index){

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        String id = reference.push().getKey();
        MarketSimulator livemarket = new MarketSimulator( LivesecurityPrices.get(index).Board,LivesecurityPrices.get(index).Change.doubleValue(),LivesecurityPrices.get(index).Close.doubleValue(),LivesecurityPrices.get(index).Company, LivesecurityPrices.get(index).High.doubleValue(),LivesecurityPrices.get(index).LastDealPrice.doubleValue(), LivesecurityPrices.get(index).LastTradedQuantity.longValue(),LivesecurityPrices.get(index).Low.doubleValue(), LivesecurityPrices.get(index).MarketCap.doubleValue(), LivesecurityPrices.get(index).OpeningPrice.doubleValue(), LivesecurityPrices.get(index).Volume);
        reference.child(id).setValue(livemarket).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(mycontext,"Markets pushed.",Toast.LENGTH_LONG).show();
            }
        });
    }
}