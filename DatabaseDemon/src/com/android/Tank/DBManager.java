package com.android.Tank;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager
{
    
    private DBHelper helper;
    
    private SQLiteDatabase db;
    
    public DBManager(Context context)
    {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    
    /**
     * add persons
     * @param persons
     */
    public void add(List<Person> persons)
    {
        db.beginTransaction();
        try
        {
            for (Person person : persons)
            {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)",
                    new Object[] {person.name, person.age, person.info});
            }
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }
    }
    
    /**
     * update person`s age
     * @param person
     */
    public void updateAge(Person person)
    {
        ContentValues values = new ContentValues();
        values.put("age", person.age);
        db.update("person", values, "name = ?", new String[] {person.name});
    }
    
    /**
     * delete old person
     * @param person
     */
    public void deleteOldPerson(Person person)
    {
        db.delete("person",
            "age >= ?",
            new String[] {String.valueOf(person.age)});
    }
    
    public List<Person> query()
    {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor cursor = queryTheCursor();
        while (cursor.moveToNext())
        {
            Person person = new Person();
            person._id = cursor.getInt(cursor.getColumnIndex("_id"));
            person.name = cursor.getString(cursor.getColumnIndex("name"));
            person.age = cursor.getInt(cursor.getColumnIndex("age"));
            person.info = cursor.getString(cursor.getColumnIndex("info"));
            persons.add(person);
        }
        cursor.close();
        return persons;
    }
    
    /**
     * query all persons, return cousor;
     * @return
     */
    public Cursor queryTheCursor()
    {
        Cursor cursor = db.rawQuery("SELECT * FROM person", null);
        return cursor;
    }
    
    /**
     * 
     */
    public void closeDB()
    {
        db.close();
    }
}
