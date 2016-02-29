package me.mustakimov.scetovod.model;

/**
 * Created by mikhail on 29/02/16.
 */

import android.support.annotation.NonNull;

import me.mustakimov.scetovod.provider.SchetovodContract;

public class PurchaseItem implements Comparable<PurchaseItem> {

    // Has been deleted this item?
    public boolean deleted = false;

    // Title ans description
    public String title = "";
    public String description = "";

    // Count and price
    public double count = 0;
    public double price = 0;

    // Date
    public int date = 0;

    @Override
    public int compareTo(@NonNull PurchaseItem another) {
        return this.date < another.date ? -1 :
                ( this.date > another.date ? 1 : 0);
    }
}
