package me.mustakimov.scetovod;

import android.content.Context;

import java.util.List;

import me.mustakimov.scetovod.model.CategoryItem;
import me.mustakimov.scetovod.model.PurchaseItem;
import me.mustakimov.scetovod.provider.SchetovodDatabase;

/**
 * Created by mikhail on 22/05/16.
 */
public class CommonVariables {
    private static List<PurchaseItem> purchases;
    private static List<CategoryItem> categories;


    public List<PurchaseItem> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseItem> purchases) {
        CommonVariables.purchases = purchases;
    }

    public List<CategoryItem> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryItem> categories) {
        CommonVariables.categories = categories;
    }


    public static void initializeVariables(Context context) {
        categories = SchetovodDatabase.getCategories(context);
        purchases = SchetovodDatabase.getPurchases(context);
    }

}
