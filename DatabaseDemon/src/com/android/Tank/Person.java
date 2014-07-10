package com.android.Tank;

/**
 * @author wWX191016
 * 将多个参数封装为一个类
 */
public class Person
{
    public int _id;
    
    public String name;
    
    public int age;
    
    public String info;
    
    public Person()
    {
        
    }
    
    public Person(String name, int age, String info)
    {
        this.name = name;
        this.age = age;
        this.info = info;
    }
}
