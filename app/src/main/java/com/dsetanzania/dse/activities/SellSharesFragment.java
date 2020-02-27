package com.dsetanzania.dse.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.Transactions;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.dsetanzania.dse.activities.BuyShareActivity.getCurrentTimeStamp;

public class SellSharesFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    RecyclerView recyclerView;
    View view;
    Spinner boardSpinner;
    DatabaseReference reference;
    User thisUserClass;
    TextView txtvirtualbalance;
    TextView txtstock;
    TextView txtrefId;
    FirebaseAuth mAuth;
    FirebaseUser fuser;
    TextInputEditText txtprice;
    TextInputEditText txtquantity;
    TextView statustxt;
    Button btnsell;
    double updatedbalance;
    double updatedStock;
     Double virtualmoney;
     int stock;
    ArrayList<Transactions> transactionArray;
    User otheruserclass;
    Transactions _transaction;
    ArrayList<User> otherUserArray;
    int status = 1;

    // newInstance constructor for creating fragment with arguments
    public static SellSharesFragment newInstance(int page, String title) {
        SellSharesFragment faqsFragment = new SellSharesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        faqsFragment.setArguments(args);
        return faqsFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_sales, container, false);
            boardSpinner = (Spinner) view.findViewById(R.id.spinnerBoard);
            mAuth = FirebaseAuth.getInstance();
            txtstock = (TextView) view.findViewById(R.id.txtstock);
            txtvirtualbalance = (TextView) view.findViewById(R.id.txtvirtualbalance);
            txtrefId = (TextView) view.findViewById(R.id.txtreferenceId);
            txtrefId.setText("#DSE" + generateguid());
            txtprice = view.findViewById(R.id.txtprice);
            txtquantity = view.findViewById(R.id.txtquantity);
            btnsell = (Button) view.findViewById(R.id.btnSell);

            thisUserClass = new User();
            otheruserclass = new User();
            _transaction = new Transactions();


            btnsell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(txtprice.getText().toString().trim()) || TextUtils.isEmpty(txtquantity.getText().toString().trim())){
                        Toast.makeText(getContext(),"Please place your quantity or price to sell",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(thisUserClass.getStock()<=0){
                        System.out.println("You have no stock");
                        Toast.makeText(getContext(),"You have less stock please buy first in order to sell",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(Double.parseDouble(txtprice.getText().toString().trim()) > thisUserClass.getVirtualmoney()){
                        System.out.println("You have less virtual balance");

                    }

                    if(transactionArray.isEmpty()){
                        pushTransaction("Queued","","","");
                        txtrefId.setText("#DSE" + generateguid());
                        Toast.makeText(getContext(),"Sales transaction was queued",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    /////////////////


                    for(final Transactions trns : transactionArray){
                        for (User usr : otherUserArray){

                            if(usr.getUserId().equals(trns.getUserId()) && trns.getPrice() == Double.parseDouble(txtprice.getText().toString().trim()) && trns.getBoard().equals(boardSpinner.getSelectedItem().toString()) && trns.getType().equals("Purchase") && trns.getStatus().equals("Queued")){

                                if(trns.getUserId().equals(fuser.getUid())){
                                    pushTransaction("Queued","","","");
                                    txtrefId.setText("#DSE" + generateguid());
                                    Toast.makeText(getContext(),"Sales transaction was queued",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(trns.getUserId());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Double vm = dataSnapshot.child("virtualmoney").getValue(Double.class);
                                        int stock = dataSnapshot.child("stock").getValue(Integer.class);
                                        String username = dataSnapshot.child("tradername").getValue(String.class);
                                        String university = dataSnapshot.child("university").getValue(String.class);

                                        Map<String, Object> _Umap = new HashMap<>();
                                        _Umap.put("virtualmoney",  vm - (trns.getPrice() * trns.getShareAmount()));
                                        _Umap.put("stock",  stock + trns.getShareAmount());
                                        dataSnapshot.getRef().updateChildren(_Umap);

                                        pushTransaction("Successfully",username,getdate(),university);
                                        reference = FirebaseDatabase.getInstance().getReference("Transactions").child(trns.getTransId());
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("status", "Successfully");
                                        map.put("boughtOrSoldBy", thisUserClass.getTradername());
                                        map.put("transactionSuccessfulldate",getdate());
                                        map.put("universictyfrom",thisUserClass.getUniversity());
                                        reference.updateChildren(map);
                                    }



                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Double vm = dataSnapshot.child("virtualmoney").getValue(Double.class);
                                        int stock = dataSnapshot.child("stock").getValue(Integer.class);
                                        String tradername = dataSnapshot.child("tradername").getValue(String.class);
                                        String university = dataSnapshot.child("university").getValue(String.class);
                                        Map<String, Object> _Umap = new HashMap<>();
                                        _Umap.put("virtualmoney",  vm + (Double.parseDouble(txtprice.getText().toString().trim()) * Integer.parseInt(txtquantity.getText().toString().trim())));
                                        _Umap.put("stock",  stock - Integer.parseInt(txtquantity.getText().toString().trim()));
                                        dataSnapshot.getRef().updateChildren(_Umap);
                                        Toast.makeText(getContext(),"Sales transaction was Successfully",Toast.LENGTH_SHORT).show();
                                        txtrefId.setText("#DSE" + generateguid());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                status = 3;
                                break;
                            }
                            else {
                                status = 2;
                            }

                        }

                        if(status == 3){
                            break;
                        }
                    }
                    if(status == 2){
                        pushTransaction("Queued","","","");
                        txtrefId.setText("#DSE" + generateguid());
                        Toast.makeText(getContext(),"Sales transaction was queued",Toast.LENGTH_SHORT).show();
                    }


                    getTransactionsAndOtherUser();
                }
            });

            getTransactionsAndOtherUser();
            getBoard();
            getUserInfo();



        return view;
    }

    private void  getBoard(){
        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> _boards = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String _b = snapshot.child("company").getValue(String.class);
                    _boards.add(_b);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinnertext, _boards);
                areasAdapter.setDropDownViewResource(R.layout.spinnerdropdown);
                boardSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserInfo(){
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NumberFormat formatter = new DecimalFormat("#,###");
                thisUserClass = dataSnapshot.getValue(User.class);
                txtstock.setText(String.valueOf(thisUserClass.getStock()));
                txtvirtualbalance.setText((formatter.format(thisUserClass.getVirtualmoney())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getTransactionsAndOtherUser(){

        transactionArray = new ArrayList<Transactions>();
        reference = FirebaseDatabase.getInstance().getReference("Transactions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    _transaction = snapshot.getValue(Transactions.class);
                     transactionArray.add(_transaction);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        otherUserArray = new ArrayList<User>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    otheruserclass = snapshot.getValue(User.class);
                    otherUserArray.add(otheruserclass);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private String generateguid(){
        String  ticketId = UUID.randomUUID().toString();
        return ticketId.substring(0,3);
    }

    public void pushTransaction(String status,String boughtby,String date,String university){

        String companyname = boardSpinner.getSelectedItem().toString();

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("Transactions");
        String id = reference.push().getKey();
        Transactions tickets = new Transactions("DSE" + generateguid(),fuser.getUid(),status,getCurrentTimeStamp(),companyname,Double.parseDouble(txtprice.getText().toString().trim()),Integer.parseInt(txtquantity.getText().toString().trim()),"Sales",id,boughtby,date,university,"thidParty");
        reference.child(id).setValue(tickets).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // Toast.makeText(getContext(),"Transaction pushed.",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateForMe(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fuser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        updatedbalance = thisUserClass.getVirtualmoney() + Double.parseDouble(txtprice.getText().toString().trim());
        updatedStock = thisUserClass.getStock() - Integer.parseInt(txtquantity.getText().toString().trim());
        Map<String, Object> map = new HashMap<>();
        map.put("virtualmoney", updatedbalance);
        map.put("stock", updatedStock);
        reference.updateChildren(map);
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

   /* public void updateForOther(){

        final Double price =Double.parseDouble(txtprice.getText().toString().trim());
        final String quantity = boardSpinner.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference("User").child(getUserId);

        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User otheruser = dataSnapshot.getValue(User.class);
                        double balance = otheruser.getVirtualmoney();
                        int stock = otheruser.getStock();
                        Map<String, Object> map = new HashMap<>();
                        map.put("virtualmoney", balance - price);
                        map.put("stock", stock + quantity);
                        reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getContext(),"Updated to other thisUserClass",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getContext(),"Not updated to other thisUserClass",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }*/



}

