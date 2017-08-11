package com.example.segundoauqui.multipane;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by segundoauqui on 8/10/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDatabase";

    public static final String TABLE_NAME = "Contacts";
    public static final String CONTACT_ID = "ID";
    public static final String CONTACT_NAME = "Name";
    public static final String CONTACT_ORIGIN = "Origin";
    public static final String CONTACT_INSTRUMENT = "Instruments";
    public static final String CONTACT_PHOTO = "photo";




    private static final String TAG = "savedata";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void delete(String id){

        SQLiteDatabase database = this.getWritableDatabase();

        String DELETE_ELEMENT = "DELETE FROM  " + TABLE_NAME + " WHERE " + CONTACT_ID + " = ? ";
        String [] parame = new String[]{id};



        Log.d(TAG, "Delete: "+DELETE_ELEMENT);

        database.execSQL(DELETE_ELEMENT, parame);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +

                CONTACT_ID + " Integer Primary Key Autoincrement, " +
                CONTACT_NAME + " TEXT, " +
                CONTACT_ORIGIN + " TEXT, " +
                CONTACT_INSTRUMENT + " TEXT, " +
                CONTACT_PHOTO + " BLOB "+
                ")";
        Log.d(TAG, "onCreate: "+CREATE_TABLE);

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.d(TAG, "onUpgrade: ");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }




    public void saveNewContact(MyContacs contact){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME, contact.getEtName());
        contentValues.put(CONTACT_ORIGIN, contact.getEtOrigin());
        contentValues.put(CONTACT_PHOTO, contact.getB());
        contentValues.put(CONTACT_INSTRUMENT, contact.getEtInstrument());

        database.insert(TABLE_NAME,null,contentValues);

        Log.d(TAG, "saveNewContact: " + contact.getEtName() + " " + contact.getEtOrigin() + " " + contact.getEtInstrument() + " "
                 + " " + contact.getB());
    }




    public ArrayList<MyContacs> getContacs(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "";
        String [] parame = null;


        if(id.equals("-1")) {
            query = "SELECT * FROM " + TABLE_NAME;
        }
        else {
            query = "SELECT * FROM " + TABLE_NAME + " Where " + CONTACT_ID + " = ? " ;
            parame = new String[]{id};
        }
        Log.d(TAG, "getContacs: " + query+ " " + id);
        //Cursor cursor = database.rawQuery(query, new String[]{CONTACT_NAME,"Jose"});
        Cursor cursor = database.rawQuery(query, parame);
        ArrayList<MyContacs> contacts = new ArrayList();
        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "getContacts: Name:" + cursor.getString(0) + ", Origin: "+ cursor.getString(1));
                contacts.add(new MyContacs(cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getBlob(4),cursor.getInt(0)));
            }while(cursor.moveToNext());
        }
        else{
            Log.d(TAG, "getContacts: empty");
        }
        return contacts;
    }

}
