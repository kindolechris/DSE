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

import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class LiveMarketAdapter extends RecyclerView.Adapter<LiveMarketAdapter.ViewHolder>  {

    private OOUArrayOfSecurityLivePrice LivesecurityPrices;
    final Vector<ViewHolder> securityPriceListViewHold = new Vector<>();
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    Dialog dialog;
    int position = 1;
    TextView txtcompanyname;
    TextView txtlastdealvalue;
    TextView txtlasttradequantity;
    TextView txtvolume;
    TextView txtchange;
    TextView txthigh;
    TextView txtlow;

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


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livemarketprices, parent, false);

        dialog = new Dialog(mycontext,R.style.Theme_MaterialComponents_Dialog_MinWidth);
        dialog.setContentView(R.layout.custom_pop_up);

        txtcompanyname = (TextView)dialog.findViewById(R.id.txtcompanyname);
        txtlasttradequantity = (TextView)dialog.findViewById(R.id.lasttradequality);
        txtvolume = (TextView)dialog.findViewById(R.id.txtvolume);
        txtchange = (TextView)dialog.findViewById(R.id.txtchange);
        txtlastdealvalue = (TextView)dialog.findViewById(R.id.txtlastdealvalue);
        txthigh = (TextView)dialog.findViewById(R.id.txthigh);
        txtlow = (TextView)dialog.findViewById(R.id.txtlow);
        LinearLayout closeModal = dialog.findViewById(R.id.layoutclose);

        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull LiveMarketAdapter.ViewHolder holder, final int position) {


        _position = position;
        NumberFormat formatter = new DecimalFormat("###.##");
        String openingPrice = formatter.format(LivesecurityPrices.get(position).OpeningPrice);
        holder.itemView.setTag(LivesecurityPrices.get(position));
        holder.company.setText(LivesecurityPrices.get(position).Company);
        holder.marketcap.setText(LivesecurityPrices.get(position).MarketCap.toString());
        holder.OpeningPrice.setText(openingPrice);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String change = (LivesecurityPrices.get(position).Change.toString());
                String high = (LivesecurityPrices.get(position).Change.toString());
                String low = (LivesecurityPrices.get(position).Change.toString());
                if(change.contains("0E-9") || high.contains("0E-9") || low.contains("0E-9")){
                    double _c = Double.parseDouble(change);
                    double _h = Double.parseDouble(high);
                    double _l = Double.parseDouble(low);
                    NumberFormat formatter = new DecimalFormat("###.#####");
                    String f = formatter.format(_c);
                    String h = formatter.format(_h);
                    String l = formatter.format(_l);
                    txtchange.setText(f);
                    txthigh.setText(h);
                    txtlow.setText(l);
                }
                txtcompanyname.setText(LivesecurityPrices.get(position).Company);
                txtlastdealvalue.setText(LivesecurityPrices.get(position).LastDealPrice.toString());
                txtlasttradequantity.setText(LivesecurityPrices.get(position).LastTradedQuantity.toString());
                txtvolume.setText(LivesecurityPrices.get(position).Volume.toString());
                //txtchange.setText(LivesecurityPrices.get(position).Change.toString());
                dialog.show();
                //notifyDataSetChanged();

            }
        });

    }


    @Override
    public int getItemCount() {
        return LivesecurityPrices.size();
    }


}
