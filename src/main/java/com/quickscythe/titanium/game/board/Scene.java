package com.quickscythe.titanium.game.board;

import com.quickscythe.titanium.game.Camera;

import java.awt.*;

public interface Scene {

    public void draw(Camera camera, Graphics g);

    public void update(Camera camera);

}
