package me.mustakimov.scetovod.MainScreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        registerForContextMenu(purchasesListView);

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.purchasesListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(CommonVariables.getPurchases().get(info.position).getTitle());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        //String[] menuItems = getResources().getStringArray(R.array.menu);

        if (menuItemIndex==0) {
            CommonVariables.getPurchases().get(info.position).setDeleted(true);
            notifyDataChanged();
        }

        //TextView text = (TextView)findViewById(R.id.footer);
        //text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
        return true;
    }

}
