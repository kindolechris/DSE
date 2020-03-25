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

import com.dsetanzania.dse.models.BondTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListofBondsTransactionAdapter extends RecyclerView.Adapter<ListofBondsTransactionAdapter.ViewHolder> {

    private List<BondTransaction> bonds;
    Context context;
    Dialog dialog1,dialog2,dialog3;

    int index;
    TextView txtdatesoldorpurchased;
    TextView personwhosoldorpurchased;
    TextView univercitytype;
    TextView transactiontype;
    TextView textdescription1;
    TextView textdescription2;
    LinearLayout transactionlayout;

    public ListofBondsTransactionAdapter(Context context, List<BondTransaction> bonds) {
        this.bonds = bonds;
        this.context = context;
    }

    @NonNull
    @Override
    public ListofBondsTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofbondstransactions, parent, false);

     /*   dialog1 = new Dialog(context,R.style.Mydialogtheme);
        dialog1.setContentView(R.layout.custom_pop_up_info1);

        dialog2 = new Dialog(context,R.style.Mydialogtheme);
        dialog2.setContentView(R.layout.custom_pop_up_info2);

        dialog3 = new Dialog(context,R.style.Mydialogtheme);
        dialog3.setContentView(R.layout.custom_pop_up_info3);

        LinearLayout closeModal1 = dialog1.findViewById(R.id.layoutclose);
        LinearLayout closeModal2 = dialog2.findViewById(R.id.layoutclose);
        LinearLayout closeModal3 = dialog3.findViewById(R.id.layoutclose);

        txtdatesoldorpurchased = (TextView) dialog1.findViewById(R.id.dateoftransactiontxt);
        personwhosoldorpurchased = (TextView) dialog1.findViewById(R.id.personwhoboughtorsold);
        univercitytype = (TextView) dialog1.findViewById(R.id.universitynametxt);
        transactiontype = (TextView) dialog1.findViewById(R.id.transactiontypetxt);
        textdescription1 = (TextView) dialog2.findViewById(R.id.notransactiontxt);
        textdescription2 = (TextView) dialog3.findViewById(R.id.notransactiontxt);
        transactionlayout = (LinearLayout) dialog1.findViewById(R.id.transactionInfoLayout);

        closeModal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });
        closeModal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.cancel();
            }
        });

        closeModal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.cancel();
            }
        });*/


        ListofBondsTransactionAdapter.ViewHolder viewHolder = new ListofBondsTransactionAdapter.ViewHolder(view);

        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtbondid;
        TextView txtdate;
        TextView txtstatus;
        TextView txttype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtstatus = (TextView)itemView.findViewById(R.id.txtstatus);
            txtbondid = (TextView)itemView.findViewById(R.id.txtbondid);
            txttype = (TextView)itemView.findViewById(R.id.txttype);
            txtdate = (TextView)itemView.findViewById(R.id.txtdate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListofBondsTransactionAdapter.ViewHolder holder, final int position) {

        holder.itemView.setTag(bonds.get(position));
        String gettimeAgo = parseDate(bonds.get(position).getDate());
        holder.txtdate.setText(gettimeAgo);
        holder.txtbondid.setText(bonds.get(position).getBondnumber());
        holder.txtstatus.setText(bonds.get(position).getBondstatus());
        holder.txttype.setText(bonds.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return bonds.size();
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

        } catch (ParseException e) {
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