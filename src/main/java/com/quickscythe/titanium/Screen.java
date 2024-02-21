package com.quickscythe.titanium;

import com.quickscythe.titanium.game.Camera;
import com.quickscythe.titanium.game.scene.Scene;
import com.quickscythe.titanium.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Screen extends JPanel {

    //    private final Timer TIMER;
    private final Heartbeat HEARTBEAT;
    private final Camera CAMERA;
    int fps = 0;
    int tps = 0;

    double max_fps = 100;
    double max_tps = 50;
    Scene current_scene;
    private long last_fps_check = 0;
    private long last_tps_check = 0;
    private Scene next_scene = null;


    public Screen() {
        setBackground(Color.BLACK);
        Screen.this.addKeyListener(GameUtils.getInputManager());
        Screen.this.addMouseListener(GameUtils.getInputManager());
        Screen.this.addMouseMotionListener(GameUtils.getInputManager());
        Screen.this.addMouseWheelListener(GameUtils.getInputManager());

        setFocusable(true);
        requestFocusInWindow();
        HEARTBEAT = new Heartbeat(this);
//        TIMER = new Timer(5, this);

        CAMERA = new Camera(1920/2, 1080/2);

    }

    public void init() {

        CAMERA.init();


        HEARTBEAT.start();
    }


    public void queueScene(Scene scene) {
        next_scene = scene;
    }

    public Scene getScene() {
        return current_scene;
    }

    protected boolean tick() {
        try {
            if (new Date().getTime() - last_tps_check >= ((1D / max_tps) * 1000D)) {
                tps = (int) (1000D / (new Date().getTime() - last_tps_check));
                last_tps_check = new Date().getTime();

                if (current_scene != null) CAMERA.update(current_scene);

                if (next_scene != null) {

                    current_scene = next_scene;
                    next_scene = null;
                }
            }
        } catch (ArithmeticException ex) {
            tps = (int) max_tps;
        }
        try {
            if (new Date().getTime() - last_fps_check >= ((1D / max_fps) * 1000D)) {
                fps = (int) (1000D / (new Date().getTime() - last_fps_check));
                last_fps_check = new Date().getTime();
                if (current_scene != null) {
                    paint();
                }

            }

        } catch (ArithmeticException ex) {
            fps = (int) max_fps;
        }

        return true;

    }

    public void paint() {
        CAMERA.draw(this);
    }

    public Camera getCAMERA() {
        return CAMERA;
    }

//    @Override
//    public void componentResized(ComponentEvent e) {
//        int W = 16;
//        int H = 9;
//        Rectangle b = e.getComponent().getBounds();
//        e.getComponent().setBounds(b.x, b.y, b.width, b.width * H / W);
////            e.getComponent().setBounds(getWidth(),getWidth()*16/9);
//    }

//    @Override
//    public void componentMoved(ComponentEvent e) {
//
//    }
//
//    @Override
//    public void componentShown(ComponentEvent e) {
//
//    }
//
//    @Override
//    public void componentHidden(ComponentEvent e) {
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }

    public int getTPS() {
        return tps;
    }

    public int getFPS() {
        return fps;
    }


    class Heartbeat extends Thread {

        private final Screen screen;

        public Heartbeat(Screen screen) {
            this.screen = screen;
        }

        public void run() {


            boolean run = true;
            while (run) {
                run = screen.tick();


            }

        }

    }


}
