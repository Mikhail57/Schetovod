package me.mustakimov.scetovod.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import me.mustakimov.scetovod.model.CategoryItem;
import me.mustakimov.scetovod.model.PurchaseItem;
import me.mustakimov.scetovod.provider.SchetovodContract.CategoryColumns;
import me.mustakimov.scetovod.provider.SchetovodContract.PurchasesColumns;

import static me.mustakimov.scetovod.util.LogUtils.makeLogTag;

/**
 * Created by mikhail on 29/02/16.
 */
public class SchetovodDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(SchetovodDatabase.class);

    private static final String DATABASE_NAME = "schetovod.db";

    private static final int VER_2016_ALPHA_A = 1; //alpha app version 0.0.1
    private static final int CUR_DATABASE_VERSION = VER_2016_ALPHA_A;

    private static Context mContext;
    private static SchetovodDatabase mInstance;

    public static SchetovodDatabase getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SchetovodDatabase(context);
        return mInstance;
    }

    interface Tables {
        String CATEGORIES = "categories";
        String PURCHASES = "purchases";
    }

    public SchetovodDatabase(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.CATEGORIES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CategoryColumns.ID + " LONG NOT NULL,"
                + CategoryColumns.TITLE + " TEXT NOT NULL,"
                + CategoryColumns.DESCRIPTION + " TEXT NOT NULL,"
                + CategoryColumns.DELETED + " INTEGER NOT NULL,"
                + "UNIQUE (" + CategoryColumns.ID + ") ON CONFLICT REPLACE)");
        db.execSQL("CREATE TABLE " + Tables.PURCHASES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PurchasesColumns.TITLE + " TEXT NOT NULL,"
                + PurchasesColumns.DESCRIPTION + " TEXT NOT NULL,"
                + PurchasesColumns.CATEGORY + " LONG NOT NULL,"
                + PurchasesColumns.COUNT + " INTEGER NOT NULL,"
                + PurchasesColumns.PRICE + " INTEGER NOT NULL,"
                + PurchasesColumns.DELETED + " INTEGER NOT NULL,"
                + PurchasesColumns.DATE + " INTEGER NOT NULL)");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static long createCategory(CategoryItem category, Context context) {
        SchetovodDatabase database = SchetovodDatabase.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryColumns.TITLE, category.getTitle());
        values.put(CategoryColumns.DELETED, category.isDeleted());
        values.put(CategoryColumns.DESCRIPTION, category.getDescription());
        long insertId = db.insert(Tables.CATEGORIES, null, values);

        db.close();
        return insertId;
    }

    public long createCategory(CategoryItem category) {
        return createCategory(category, mContext);
    }

    /**
     * Method for adding purchase into DB
     *
     * @param purchase - Purchase made by user
     * @param context  - Context of the Activity (needed by SQLiteDatabase)
     * @return id of purchase. On conflict or error returns -1.
     */
    public static long addPurchase(PurchaseItem purchase, Context context) {
        SchetovodDatabase database = SchetovodDatabase.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PurchasesColumns.CATEGORY, purchase.getCategoryId());
        values.put(PurchasesColumns.COUNT, purchase.getCount());
        values.put(PurchasesColumns.DATE, purchase.getDate());
        values.put(PurchasesColumns.DELETED, purchase.isDeleted());
        values.put(PurchasesColumns.DESCRIPTION, purchase.getDescription());
        values.put(PurchasesColumns.PRICE, purchase.getPrice());
        values.put(PurchasesColumns.TITLE, purchase.getTitle());
        long insertId = db.insert(Tables.PURCHASES, null, values);

        db.close();
        return insertId;
    }

    public long addPurchase(PurchaseItem purchase) {
        return addPurchase(purchase, mContext);
    }

    public static List<CategoryItem> getCategories(Context context) {
        SchetovodDatabase database = SchetovodDatabase.getInstance(context);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(Tables.CATEGORIES, new String[]{
                        CategoryColumns.ID, CategoryColumns.TITLE, CategoryColumns.DELETED,
                        CategoryColumns.DESCRIPTION},
                null, null, null, null, null);

        List<CategoryItem> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                categories.add(cursorToCategory(cursor));
            }
        }

        return categories;
    }

    public static List<PurchaseItem> getPurchases(Context context) {
        List<PurchaseItem> purchases = new ArrayList<>();
        SchetovodDatabase database = SchetovodDatabase.getInstance(context);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(Tables.PURCHASES, new String[]{
                        PurchasesColumns.TITLE, PurchasesColumns.CATEGORY, PurchasesColumns.COUNT,
                        PurchasesColumns.DATE, PurchasesColumns.PRICE, PurchasesColumns.DELETED,
                        PurchasesColumns.DESCRIPTION},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                purchases.add(cursorToPurchase(cursor));
            }
        }

        return purchases;
    }

    private static CategoryItem cursorToCategory(Cursor cursor) {
        CategoryItem category = new CategoryItem();

        int idColumn = cursor.getColumnIndex(CategoryColumns.ID);
        int deletedColumn = cursor.getColumnIndex(CategoryColumns.DELETED);
        int titleColumn = cursor.getColumnIndex(CategoryColumns.TITLE);
        int descriptionColumn = cursor.getColumnIndex(CategoryColumns.DESCRIPTION);

        category.setId(cursor.getInt(idColumn));
        category.setDeleted(cursor.getInt(deletedColumn));
        category.setTitle(cursor.getString(titleColumn));
        category.setDescription(cursor.getString(descriptionColumn));

        return category;
    }

    private static PurchaseItem cursorToPurchase(Cursor cursor) {
        PurchaseItem purchase = new PurchaseItem();

        int titleColumn = cursor.getColumnIndex(PurchasesColumns.TITLE);
        int categoryColumn = cursor.getColumnIndex(PurchasesColumns.CATEGORY);
        int countColumn = cursor.getColumnIndex(PurchasesColumns.COUNT);
        int dateColumn = cursor.getColumnIndex(PurchasesColumns.DATE);
        int priceColumn = cursor.getColumnIndex(PurchasesColumns.PRICE);
        int deletedColumn = cursor.getColumnIndex(PurchasesColumns.DELETED);
        int descriptionColumn = cursor.getColumnIndex(PurchasesColumns.DESCRIPTION);

        purchase.setDeleted(cursor.getInt(deletedColumn));
        purchase.setDescription(cursor.getString(descriptionColumn));
        purchase.setTitle(cursor.getString(titleColumn));
        purchase.setCategoryId(cursor.getLong(categoryColumn));
        purchase.setCount(cursor.getDouble(countColumn));
        purchase.setDate(cursor.getLong(dateColumn));
        purchase.setPrice(cursor.getDouble(priceColumn));

        return purchase;
    }
}
