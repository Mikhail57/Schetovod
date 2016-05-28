package me.mustakimov.scetovod.MainScreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import me.mustakimov.scetovod.CommonVariables;
import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.adapters.PurchaseAdapter;
import me.mustakimov.scetovod.util.PurchasesUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasesFragment extends Fragment {

    static PurchaseAdapter adapter;

    public PurchasesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchases, container, false);

        ListView purchasesListView = (ListView) view.findViewById(R.id.purchasesListView);
        adapter = new PurchaseAdapter(getActivity(), CommonVariables.getPurchases());
        purchasesListView.setAdapter(adapter);

        PurchasesUtils.calculateAndUpdateSumView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyDataChanged();
        PurchasesUtils.calculateAndUpdateSumView(getView());
    }

    public static void notifyDataChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
