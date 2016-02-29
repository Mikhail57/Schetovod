package me.mustakimov.scetovod.provider;

/**
 * Created by mikhail on 29/02/16.
 */
public class SchetovodContract {

    interface CategoriesColumns {

        /** Unique string identifier of category */
        String CATEGORY_ID = "category_title";
        /** Unique string title of category shown in UI */
        String CATEGORY_TITLE = "category_title";
        /** String description of category */
        String CATEGORY_DESCRIPTION = "category_description";
        /** Extra field (will be used in future updates) */
        String CATEGORY_DELETED = "category_deleted";
    }

    interface PurchasesColumns {

        /** Category of purchase */
        String PURCHASE_CATEGORY = "purchase_category";
        /** Title of current purchase. For example, "Bread". */
        String PURCHASE_TITLE = "purchase_title";
        /** Count of items of current purchase */
        String PURCHASE_COUNT = "purchase_count";
        /** Price of the purchase */
        String PURCHASE_PRICE = "purchase_price";
        /** Day when user took purchase */
        String PURCHASE_DATE = "purchase_date";
    }
}
