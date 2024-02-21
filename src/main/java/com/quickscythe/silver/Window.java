package com.quickscythe.silver;

import com.quickscythe.silver.utils.Resources;

import javax.swing.*;

public class Window extends JFrame {

    private final Screen SCREEN;


    public Window() {


        SCREEN = new Screen();





    }

    public void init() {
        setIconImage(Resources.getImage("logo.png"));


        getContentPane().add(SCREEN);
        SCREEN.init();
//        pack();
        setSize(1280, 720);
//        setPreferredSize(new Dimension(16 * 100, 9 * 100));
        setTitle("Project Silver");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);


    }


    public Screen getScreen() {
        return SCREEN;
    }







}
