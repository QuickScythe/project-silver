package com.quickscythe.titanium.game.controllers;

public interface EntityController {

    public boolean requestingUp();
    public boolean requestingDown();
    public boolean requestingLeft();
    public boolean requestingRight();
    public double getX();
    public double getY();

    public boolean requestingPause();
    public boolean requestingMenu();
}
