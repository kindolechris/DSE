package com.dsetanzania.dse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.activities.BuyShareActivity;
import com.dsetanzania.dse.activities.SellShareActivity;
import com.dsetanzania.dse.models.MarketSimulator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulatedMarketToEditAdapter extends RecyclerView.Adapter<SimulatedMarketToEditAdapter.ViewHolder> implements Filterable {

    List<MarketSimulator> marketSimulator;
    List<MarketSimulator> marketSimulatorFilter;
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    Dialog dialog;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    TextInputEditText quantity;
    TextInputEditText price;


    TextView companyname;
    TextView txtBoard;
    Button updatebtn;

    @Override
    public Filter getFilter() {
        return filteredItem;
    }


    private Filter filteredItem = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MarketSimulator> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(marketSimulatorFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MarketSimulator item : marketSimulatorFilter) {
                    if (item.getCompany().toLowerCase().contains(filterPattern) || item.getBoard().toLowerCase().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            marketSimulator.clear();
            marketSimulator.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public SimulatedMarketToEditAdapter(Context context, List<MarketSimulator> list) {
        this.marketSimulator = list;
        this.mycontext =  context;
        marketSimulatorFilter = new ArrayList<>(marketSimulator);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcompanyname;
        TextView txtMarketCap;
        TextView txtOpeningPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dialog = new Dialog(mycontext,R.style.Mydialogtheme);
            dialog.setContentView(R.layout.popup_edit_company_sales);

            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompany);
            txtMarketCap = (TextView)itemView.findViewById(R.id.txtmarketcap);
            txtOpeningPrice = (TextView)itemView.findViewById(R.id.txtopeningprice);


            companyname = (TextView)dialog.findViewById(R.id.txtcompanynametoedit);
            quantity = (TextInputEditText)dialog.findViewById(R.id.txtquantitytoedit);
            price = (TextInputEditText)dialog.findViewById(R.id.txtpricetoedit);
            updatebtn = (Button) dialog.findViewById(R.id.btnUpdate);
            txtBoard = (TextView)dialog.findViewById(R.id.txtBoard);

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
        holder.txtOpeningPrice.setText("Opened at " + String.valueOf(marketSimulator.get(position).getOpeningPrice())  + " Price");
        holder.txtMarketCap.setText(String.valueOf(marketSimulator.get(position).getMarketCap()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyname.setText(marketSimulator.get(position).getCompany());
                txtBoard.setText(marketSimulator.get(position).getBoard());
                dialog.show();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
                Map<String, Object> map = new HashMap<>();
                map.put("lastTradedQuantity",quantity.getText().toString());
                map.put("openingPrice",price.getText().toString());
                reference.updateChildren(map);

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
