package com.dsetanzania.dse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    int index;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            txtsatus = (TextView)itemView.findViewById(R.id.txtstatus);
            txtdate = (TextView)itemView.findViewById(R.id.txttransactiondate);
            txtcompanyname = (TextView)itemView.findViewById(R.id.txtcompanyname);
            txttype = (TextView)itemView.findViewById(R.id.txttype);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListofSharesTransactionAdapter.ViewHolder holder, final int position) {
        final PersonalsharesTransactionModel item = transactions.get(position);
      holder.itemView.setTag(item);
        String gettimeAgo = format(item.getCreatedAt());
        holder.txtdate.setText(parseDate(gettimeAgo));
        holder.txtcompanyname.setText(item.getCompanyname());
        holder.txttype.setText("Type :: " + capitalize(item.getTransactiontype()));

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //dari kiri
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
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

        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " Transaction is on " + item.getStatus() + " Status", Toast.LENGTH_SHORT).show();
            }
        });

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               /* if(transactions.get(position).getStatus().equals("Successfully") && (!transactions.get(position).getTransactionParty().equals("fromCompany"))){
                    txtdatesoldorpurchased.setText(transactions.get(position).getTransactionSuccessfulldate());
                    personwhosoldorpurchased.setText(getCapsSentences(transactions.get(position).getBoughtOrSoldBy()));
                    univercitytype.setText(transactions.get(position).getUniversictyfrom());

                    if(transactions.get(position).getType().equals("Sales")){
                        transactiontype.setText("Was bought by : ");
                    }else {
                        transactiontype.setText("Was sold by : ");
                    }
                    dialog1.show();

                } else if(transactions.get(position).getStatus().equals("Queued")){
                    textdescription1.setText("Awaiting transaction");
                    dialog2.show();
                } else if ((transactions.get(position).getStatus().equals("Successfully") && (transactions.get(position).getTransactionParty().equals("fromCompany")) && transactions.get(position).getType().equals("Purchase"))){
                    textdescription2.setText("Bought from " + transactions.get(position).getBoard() + " on\n\n" + transactions.get(position).getDate().substring(0,10));
                    dialog3.show();
                }*/
            }
        });

        if(item.getStatus().equalsIgnoreCase("closed")){
            holder.txtsatus.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.txtsatus.setText("Status :: " + capitalize(item.getStatus()));

        }else {
            holder.txtsatus.setTextColor(context.getResources().getColor(R.color.colorSuccess));
            holder.txtsatus.setText(capitalize(item.getStatus()));
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

    private String getCapsSentences(String tagName) {
        String[] splits = tagName.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splits.length; i++) {
            String eachWord = splits[i];
            if (i > 0 && eachWord.length() > 0) {
                sb.append(" ");
            }
            String cap = eachWord.substring(0, 1).toUpperCase()
                    + eachWord.substring(1);
            sb.append(cap);
        }
        return sb.toString();
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
}
