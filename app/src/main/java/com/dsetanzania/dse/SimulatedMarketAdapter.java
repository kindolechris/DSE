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
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;

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
        String openingPrice = formatter.format(LivesecurityPrices.get(position).OpeningPrice);
        holder.itemView.setTag(LivesecurityPrices.get(position));
        holder.txtcompanyname.setText(LivesecurityPrices.get(position).Company);
        holder.txtprice.setText("Opened at " + openingPrice + "Price");
        holder.txtlastdealvalue.setText(LivesecurityPrices.get(position).LastDealPrice.toString());
        holder.txtlasttradequantity.setText(LivesecurityPrices.get(position).LastTradedQuantity.toString());
        holder.txtvolume.setText(LivesecurityPrices.get(position).Volume.toString());
        String change = (LivesecurityPrices.get(position).Change.toString());

                if(change.contains("0E-9")){
                    double _c = Double.parseDouble(change);
                    String f = formatter.format(_c);
                    holder.txtchange.setText(f);
                }

                holder.buybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(mycontext, BuyShareActivity.class);
                        mycontext.startActivity(myIntent);
                    }
                });

                holder.sellbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(mycontext, SellShareActivity.class);
                        mycontext.startActivity(myIntent);
                    }
                });
            }

    @Override
    public int getItemCount() {
        return LivesecurityPrices.size();
    }


}
