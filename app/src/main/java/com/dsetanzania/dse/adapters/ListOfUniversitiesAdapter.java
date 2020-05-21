package com.dsetanzania.dse.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.UniversityModel;

import java.util.ArrayList;
import java.util.List;

public class ListOfUniversitiesAdapter extends RecyclerView.Adapter<ListOfUniversitiesAdapter.ViewHolder> implements Filterable {

    private ArrayList<UniversityModel> universitymodel;
    private ArrayList<UniversityModel> universitymodelFilter;
    Context mycontext;
    ItemClicked fragmentActivity;

    @Override
    public Filter getFilter() {
        return filteredItem;
    }

    private Filter filteredItem = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UniversityModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(universitymodelFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (UniversityModel item : universitymodelFilter) {
                    if (item.getUniversityname().toLowerCase().contains(filterPattern)){
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
            universitymodel.clear();
            universitymodel.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public  interface  ItemClicked{
        void OnServerItemClicked(int index);
    }

    public ListOfUniversitiesAdapter(Context context, ArrayList<UniversityModel> list) {
        this.universitymodel = list;
        this.mycontext =  context;
        universitymodelFilter = new ArrayList<>(universitymodel);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtuniversityname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtuniversityname = (TextView)itemView.findViewById(R.id.txtuniversityname);
            //txtuniversityname.setSelected(true);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofuniverecyclerview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.itemView.setTag(universitymodel.get(position));
        holder.txtuniversityname.setText(universitymodel.get(position).getUniversityname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = mycontext.getSharedPreferences("universitypref", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("universityname",universitymodel.get(position).getUniversityname());
                editor.commit();
                ((Activity)mycontext).finish();
            }
        });

        //pushMarkets();
        //_position = position + 1;
    }

    @Override
    public int getItemCount() {
        return universitymodel.size();
    }

}