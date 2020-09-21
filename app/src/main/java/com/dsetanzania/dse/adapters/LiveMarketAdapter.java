package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.live_data.SecurityLivePrice;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class LiveMarketAdapter extends RecyclerView.Adapter<LiveMarketAdapter.ViewHolder>  {

    private List<SecurityLivePrice> LivesecurityPrices;
    Context mycontext;
    int _position;
    Dialog dialog;
    TextView txtcompanyname;
    TextView txtlastdealvalue;
    TextView txtlasttradequantity;
    TextView txtvolume;
    TextView txtchange;
    TextView txthigh;
    TextView txtlow;
    TextView txtdate;


    public LiveMarketAdapter(Context context,List<SecurityLivePrice> list) {
        this.LivesecurityPrices = list;
        this.mycontext =  context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView company;
        TextView marketcap;
        TextView OpeningPrice;
        LinearLayout parentlayout;
        TextView txtClosing;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //currentSelectedServer = itemView.findViewById(R.id.currentConnectedServer);


            company = itemView.findViewById(R.id.txtxompany);
            marketcap = itemView.findViewById(R.id.txtmarketcap);
            OpeningPrice = itemView.findViewById(R.id.txtopeningprice);
            txtClosing = itemView.findViewById(R.id.closingTxt);
            //parentlayout = itemView.findViewById(R.id.serverlistLayout);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livemarketprices, parent, false);

        dialog = new Dialog(mycontext,R.style.Mydialogtheme);
        dialog.setContentView(R.layout.custom_pop_up);

        txtdate = (TextView) dialog.findViewById(R.id.txtdate);
        txtcompanyname = (TextView)dialog.findViewById(R.id.txtcompanyname);
        txtlasttradequantity = (TextView)dialog.findViewById(R.id.lasttradequality);
        txtvolume = (TextView)dialog.findViewById(R.id.txtvolume);
        txtchange = (TextView)dialog.findViewById(R.id.txtchange);
        txtlastdealvalue = (TextView)dialog.findViewById(R.id.txtlastdealvalue);
        txthigh = (TextView)dialog.findViewById(R.id.txthigh);
        txtlow = (TextView)dialog.findViewById(R.id.txtlow);
        Button closeModal = dialog.findViewById(R.id.btnclose);

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
        final NumberFormat formatter = new DecimalFormat("#,###");
        DecimalFormat format = new DecimalFormat("0.#");
        holder.itemView.setTag(LivesecurityPrices.get(position));
        holder.OpeningPrice.setText(format.format(Double.valueOf(LivesecurityPrices.get(position).getOpeningPrice())));
        holder.company.setText(LivesecurityPrices.get(position).getCompany());
        holder.marketcap.setText("M.CAP " + (formatValue(Double.valueOf(LivesecurityPrices.get(position).getMarketCap()))));
        holder.txtClosing.setText("↑(" + format.format(Double.valueOf(LivesecurityPrices.get(position).getHigh())) + ")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               /* txtchange.setText(format.format(Double.valueOf(LivesecurityPrices.get(position).getChange())));
                txthigh.setText(format.format(Double.valueOf(LivesecurityPrices.get(position).getHigh())));
                txtlow.setText(format.format(Double.valueOf(LivesecurityPrices.get(position).getLow())));
                txtcompanyname.setText(LivesecurityPrices.get(position).getCompany());
                txtlastdealvalue.setText(format.format(Double.valueOf(LivesecurityPrices.get(position).getLastDealPrice())));
                txtlasttradequantity.setText(String.valueOf(format.format(Double.valueOf(LivesecurityPrices.get(position).getLastTradedQuantity()))));
                txtvolume.setText(String.valueOf(format.format(Double.valueOf(LivesecurityPrices.get(position).getVolume()))));
                txtdate.setText(getDateTime().substring(0,10));
                dialog.show();*/
                //notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        try {
            return LivesecurityPrices.size();
        }catch (Exception ex){

        }
        return 0;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
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
