package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.BoardSharesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulatedMarketToEditAdapter extends RecyclerView.Adapter<SimulatedMarketToEditAdapter.ViewHolder> implements Filterable {

    List<BoardSharesModel> marketSimulator;
    List<BoardSharesModel> marketSimulatorFilter;
    Context mycontext;
    int _position;
    int selectedPosition=-1;
    ItemClicked fragmentActivity;
    int position = 1;
    Dialog dialog;
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
            List<BoardSharesModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(marketSimulatorFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BoardSharesModel item : marketSimulatorFilter) {
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

    public SimulatedMarketToEditAdapter(Context context, List<BoardSharesModel> list) {
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

            dialog = new Dialog(mycontext, R.style.Mydialogtheme);
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
