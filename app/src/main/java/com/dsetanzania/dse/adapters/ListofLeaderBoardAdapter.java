package com.dsetanzania.dse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.UserModel;

import java.util.List;

public class ListofLeaderBoardAdapter extends RecyclerView.Adapter<ListofLeaderBoardAdapter.ViewHolder> {

    private List<UserModel> users;
    Context context;
    int index;
    public ListofLeaderBoardAdapter(Context context, List<UserModel> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtname;
        TextView txtuniversity;
        TextView txtgender;
        TextView txtrate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = (TextView)itemView.findViewById(R.id.txtusername);
            txtuniversity = (TextView)itemView.findViewById(R.id.txtuniversity);
            txtgender = (TextView)itemView.findViewById(R.id.txtgender);
            txtrate = (TextView)itemView.findViewById(R.id.txtportfoliovalue);
            txtname.setSelected(true);
            txtuniversity.setSelected(true);
            txtrate.setSelected(true);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setTag(users.get(position));
        holder.txtname.setText(users.get(position).getTradername());
        holder.txtuniversity.setText(users.get(position).getUniversity());
        holder.txtgender.setText(users.get(position).getGender());
        holder.txtrate.setText(String.valueOf( "P.V : " + users.get(position).getPortfolioValue()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
