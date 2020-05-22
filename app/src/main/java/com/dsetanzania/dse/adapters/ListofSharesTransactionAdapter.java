package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListofSharesTransactionAdapter extends RecyclerSwipeAdapter<ListofSharesTransactionAdapter.ViewHolder> {

    private List<PersonalsharesTransactionModel> transactions;
    Context context;
    DbHelper dbHelper;
    SQLiteDatabase database;


    public ListofSharesTransactionAdapter(Context context, List<PersonalsharesTransactionModel> transactions) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ListofSharesTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);

        ListofSharesTransactionAdapter.ViewHolder viewHolder = new ListofSharesTransactionAdapter.ViewHolder(view);

        return viewHolder;
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnclose;
                TextView txtdescription;
                TextView txttransactioncompanyname;
                TextView txttransactiondate;
                final Dialog dialog = new Dialog(context,R.style.Mydialogtheme);
                dialog.setCancelable(true); // dismiss when touching outside Dialog
                dialog.setContentView(R.layout.shares_transaction_pop_up);
                btnclose = (Button) dialog.findViewById(R.id.btnclose);
                txtdescription = (TextView) dialog.findViewById(R.id.txtdescription);
                txttransactioncompanyname = (TextView) dialog.findViewById(R.id.txttransactioncompanyname);
                txttransactiondate = (TextView) dialog.findViewById(R.id.txttransactiondate);
                setDataToView(txtdescription,txttransactiondate,txttransactioncompanyname,position);
                dialog.show();

                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        };
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsatus;
        TextView txtdate;
        TextView txtcompanyname;
        TextView txttype;
        public TextView Delete;
        public SwipeLayout swipeLayout;
        public LinearLayout sharetransactionlayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            txtsatus = (TextView)itemView.findViewById(R.id.txtstatus);
            txtdate = (TextView)itemView.findViewById(R.id.txttransactiondate);
            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompanyname);
            sharetransactionlayout = (LinearLayout) itemView.findViewById(R.id.sharetransactionlayout);
            txttype = (TextView)itemView.findViewById(R.id.txttype);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListofSharesTransactionAdapter.ViewHolder holder, final int position) {
        final PersonalsharesTransactionModel item = transactions.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
      holder.itemView.setTag(item);
        holder.txtdate.setText(item.getTimeago());
        holder.txtcompanyname.setText(item.getCompanyname());
        holder.txttype.setText("Type : " + capitalize(item.getTransactiontype()));

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wraper));

        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        holder.sharetransactionlayout.setOnClickListener(onClickListener(position));


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getStatus().equalsIgnoreCase("closed")){
                    Toast.makeText(v.getContext(), "Transaction is closed, can't be cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }
                mItemManger.removeShownLayouts(holder.swipeLayout);
                transactions.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, transactions.size());
                mItemManger.closeAllItems();
                deleteRow(String.valueOf(item.getId()));
                Toast.makeText(v.getContext(), "Transaction " + holder.txtcompanyname.getText().toString() + " cancelled", Toast.LENGTH_SHORT).show();
            }
        });


        if(item.getStatus().equalsIgnoreCase("closed")){
            holder.txtsatus.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.txtsatus.setText("Status : " + capitalize(item.getStatus()));

        }else {
            holder.txtsatus.setTextColor(context.getResources().getColor(R.color.colorSuccess));
            holder.txtsatus.setText("Status : " + capitalize(item.getStatus()));
        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static String parseDate(String givenDateString) {
        if (givenDateString.equalsIgnoreCase("")) {
            return "";
        }

        long timeInMilliseconds=0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {

            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String result = "now";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String todayDate = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong =  timeInMilliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(todayDate);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            different = different % secondsInMilli;
            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedDays > 0 && elapsedSeconds < 59) {
                                return "now";
                            }
                        }
                    } else {
                        return String.valueOf(elapsedMinutes) + "mins ago";
                    }
                } else {
                    return String.valueOf(elapsedHours) + "hr ago";
                }

            } else {
                if (elapsedDays <= 29) {
                    return String.valueOf(elapsedDays) + "d ago";

                }
                else if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1Mth ago";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2Mth ago";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3Mth ago";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4Mth ago";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5Mth ago";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6Mth ago";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7Mth ago";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8Mth ago";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9Mth ago";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10Mth ago";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11Mth ago";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12Mth ago";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 year ago";
                }
            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }



    public String format(String date){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        Date d = null;
        try
        {
            d = input.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(d);

        return formatted;
    }

    public static String capitalize(String str)
    {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void deleteRow(String value)
    {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + DbContract.SHARE_TRANSACTION_TABLE + " WHERE id "+"='"+value+"'");
        database.close();
    }
    private void setDataToView(TextView description,TextView date,TextView companyname,int position) {
        date.setText(transactions.get(position).getCreatedAt().substring(0,10));
        companyname.setText(transactions.get(position).getCompanyname());
        if(transactions.get(position).getStatus().equalsIgnoreCase("opened")){
            if(transactions.get(position).getTransactiontype().equalsIgnoreCase("sell")){
                description.setText("This transaction is placed on the market, you will be notified once a buyer found.");
            }
            else{
                description.setText("This transaction is placed on the market, you will be notified once a market price matches.");
            }
            return;
        }
        description.setText("Transaction closed");

    }
}
