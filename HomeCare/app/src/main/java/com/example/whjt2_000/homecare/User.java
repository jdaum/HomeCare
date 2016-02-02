package com.example.whjt2_000.homecare;

import android.app.Application;

/**
 * Created by jeanette on 02.02.16.
 */
public class User extends Application{
    private String name = "Your Name";

    public void setName(String name){this.name = name;}
    public String getName(){
        return name;
    }
}
