package me.mustakimov.scetovod.util;

import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.mustakimov.scetovod.CommonVariables;
import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.model.PurchaseItem;

/**
 * Created by mikhail on 28/05/16.
 */
public class PurchasesUtils {

    public static List<PurchaseItem> getTodayPurchases(List<PurchaseItem> purchases) {
        List<PurchaseItem> result = new ArrayList<>();

        for (PurchaseItem purchase : purchases) {
            if (DateUtils.isToday(purchase.getDate())) {
                result.add(purchase);
            }
        }

        return result;
    }

    public static double calculateTodaySum(List<PurchaseItem> purchases) {
        double result = 0;

        List<PurchaseItem> todays = getTodayPurchases(purchases);
        for (PurchaseItem purchase : todays) {
            result += purchase.getPrice();
        }

        return result;
    }

    public static void calculateAndUpdateSumView(View view) {
        new CalculateTodaySumAndUpdateTextView().execute(view);
    }


    static private class CalculateTodaySumAndUpdateTextView extends AsyncTask<View, Void, Void> {

        View view;
        double today;

        @Override
        protected Void doInBackground(View... params) {
            view = params[0];
            today = calculateTodaySum(CommonVariables.getPurchases());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView sumText = (TextView) view.findViewById(R.id.todaySum);
            sumText.setText(String.format("%d\u20BD", (int)Math.ceil(today)));
        }
    }
}
