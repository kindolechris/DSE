package com.dsetanzania.dse.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.BuyBoardShareActivity;
import com.dsetanzania.dse.models.BoardSharesModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class BoardSharesAdapter extends RecyclerView.Adapter<BoardSharesAdapter.ViewHolder>  {

    private List<BoardSharesModel> marketcksimulator;
    Context mycontext;
    int _position;

    public BoardSharesAdapter(Context context, List<BoardSharesModel> list) {
        this.marketcksimulator = list;
        this.mycontext =  context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcompanyname;
        TextView txtlasttradequantity;
        LinearLayout boardsharelayout;
        TextView txtvolume;
        TextView txtchange;
        TextView txtprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompanyname);
            txtlasttradequantity = (TextView)itemView.findViewById(R.id.lasttradequality);
            txtvolume = (TextView)itemView.findViewById(R.id.txtvolume);
            txtprice = (TextView)itemView.findViewById(R.id.openingPrice);
            boardsharelayout = (LinearLayout) itemView.findViewById(R.id.boardsharelayout);
            //buybtn = (Button) itemView.findViewById(R.id.btnbuy);
            //sellbtn = (Button) itemView.findViewById(R.id.btnSell);

            //LinearLayout closeModal = dialog.findViewById(R.id.layoutclose);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_shares_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        _position = position;
        NumberFormat formatter = new DecimalFormat("#,###");
        holder.itemView.setTag(marketcksimulator.get(position));
        holder.txtcompanyname.setText(marketcksimulator.get(position).getCompany());
        holder.txtprice.setText("Opened at " + formatter.format(Double.parseDouble(marketcksimulator.get(position).getOpeningPrice())));
        holder.txtlasttradequantity.setText("LTQ :" + formatter.format(Double.parseDouble(marketcksimulator.get(position).getLastTradedQuantity())));
        holder.txtvolume.setText("Volume :" + formatter.format(Double.parseDouble(marketcksimulator.get(position).getVolume())));
        holder.boardsharelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, BuyBoardShareActivity.class);
                myIntent.putExtra("OpeningPrice",marketcksimulator.get(position).getOpeningPrice());
                myIntent.putExtra("Companyname",marketcksimulator.get(position).getCompany());
                myIntent.putExtra("boardid",marketcksimulator.get(position).getId());
                mycontext.startActivity(myIntent);
            }
        });

        //pushMarkets();
        //_position = position + 1;
    }

    @Override
    public int getItemCount() {
        return marketcksimulator.size();
    }

    public  String formatExponential(String value){
        double _c = Double.parseDouble(value);
        NumberFormat formatter = new DecimalFormat("###.##");
        String f = formatter.format(_c);
        return f;
    }

    /*public void pushMarkets(){

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        String id;
        for(int index=0; index<getItemCount(); index++){
            id = reference.push().getKey();
            MarketSimulator livemarket = new MarketSimulator(LivesecurityPrices.get(index).Board,String.valueOf(LivesecurityPrices.get(index).Change.doubleValue()),String.valueOf(LivesecurityPrices.get(index).Close.doubleValue()),String.valueOf(LivesecurityPrices.get(index).Company), String.valueOf(LivesecurityPrices.get(index).High.doubleValue()),String.valueOf(LivesecurityPrices.get(index).LastDealPrice.doubleValue()), String.valueOf(LivesecurityPrices.get(index).LastTradedQuantity.longValue()),String.valueOf(LivesecurityPrices.get(index).Low.doubleValue()), String.valueOf(LivesecurityPrices.get(index).MarketCap.doubleValue()),String.valueOf(LivesecurityPrices.get(index).OpeningPrice.doubleValue()), String.valueOf(LivesecurityPrices.get(index).Volume));
            reference.child(id).setValue(livemarket).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Toast.makeText(mycontext,"Markets pushed.",Toast.LENGTH_LONG).show();
                }
            });
        }
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
}