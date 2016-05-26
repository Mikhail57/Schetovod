package me.mustakimov.scetovod;

import android.content.Context;
import android.content.res.Resources;

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


    public static List<PurchaseItem> getPurchases() {
        return purchases;
    }

    public static void setPurchases(List<PurchaseItem> purchases) {
        CommonVariables.purchases = purchases;
    }

    public static List<CategoryItem> getCategories() {
        return categories;
    }

    public static void setCategories(List<CategoryItem> categories) {
        CommonVariables.categories = categories;
    }


    public static void initializeVariables(Context context) {
        categories = SchetovodDatabase.getCategories(context);
        purchases = SchetovodDatabase.getPurchases(context);

        if (categories.size() == 0) {
            initializeCategories(context);
        }
    }

    private static void initializeCategories(Context context) {
        Resources res = context.getResources();
        String[] categoriesArray = res.getStringArray(R.array.categories);

        for (String title : categoriesArray) {
            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setTitle(title);
            categoryItem.setDeleted(false);
            categoryItem.setDescription("");

            categories.add(categoryItem);
        }
    }

    public static boolean addCategory(Context context, CategoryItem category) {
        categories.add(category);
        SchetovodDatabase.createCategory(category, context);

        return true;
    }

    public static boolean addPurchase(Context context, PurchaseItem purchase) {
        purchases.add(purchase);
        SchetovodDatabase.addPurchase(purchase, context);

        return true;
    }

}
