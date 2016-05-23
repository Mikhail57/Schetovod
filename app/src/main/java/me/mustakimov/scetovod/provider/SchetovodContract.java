package me.mustakimov.scetovod.provider;

/**
 * Created by mikhail on 29/02/16.
 */
public class SchetovodContract {

    interface CategoryColumns {

        /** Unique string identifier of category */
        String ID = "id";
        /** Unique string title of category shown in UI */
        String TITLE = "title";
        /** String description of category */
        String DESCRIPTION = "description";
        /** Extra field (will be used in future updates) */
        String DELETED = "deleted";
    }

    interface PurchasesColumns {

        /** Category of purchase */
        String CATEGORY = "category";
        /** Title of current purchase. For example, "Bread". */
        String TITLE = "title";
        /** Some additional description of purchase */
        String DESCRIPTION = "description";
        /** Count of items of current purchase */
        String COUNT = "count";
        /** Price of the purchase */
        String PRICE = "price";
        /** Day when user took purchase */
        String DATE = "date";
        /** Extra field (will be used in future updates) */
        String DELETED = "deleted";
    }
}
