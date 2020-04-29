package com.dsetanzania.dse.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.BuyBondsActivity;
import com.dsetanzania.dse.models.BondsModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SimulatedBondsAdapter extends RecyclerView.Adapter<SimulatedBondsAdapter.ViewHolder>  {

    private List<BondsModel> bonds;

    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public SimulatedBondsAdapter(Context context, List<BondsModel> bonds) {
        this.bonds = bonds;
        this.mycontext =  context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtbondrate;
        TextView txtbonddate;
        TextView txtbondduration;
        TextView txtbondnumber;
        Button buybtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          //  txtbonddate = (TextView)itemView.findViewById(R.id.txtbonddate);
            txtbondduration = (TextView)itemView.findViewById(R.id.txtbondduration);
            txtbondnumber = (TextView)itemView.findViewById(R.id.txtbondnumber);
            txtbondrate = (TextView)itemView.findViewById(R.id.txtbondrate);
            //LinearLayout closeModal = dialog.findViewById(R.id.layoutclose);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonds_list_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        _position = position;

        holder.itemView.setTag(bonds.get(position));
        holder.txtbondnumber.setText("No. " + String.valueOf(bonds.get(position).getBondnumber()));
        holder.txtbondrate.setText(String.valueOf(bonds.get(position).getRate()) + " %");
        //holder.txtbonddate.setText(bonds.get(position).getDate().substring(0,10));
        holder.txtbondduration.setText("Duration : " + String.valueOf(bonds.get(position).getMonth()) + " Month");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mycontext, BuyBondsActivity.class);
                myIntent.putExtra("Bondnumber",String.valueOf(bonds.get(position).getBondnumber()));
                myIntent.putExtra("Rate",String.valueOf(bonds.get(position).getRate()));
                myIntent.putExtra("Duration",String.valueOf(bonds.get(position).getMonth()));
                mycontext.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bonds.size();
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