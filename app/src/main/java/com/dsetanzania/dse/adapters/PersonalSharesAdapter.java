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
import com.dsetanzania.dse.activities.BuyPersonShareActivity;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class PersonalSharesAdapter extends RecyclerView.Adapter<PersonalSharesAdapter.ViewHolder>  {

    private List<PersonalsharesTransactionModel> personalShareData;
    final Vector<ViewHolder> securityPriceListViewHold = new Vector<>();
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public PersonalSharesAdapter(Context context, List<PersonalsharesTransactionModel> list) {
        this.personalShareData = list;
        this.mycontext =  context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcompanyname;
        TextView txtstatus;
        TextView txtlquantity;
        TextView txtprice;
        LinearLayout personalshareLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompanyname);
            txtstatus = (TextView)itemView.findViewById(R.id.txtstatus);
            txtlquantity = (TextView)itemView.findViewById(R.id.txtquantity);
            txtprice = (TextView)itemView.findViewById(R.id.openingPrice);
            personalshareLayout = (LinearLayout)itemView.findViewById(R.id.personalshareLayout);
            //buybtn = (Button) itemView.findViewById(R.id.btnbuy);
            //sellbtn = (Button) itemView.findViewById(R.id.btnSell);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_shares_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       _position = -1;
        NumberFormat formatter = new DecimalFormat("#,###");
        holder.itemView.setTag(personalShareData.get(position));
        holder.txtcompanyname.setText(personalShareData.get(position).getCompanyname());
        holder.txtprice.setText("Sold at : " + formatter.format(personalShareData.get(position).getPrice()));
        holder.txtlquantity.setText("Quantity : " + formatter.format(personalShareData.get(position).getSharesamount()));
        holder.txtstatus.setText("Time : " + personalShareData.get(position).getTimeago());

        holder.personalshareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, BuyPersonShareActivity.class);
                myIntent.putExtra("OpeningPrice", String.valueOf(personalShareData.get(position).getPrice()));
                myIntent.putExtra("Companyname", personalShareData.get(position).getCompanyname());
                myIntent.putExtra("boardshare_id", String.valueOf(personalShareData.get(position).getBoardshareId()));
                myIntent.putExtra("peerid", String.valueOf(personalShareData.get(position).getId()));
                mycontext.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personalShareData.size();
    }

    public  String formatExponential(String value){
        double _c = Double.parseDouble(value);
        NumberFormat formatter = new DecimalFormat("###.##");
        String f = formatter.format(_c);
        return f;
    }


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