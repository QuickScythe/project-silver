package com.quickscythe.titanium;

import com.quickscythe.titanium.game.Window;

public class Main {

    public static Window window;

    public static void main(String[] args){
        window = new Window();
    }

    public static Window getWindow(){
        return window;
    }
}
