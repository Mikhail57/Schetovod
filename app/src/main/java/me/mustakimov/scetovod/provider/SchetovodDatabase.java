package me.mustakimov.scetovod.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import me.mustakimov.scetovod.provider.SchetovodContract.*;
import static me.mustakimov.scetovod.util.LogUtils.*;

/**
 * Created by mikhail on 29/02/16.
 */
public class SchetovodDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(SchetovodDatabase.class);

    private static final String DATABASE_NAME = "schetovod.db";

    private static final int VER_2016_ALPHA_A = 1; //alpha app version 0.0.1
    private static final int CUR_DATABASE_VERSION = VER_2016_ALPHA_A;

    private final Context mContext;

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
                + CategoriesColumns.CATEGORY_ID + " TEXT NOT NULL,"
                + CategoriesColumns.CATEGORY_TITLE + " TEXT NOT NULL,"
                + CategoriesColumns.CATEGORY_DESCRIPTION + " TEXT NOT NULL,"
                + CategoriesColumns.CATEGORY_DELETED + " INTEGER NOT NULL,"
                + "UNIQUE (" + CategoriesColumns.CATEGORY_ID + ") ON CONFLICT REPLACE)");
        db.execSQL("CREATE TABLE " + Tables.PURCHASES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PurchasesColumns.PURCHASE_TITLE + " TEXT NOT NULL,"
                + PurchasesColumns.PURCHASE_CATEGORY + " TEXT NOT NULL,"
                + PurchasesColumns.PURCHASE_COUNT + " INTEGER NOT NULL,"
                + PurchasesColumns.PURCHASE_PRICE + " INTEGER NOT NULL,"
                + PurchasesColumns.PURCHASE_DATE + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
