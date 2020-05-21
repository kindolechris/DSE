package com.dsetanzania.dse.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.FaqsAdapter;
import com.dsetanzania.dse.adapters.SimulatedBondsAdapter;
import com.dsetanzania.dse.helperClasses.FaqsChildLayout;
import com.dsetanzania.dse.helperClasses.FaqsParentLayout;
import com.dsetanzania.dse.helperClasses.FaqsTitleCreator;
import com.dsetanzania.dse.models.BondTransactionModel;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FaqLisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaqLisFragment extends Fragment {


    private String title;
    private int page;
    ArrayList<BondTransactionModel> bonds;
    View view;
    RecyclerView recyclerView;

    // newInstance constructor for creating fragment with arguments
    public static FaqLisFragment newInstance(int page, String title) {
        FaqLisFragment faqLisFragment = new FaqLisFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        faqLisFragment.setArguments(args);
        return faqLisFragment;
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

        view = inflater.inflate(R.layout.fragment_faq_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.faqsrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FaqsAdapter adapter = new FaqsAdapter(getContext(),initData());
        adapter.setCustomParentAnimationViewId(R.id.expandArrow);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<ParentObject> initData() {
        FaqsTitleCreator titleCreator = FaqsTitleCreator.get(getContext());
        List<FaqsParentLayout> titles = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        Resources res = getResources();
        String[] content = res.getStringArray(R.array.Faqs_content);
        int contentIndex = 0;
        for(FaqsParentLayout title:titles)
        {

            List<Object> childList = new ArrayList<>();
            childList.add(new FaqsChildLayout(content[contentIndex]));
            title.setChildObjectList(childList);
            parentObject.add(title);
            contentIndex++;
        }
        return parentObject;

    }

}
