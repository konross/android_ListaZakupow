package konross.listazakupow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Konrad on 2016-03-08.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public static final int database_version=1;

    public String CREATE_TABLE_OF_LISTS="CREATE TABLE "+ TableData.Tables.LISTS_TABLE+" ( "
            + TableData.Tables.LIST_ID + " integer primary key autoincrement, "
            + TableData.Tables.LIST_NAME+" TEXT not null);";
    public  String CREATE_TABLE_OF_PRODUCTS="CREATE TABLE "+ TableData.Tables.PRODUCTS_TABLE_NAME + " ( "+
            TableData.Tables.PRODUCT_ID + " integer primary key autoincrement, "+
            TableData.Tables.PRODUCT_NAME + " TEXT not null , "+
            TableData.Tables.PRODUCT_VALUE + " TEXT not null , "+
            TableData.Tables.PRODUCT_LIST_ID+ " integer,"+
            " FOREIGN KEY ("+ TableData.Tables.PRODUCT_LIST_ID+") REFERENCES "+
            TableData.Tables.DATABASE_NAME+"("+ TableData.Tables.LIST_ID+"));";

    public DatabaseManager(Context context) {
        super(context, TableData.Tables.DATABASE_NAME,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OF_LISTS);
        db.execSQL(CREATE_TABLE_OF_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInfoList(DatabaseManager dbm, String name){
        SQLiteDatabase sq=dbm.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableData.Tables.LIST_NAME, name);
        sq.insert(TableData.Tables.LISTS_TABLE, null, cv);
    }

    public Cursor getInfoList(DatabaseManager dbm){
        SQLiteDatabase sq=dbm.getReadableDatabase();
        String[] rows = {TableData.Tables.LIST_ID,TableData.Tables.LIST_NAME};
        Cursor cr=sq.query(TableData.Tables.LISTS_TABLE,rows,null,null,null,null,null);
        return cr;
    }

    public void putInfoProduct(DatabaseManager dbm, String name, String value, int id_list){
        SQLiteDatabase sq=dbm.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableData.Tables.PRODUCT_NAME,name);
        cv.put(TableData.Tables.PRODUCT_VALUE,value);
        cv.put(TableData.Tables.PRODUCT_LIST_ID,id_list);
        sq.insert(TableData.Tables.PRODUCTS_TABLE_NAME,null,cv);
    }

    public Cursor getInfoProduct(DatabaseManager dbm){
        SQLiteDatabase sq=dbm.getReadableDatabase();
        String[] rows = {TableData.Tables.PRODUCT_ID,TableData.Tables.PRODUCT_NAME, TableData.Tables.PRODUCT_VALUE, TableData.Tables.PRODUCT_LIST_ID};
        Cursor cr=sq.query(TableData.Tables.PRODUCTS_TABLE_NAME,rows,null,null,null,null,null);
        return cr;
    }

    public void updateInfoProduct(DatabaseManager dbm, String name, String value, int id_product, int id_list){
        SQLiteDatabase sq=dbm.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableData.Tables.PRODUCT_NAME,id_product);
        cv.put(TableData.Tables.PRODUCT_NAME,name);
        cv.put(TableData.Tables.PRODUCT_VALUE,value);
        cv.put(TableData.Tables.PRODUCT_LIST_ID,id_list);
        //String war=id_product.toString();
        sq.update(TableData.Tables.PRODUCTS_TABLE_NAME, cv, "prok1_id = "+id_product,null/*new String[]{}*/);
    }
}
