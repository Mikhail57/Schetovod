package me.mustakimov.scetovod.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.model.PurchaseItem;
import me.mustakimov.scetovod.provider.SchetovodDatabase;

/**
 * Created by mikhail on 28/05/16.
 */
public class PurchaseAdapter extends BaseAdapter {

    Context context;
    List<PurchaseItem> purchases;
    LayoutInflater inflater;

    public PurchaseAdapter(Context context, List<PurchaseItem> purchases) {
        this.context = context;
        this.purchases = purchases;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return purchases.size();
    }

    @Override
    public Object getItem(int position) {
        return purchases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.purchase_item, parent, false);
        }

        TextView title, price;
        title = (TextView) view.findViewById(R.id.purchaseTitile);
        price = (TextView) view.findViewById(R.id.purchasePrice);

        title.setText(purchases.get(position).getTitle());
        price.setText(String.format("%.2f\u20BD", purchases.get(position).getPrice()));

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        sort();
        for (int i = 0; i < purchases.size(); i++) {
            PurchaseItem purchase = purchases.get(i);
            if (purchase.isDeleted()) {
                SchetovodDatabase.deletePurchase(context, purchase.getId());
                purchases.remove(i);
                i--;
            }
        }
        super.notifyDataSetChanged();
    }

    public void sort() {
        Collections.sort(purchases, new PurchasesComparator());
        Collections.reverse(purchases);
    }



    class PurchasesComparator implements Comparator<PurchaseItem> {

        @Override
        public int compare(PurchaseItem lhs, PurchaseItem rhs) {
            int result;
            result = (lhs.getDate() > rhs.getDate()) ? 1 : (lhs.getDate() == rhs.getDate()) ? 0 : -1;
            return result;
        }
    }
}
