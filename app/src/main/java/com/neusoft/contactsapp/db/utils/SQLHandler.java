package com.neusoft.contactsapp.db.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.neusoft.contactsapp.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class SQLHandler extends SQLiteOpenHelper {
    //create class members of such information
    private static final int DB_VERSION=1;
    private static final String DB_NAME="CMS";
    private static final String TABLE_NAME_CONTACTS="Contacts";
    private static final String COL_ID="ID";
    private static final String COL_F_NAME="FIRST_NAME";
    private static final String COL_L_NAME="LAST_NAME";
    private static final String COL_MOB="PHONE_NO";
    public SQLHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        //null- cursor factory instance
    }
    //crete the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        //Android a column to be auto increment
        String query="CREATE TABLE "+TABLE_NAME_CONTACTS+"("+
                COL_ID+" INTEGER PRIMARY KEY,"+
                COL_F_NAME+" TEXT,"+
                COL_L_NAME+" TEXT,"+
                COL_MOB+" TEXT)";
        db.execSQL(query);
    }
    //Upgrading Databases
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table if it exists
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CONTACTS);
        //create table again
        onCreate(db);
    }
    //Add contact
    public void addContact(Contact contact){
        //get a reference to the database on which application is working on
        SQLiteDatabase db=this.getWritableDatabase();//db reference that can be used to write data
        //I can use insert method of SQLiteDatabase
        ContentValues vals=new ContentValues();
        //this will store the values for this record/row
        vals.put(COL_F_NAME,contact.getFirstName());
        vals.put(COL_L_NAME,contact.getLastName());
        vals.put(COL_MOB,contact.getPhoneNumber());
        //insert the row in this database
        db.insert(TABLE_NAME_CONTACTS,null,vals);
        //close the database connection
        db.close();
    }
    //1. Create a new Contact Object
    //Save in the database
    //Delete Contact
    //Delete all contacts
    //Show all contacts
    public List<Contact> getAllContacts(){
        List<Contact> contactList=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME_CONTACTS;
        //TRAVERSE THIS INFORMATION
        //CURSOR CAN HELP
        Cursor cursor=db.rawQuery(query,null);//No Selection arguments
        //loop through all the rows in the table
        if(cursor.moveToFirst()){
            do{
                Contact con=new Contact();
                con.setID(cursor.getInt(0));
                con.setFirstName(cursor.getString(1));
                con.setLastName(cursor.getString(2));
                con.setPhoneNumber(cursor.getString(3));
                contactList.add(con);
            }while(cursor.moveToNext());//if the next contact exists
        }
        db.close();//Close the database
        return contactList;//This will have all the contacts in the table
    }
    //Update the contact
    public void updateContact(Contact con){
        ContentValues values=new ContentValues();
        values.put(COL_F_NAME,con.getFirstName());
        values.put(COL_L_NAME,con.getLastName());
        values.put(COL_MOB,con.getPhoneNumber());
        //update the contact in the database
        SQLiteDatabase db=getWritableDatabase();
        db.update(TABLE_NAME_CONTACTS,values,COL_ID+"="+con.getID(),null);
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db=getWritableDatabase();
        //delete the contact from database
        //delete from Contacts where id=contact.getID();
        db.delete(TABLE_NAME_CONTACTS,COL_ID+"="+contact.getID(),null);
        db.close();
    }
}
