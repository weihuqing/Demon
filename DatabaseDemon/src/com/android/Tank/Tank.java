package com.android.Tank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class Tank extends Activity
{
    
    private DBManager manager;
    
    private ListView listView;
    
    private Button add;
    
    private Button update;
    
    private Button delete;
    
    private Button query;
    
    private Button queryTheCursor;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.list_view);
        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        queryTheCursor = (Button) findViewById(R.id.query_the_cursor);
        manager = new DBManager(this);
        
        OnClickListener clickListener = new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (add == v)
                {
                    ArrayList<Person> persons = new ArrayList<Person>();
                    
                    Person person1 = new Person("Ella", 21, "lively girl");
                    Person person2 = new Person("Jenny", 22, "beautiful girl");
                    Person person3 = new Person("Jessica", 23, "sexy girl");
                    Person person4 = new Person("Kelly", 24, "hot baby");
                    Person person5 = new Person("Jane", 25, "a pretty woman");
                    
                    persons.add(person1);
                    persons.add(person2);
                    persons.add(person3);
                    persons.add(person4);
                    persons.add(person5);
                    
                    manager.add(persons);
                }
                if (update == v)
                {
                    Person person = new Person();
                    person.name = "Jane";
                    person.age = 30;
                    manager.updateAge(person);
                }
                if (delete == v)
                {
                    Person person = new Person();
                    person.age = 23;
                    manager.deleteOldPerson(person);
                }
                if (query == v)
                {
                    List<Person> persons = manager.query();
                    ArrayList<Map<String, String>> list =
                        new ArrayList<Map<String, String>>();
                    for (Person person : persons)
                    {
                        HashMap<String, String> map =
                            new HashMap<String, String>();
                        map.put("name", person.name);
                        map.put("info", person.age + " years old, "
                            + person.info);
                        list.add(map);
                    }
                    SimpleAdapter adapter =
                        new SimpleAdapter(Tank.this, list,
                            android.R.layout.simple_list_item_2, new String[] {
                                "name", "info"}, new int[] {android.R.id.text1,
                                android.R.id.text2});
                    listView.setAdapter(adapter);
                }
                if (queryTheCursor == v)
                {
                    Cursor c = manager.queryTheCursor();
                    startManagingCursor(c); //托付给activity根据自己的生命周期去管理Cursor的生命周期  
                    CursorWrapper cursorWrapper = new CursorWrapper(c)
                    {
                        @Override
                        public String getString(int columnIndex)
                        {
                            //将简介前加上年龄  
                            if (getColumnName(columnIndex).equals("info"))
                            {
                                int age = getInt(getColumnIndex("age"));
                                return age + " years old, "
                                    + super.getString(columnIndex);
                            }
                            return super.getString(columnIndex);
                        }
                    };
                    //确保查询结果中有"_id"列  
                    SimpleCursorAdapter adapter =
                        new SimpleCursorAdapter(Tank.this,
                            android.R.layout.simple_list_item_2, cursorWrapper,
                            new String[] {"name", "info"}, new int[] {
                                android.R.id.text1, android.R.id.text2});
                    ListView listView = (ListView) findViewById(R.id.list_view);
                    listView.setAdapter(adapter);
                }
            }
        };
        
        add.setOnClickListener(clickListener);
        update.setOnClickListener(clickListener);
        delete.setOnClickListener(clickListener);
        query.setOnClickListener(clickListener);
        queryTheCursor.setOnClickListener(clickListener);
        
    }
}
