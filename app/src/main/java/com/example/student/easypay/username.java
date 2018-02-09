package com.example.student.easypay;

/**
 * Created by dell on 2017/7/13.
 */

public class username {
    static String curr_username = "";

    static public void set(String str){
        curr_username = str;
    }

    static public String get(){
        return curr_username;
    }
}
