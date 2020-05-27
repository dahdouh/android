package com.example.tp2exercice5;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper implements DatabaseConstants {
    private Resources resources;
    private SQLiteDatabase database;
    public static final int DB_VERSION = 2;

    //Constreur de de la classe SQKiteHelper permettant d'établir la connexion avec la base de données SQLite
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        resources = context.getResources();
        openDatabase();
        onCreate(this.database);
    }

    //methode permet d'ouvrir la base de données SQLite
    public void openDatabase() {
        try {
            this.database = getWritableDatabase();
        } catch (final SQLException se) {
            se.printStackTrace();
        }
    }

    //methode de création de table de contacts
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACT);
        db.execSQL("CREATE TABLE " + TABLE_CONTACT + "(" + COL_CONTACT_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT" +", "+ COL_CONTACT_NOM + " VARCHAR(100) NOT NULL, "+
                COL_CONTACT_PRENOM + " VARCHAR(100) NOT NULL, "+
                COL_CONTACT_TEL + " VARCHAR(12) NOT NULL);");
    }

    //inserer les contacts dans la base de données SQLite
    public static void insertContact(SQLiteDatabase db, String nom, String prenom, String tel) {
        db.execSQL("INSERT INTO " + TABLE_CONTACT +" (" + COL_CONTACT_NOM + ","+ COL_CONTACT_PRENOM +", "+ COL_CONTACT_TEL +") " +
                "VALUES('" + nom + "', '"+ prenom +"', '"+ tel +"')");
    }

    // mise à jour de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACT);

    }

    // recuperer la liste des contacts
    public List<String> getContactsFromDb() {
        final String sqlQuery = "SELECT " + COL_CONTACT_ID + ", " + COL_CONTACT_NOM + "," + COL_CONTACT_PRENOM + ", "+ COL_CONTACT_TEL
                + " FROM " + TABLE_CONTACT + " ORDER BY "
                + COL_CONTACT_NOM;

        final Cursor cursor = this.database.rawQuery(sqlQuery, null);
        List<String> contactList = new ArrayList<String>();
        while (cursor.moveToNext()) {

            contactList.add(cursor.getString(cursor.getColumnIndex(COL_CONTACT_NOM))
                    + "_" + cursor.getString(cursor.getColumnIndex(COL_CONTACT_PRENOM))+ "_"
                    + cursor.getString(cursor.getColumnIndex(COL_CONTACT_TEL))
            );
        }
        cursor.close();
        return contactList;
    }

    public SQLiteDatabase getDatabase(){
        return this.database;
    }
}