package ua.kharkiv.sendey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * ����� �������� ��� ����������� ������ ��� ������ � ��
 */
public class DataBase {
  
    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";
  
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_F_NAME = "first_name";
    public static final String COLUMN_L_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
  
    private static final String DB_CREATE = 
        "create table " + DB_TABLE + "(" +
        COLUMN_ID + " integer primary key autoincrement, " +
        COLUMN_F_NAME + " text, " +
        COLUMN_L_NAME + " text, " +
        COLUMN_EMAIL + " text, " +  
        COLUMN_PHONE + " integer" + 
        ");";
  
    private final Context mCtx;
  
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
  
    public DataBase(Context ctx) {
        mCtx = ctx;
    }
  
    /* ��������� ����������� � �� */
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }
  
    /* ��������� ����������� � �� */
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }
  
    /* �������� ��� ������ �� ������� DB_TABLE */
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }
  
    /* �������� ������ COLUMN_ID �� ������� DB_TABLE */
    public Cursor getSingleData(String id) {
        String selection = COLUMN_ID + " = " + id;
        return mDB.query(DB_TABLE, null, selection, null, null, null, null);
    }
  
    /* ��������� ������ � ������� DB_TABLE */
    public void addRec(String firstName, String lastName, String email,
                       long phone) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_F_NAME, firstName);
        cv.put(COLUMN_L_NAME, lastName);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PHONE, phone);
        mDB.insert(DB_TABLE, null, cv);
    }
  
    /* ������� ������ �� ������� DB_TABLE */
    public void delRec(long id) {
    	String whereClause = COLUMN_ID + " = " + id;
        mDB.delete(DB_TABLE, whereClause, null);
    }
  
    /* ������� � ��������� �� */
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        /* ������� ��� ��������� �� */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}