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
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.BondTransaction;
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


public class SellBondsFragment extends Fragment {

    View view;

    private String title;
    private int page;
    DatabaseReference reference;
    FirebaseAuth mauth;
    FirebaseUser firebaseUser;
    User user;
    TextView txtholdings;
    TextView txtbondAmount;
    Spinner bondspinner;
    Button btnsellbonds;
    TextInputEditText txtquantity;
    TextInputEditText txtprice;


    // newInstance constructor for creating fragment with arguments
    public static SellBondsFragment newInstance(int page, String title) {
        SellBondsFragment faqsFragment = new SellBondsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(view == null){
            view = inflater.inflate(R.layout.fragment_sell_bond, container, false);
            user = new User();

            txtquantity = (TextInputEditText) view.findViewById(R.id.txtquantity);
            txtprice = (TextInputEditText) view.findViewById(R.id.txtprice);

            txtholdings = (TextView) view.findViewById(R.id.txtbondholdings);
            txtbondAmount = (TextView) view.findViewById(R.id.txtvirtualbalance);
            bondspinner = (Spinner) view.findViewById(R.id.spinnerBonds);
            btnsellbonds = (Button) view.findViewById(R.id.btnsellbonds);

            mauth = FirebaseAuth.getInstance();
            firebaseUser = mauth.getCurrentUser();
            getUserInfo();
            getuserbond();


            btnsellbonds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(txtquantity.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim())){
                        Toast.makeText(getContext(),"Enter price and quantity to sell",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double consideration = (Integer.parseInt(txtprice.getText().toString().trim()) / 100.00) * Integer.parseInt(txtquantity.getText().toString().trim());
                    checkuserfunds(Integer.parseInt(txtquantity.getText().toString().trim()),Integer.parseInt(txtprice.getText().toString().trim()),consideration);
                    //double value = consideration;
                }
            });
        }

        return view;
    }



    public void getUserInfo(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                NumberFormat formatter = new DecimalFormat("#,###");
                txtholdings.setText(String.valueOf(user.getBonds()));
                txtbondAmount.setText(formatter.format(user.getVirtualmoney()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void  getuserbond(){
        reference = FirebaseDatabase.getInstance().getReference("Bonds");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> _boards = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    int _b = snapshot.child("bondnumber").getValue(Integer.class);
                    _boards.add(String.valueOf(_b));
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinnertext, _boards);
                areasAdapter.setDropDownViewResource(R.layout.spinnerdropdown);
                bondspinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateUserInfo(int bonds,double consideration){

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int _b = dataSnapshot.child("bonds").getValue(Integer.class);
                double _virtualmoney = dataSnapshot.child("virtualmoney").getValue(Integer.class);
                Map<String, Object> map = new HashMap<>();
                map.put("bonds", _b - bonds);
                map.put("virtualmoney", _virtualmoney + consideration);
                reference.updateChildren(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sellbonds(int units,int price,double consideration) {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("BondsTransaction");
        String id = reference.push().getKey();
        BondTransaction bondTransaction = new BondTransaction(id,firebaseUser.getUid(),bondspinner.getSelectedItem().toString(),"Successfully",consideration,"Sales",units,price,getCurrentTimeStamp());
        reference.child(id).setValue(bondTransaction).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Bond was sold successfully.",Toast.LENGTH_LONG).show();
                updateUserInfo(units,consideration);
            }
        });
    }


    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    public void checkuserfunds(int units,int price,double consideration){

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int _b = dataSnapshot.child("bonds").getValue(Integer.class);

                if(_b < units){
                    Toast.makeText(getActivity(),"You have less bonds to sell",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    sellbonds(units,price,consideration);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

