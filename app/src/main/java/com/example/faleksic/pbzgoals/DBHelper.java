package com.example.faleksic.pbzgoals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pbz_goals.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public static final String SAVING_TABLE_NAME = "saving";
    public static final String COLUMN_ID = "_id";
    public static final String SAVING_COLUMN_TITLE = "title";
    public static final String SAVING_COLUMN_DATE_END = "date_end";
    public static final String SAVING_COLUMN_AMOUNT = "amount";
    public static final String SAVING_COLUMN_FREQUENCY = "frequency";
    public static final String SAVING_COLUMN_CATEGORY = "category";
    public static final String SAVING_COLUMN_PAID = "paid";
    public static final String SAVING_COLUMN_ACTIVE = "active";

    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_NAME = "name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CATEGORY_TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CATEGORY_COLUMN_NAME + " TEXT UNIQUE)"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + SAVING_TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                SAVING_COLUMN_TITLE + " TEXT, " +
                SAVING_COLUMN_DATE_END + " TEXT, " +
                SAVING_COLUMN_AMOUNT + " DOUBLE, " +
                SAVING_COLUMN_FREQUENCY + " INTEGER, " +
                SAVING_COLUMN_CATEGORY + " INTEGER, " +
                SAVING_COLUMN_PAID + " DOUBLE, " +
                SAVING_COLUMN_ACTIVE + " INTEGER, " +
                "FOREIGN KEY (" + SAVING_COLUMN_CATEGORY + ") REFERENCES " +
                CATEGORY_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE)"
        );
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, context.getResources().getString(R.string.uncategorised));
        sqLiteDatabase.insert(CATEGORY_TABLE_NAME, null, contentValues);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SAVING_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertCategory(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, name);
        db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertSaving(String title, String dateEnd, double amount, int frequency, int category, double paid, boolean active) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SAVING_COLUMN_TITLE, title);
        contentValues.put(SAVING_COLUMN_DATE_END, dateEnd);
        contentValues.put(SAVING_COLUMN_AMOUNT, amount);
        contentValues.put(SAVING_COLUMN_FREQUENCY, frequency);
        contentValues.put(SAVING_COLUMN_CATEGORY, category);
        contentValues.put(SAVING_COLUMN_PAID, paid);
        if(active) {
            contentValues.put(SAVING_COLUMN_ACTIVE, 1);
        } else {
            contentValues.put(SAVING_COLUMN_ACTIVE, 0);
        }
        db.insert(SAVING_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateCategory(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, name);
        db.update(CATEGORY_TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateSaving(int id, String title, Date dateEnd, double amount, int frequency, int category, String description, double paid, boolean active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SAVING_COLUMN_TITLE, title);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        contentValues.put(SAVING_COLUMN_DATE_END, dateFormat.format(dateEnd));
        contentValues.put(SAVING_COLUMN_AMOUNT, amount);
        contentValues.put(SAVING_COLUMN_FREQUENCY, frequency);
        contentValues.put(SAVING_COLUMN_CATEGORY, category);
        contentValues.put(SAVING_COLUMN_PAID, paid);
        contentValues.put(SAVING_COLUMN_ACTIVE, active);
        db.update(SAVING_TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Cursor getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public int getCategoryId(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + CATEGORY_TABLE_NAME + " WHERE " +
                CATEGORY_COLUMN_NAME + "=?", new String[]{name});
        int id = -1;
        while (res.moveToNext()) {
            id = res.getInt(0);
        }
        res.close();

        return id;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + COLUMN_ID + ", " + CATEGORY_COLUMN_NAME + ", (SELECT COUNT(*) FROM " + SAVING_TABLE_NAME
                + " WHERE " + SAVING_COLUMN_CATEGORY + "=" + CATEGORY_TABLE_NAME + "." + COLUMN_ID + ") AS count" +
                " FROM " + CATEGORY_TABLE_NAME, null);
        return res;
    }

    public int countAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + CATEGORY_TABLE_NAME, null);
        int count = 0;
        while (res.moveToNext()) {
            count = res.getInt(0);
        }
        res.close();

        return count;
    }

    public Cursor getSaving(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT s." + COLUMN_ID + ", s." + SAVING_COLUMN_TITLE +
                ", s." + SAVING_COLUMN_DATE_END + ", s." + SAVING_COLUMN_AMOUNT +
                ", s." + SAVING_COLUMN_FREQUENCY + ", s." + SAVING_COLUMN_CATEGORY +", s." + SAVING_COLUMN_PAID +
                ", s." + SAVING_COLUMN_ACTIVE +
                ", c." + CATEGORY_COLUMN_NAME + " FROM " + SAVING_TABLE_NAME +
                " s JOIN " + CATEGORY_TABLE_NAME + " c ON s." + SAVING_COLUMN_CATEGORY +
                "=c." + COLUMN_ID + " WHERE s." + COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllSavings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT s." + COLUMN_ID + ", s." + SAVING_COLUMN_TITLE +
                ", s." + SAVING_COLUMN_DATE_END + ", s." + SAVING_COLUMN_AMOUNT +
                ", s." + SAVING_COLUMN_FREQUENCY + ", s." + SAVING_COLUMN_CATEGORY +", s." + SAVING_COLUMN_PAID +
                ", s." + SAVING_COLUMN_PAID +
                ", c." + CATEGORY_COLUMN_NAME + " FROM " + SAVING_TABLE_NAME +
                " s JOIN " + CATEGORY_TABLE_NAME + " c ON s." + SAVING_COLUMN_CATEGORY +
                "=c." + COLUMN_ID, null);
            return res;
    }

    public Cursor getAllNotesWithCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT s." + COLUMN_ID + ", s." + SAVING_COLUMN_TITLE +
                ", s." + SAVING_COLUMN_DATE_END + ", s." + SAVING_COLUMN_AMOUNT +
                ", s." + SAVING_COLUMN_FREQUENCY + ", s." + SAVING_COLUMN_CATEGORY + ", s." + SAVING_COLUMN_PAID +
                ", s." + SAVING_COLUMN_PAID +
                ", c." + CATEGORY_COLUMN_NAME + " FROM " + SAVING_TABLE_NAME +
                " s JOIN " + CATEGORY_TABLE_NAME + " c ON s." + SAVING_COLUMN_CATEGORY +
                "=c." + COLUMN_ID + " WHERE s." + SAVING_COLUMN_CATEGORY + "=?", new String[]{Integer.toString(id)}, null);
        return res;
    }

    public Integer deleteCategory(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CATEGORY_TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deleteNote(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SAVING_TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }

}
