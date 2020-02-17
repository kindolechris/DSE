package com.dsetanzania.dse;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.helperClasses.TimeAgo;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.models.Transactions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListOfTransactionAdapter  extends RecyclerView.Adapter<ListOfTransactionAdapter.ViewHolder> {

    private List<Transactions> transactions;
    Context context;
    Dialog dialog;

    int index;
    TextView txtdatesoldorpurchased;
    TextView personwhosoldorpurchased;
    TextView univercitytype;
    TextView transactiontype;
    TextView notransactiontxt;
    LinearLayout transactionlayout;

    public ListOfTransactionAdapter(Context context, List<Transactions> transactions) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listoftransactions, parent, false);

        dialog = new Dialog(context,R.style.Mydialogtheme);
        dialog.setContentView(R.layout.custom_pop_up_info);
        LinearLayout closeModal = dialog.findViewById(R.id.layoutclose);

        txtdatesoldorpurchased = (TextView) dialog.findViewById(R.id.dateoftransactiontxt);
        personwhosoldorpurchased = (TextView) dialog.findViewById(R.id.personwhoboughtorsold);
        univercitytype = (TextView) dialog.findViewById(R.id.universitynametxt);
        transactiontype = (TextView) dialog.findViewById(R.id.transactiontypetxt);
        notransactiontxt = (TextView) dialog.findViewById(R.id.notransactiontxt);
        transactionlayout = (LinearLayout) dialog.findViewById(R.id.transactionInfoLayout);

        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        ListOfTransactionAdapter.ViewHolder viewHolder = new ListOfTransactionAdapter.ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsatus;
        TextView txtdate;
        TextView txtid;
        TextView txttype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsatus = (TextView)itemView.findViewById(R.id.txtstatus);
            txtdate = (TextView)itemView.findViewById(R.id.txttransactiondate);
            txtid = (TextView)itemView.findViewById(R.id.txttransactionId);
            txttype = (TextView)itemView.findViewById(R.id.txttype);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfTransactionAdapter.ViewHolder holder, final int position) {

        holder.itemView.setTag(transactions.get(position));
        String gettimeAgo = parseDate(transactions.get(position).getDate());
        holder.txtdate.setText(gettimeAgo);
        holder.txtid.setText(transactions.get(position).getId());
        holder.txttype.setText(transactions.get(position).getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(transactions.get(position).getStatus().equals("Successfully")){
                    transactionlayout.setVisibility(View.VISIBLE);
                    notransactiontxt.setVisibility(View.INVISIBLE);
                    txtdatesoldorpurchased.setText(transactions.get(position).getTransactionSuccessfulldate());
                    personwhosoldorpurchased.setText(getCapsSentences(transactions.get(position).getBoughtOrSoldBy()));
                    univercitytype.setText(transactions.get(position).getUniversictyfrom());

                    if(transactions.get(position).getType().equals("Sales")){
                        transactiontype.setText("Was bought by : ");
                    }else {
                        transactiontype.setText("Was sold by : ");
                    }
                }else {
                    transactionlayout.setVisibility(View.INVISIBLE);
                    notransactiontxt.setVisibility(View.VISIBLE);
                }
                dialog.show();

            }
        });

        if(transactions.get(position).getStatus().equals("Successfully")){
            holder.txtsatus.setTextColor(context.getResources().getColor(R.color.colorSuccess));
            holder.txtsatus.setText(transactions.get(position).getStatus());

        }else {
            holder.txtsatus.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.txtsatus.setText(transactions.get(position).getStatus());
            transactionlayout.setVisibility(View.INVISIBLE);
            notransactiontxt.setVisibility(View.VISIBLE);
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
}
