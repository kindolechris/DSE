package com.dsetanzania.dse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.activities.BuyShareActivity;
import com.dsetanzania.dse.activities.SellShareActivity;
import com.dsetanzania.dse.activities.SimulatedMarketListActivity;
import com.dsetanzania.dse.models.MarketSimulator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    TextView date;
    private int id;


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
            date = (TextView)dialog.findViewById(R.id.txtdateToedit);
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
        final NumberFormat formatter = new DecimalFormat("#,###");
        holder.itemView.setTag(marketSimulator.get(position));
        holder.txtcompanyname.setText(marketSimulator.get(position).getCompany());
        holder.txtOpeningPrice.setText("Opened at " + formatter.format(Double.valueOf(marketSimulator.get(position).getOpeningPrice()))  + " Price");
        holder.txtMarketCap.setText(formatValue(Double.valueOf(marketSimulator.get(position).getMarketCap())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = position;
                companyname.setText(marketSimulator.get(position).getCompany());
                txtBoard.setText(marketSimulator.get(position).getBoard());
                date.setText(getdate());
                dialog.show();
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(price.getText().toString().trim()) || TextUtils.isEmpty(quantity.getText().toString().trim() )){
                    Toast.makeText(mycontext,"Enter some values to update",Toast.LENGTH_SHORT).show();
                    return;
                }
                reference = FirebaseDatabase.getInstance().getReference("MarketSimulator").child(marketSimulator.get(id).getId());
                Map<String, Object> map = new HashMap<>();
                map.put("openingPrice", price.getText().toString().trim());
                map.put("lastTradedQuantity", quantity.getText().toString().trim());

                reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(mycontext,"Updated",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(mycontext,"Not updated",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });

                //Toast.makeText(mycontext,"Updated"+ marketSimulator.get(id).getId(),Toast.LENGTH_SHORT).show();

                //setkeys();
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

    public static String getdate(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private void  setkeys(){

        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String childKey = snapshot.getKey();
                    snapshot.getRef().child("Id").setValue(childKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static String formatValue(double value) {
        int power;
        String suffix = " kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(value);
        value = value/(Math.pow(10,(power/3)*3));
        formattedNumber=formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3);
        return formattedNumber.length()>4 ?  formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }
}
