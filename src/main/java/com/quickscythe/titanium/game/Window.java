package com.quickscythe.titanium.game;

import com.quickscythe.titanium.Main;
import com.quickscythe.titanium.game.board.LevelScene;
import com.quickscythe.titanium.utils.GameUtils;
import com.quickscythe.titanium.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class Window extends JFrame {

    Screen screen;


    public Window() {
        screen = new Screen();
        add(screen);
        pack();
        setSize(1600, 900);
        setPreferredSize(new Dimension(16*10,9*10));
        setTitle("Project Titanium");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Resources.getImage("logo.png"));
        setVisible(true);


    }




    public Screen getScreen() {
        return screen;
    }

    public class Screen extends JPanel implements ActionListener, ComponentListener {

        int fps = 0;
        Timer timer;
        Heartbeat heartbeat;
        Camera camera;
        int tps = 0;

        double max_fps = 100;
        double max_tps = 50;
        private long last_fps_check = 0;
        private long last_tps_check = 0;


        public Screen() {
            setBackground(Color.BLACK);

            addKeyListener(new TAdapter());
            setFocusable(true);
            requestFocusInWindow();

            camera = new Camera(getWidth(), getHeight());


            timer = new Timer(5, this);
            timer.start();

            heartbeat = new Heartbeat(this);
            heartbeat.start();


        }




        @Override
        public void actionPerformed(ActionEvent e) {

//            camera.setSize(getWidth() - 1, getHeight() - 1);
        }

        protected void tick() {
            try {
                if (new Date().getTime() - last_tps_check >= ((1D / max_tps) * 1000D)) {
                    tps = (int) (1000D/(new Date().getTime() - last_tps_check));
                    last_tps_check = new Date().getTime();
//                    camera.getViewport().setBounds();
//                    camera.setSize((int) (screen.getWidth()), (int) (screen.getHeight()));
                    camera.update();
                }
            } catch (ArithmeticException ex) {
                tps = 999;
            }
            try {
                if (new Date().getTime() - last_fps_check >= ((1D / max_fps) * 1000D)) {
//                    System.out.println("Time since last frame: " + (new Date().getTime() - last_fps_check));
                    fps = (int) (1000D/(new Date().getTime() - last_fps_check));
                    last_fps_check = new Date().getTime();
                    paint();

                }

            } catch (ArithmeticException ex) {
                fps = 999;
            }

        }

        public void paint(){
            camera.draw(this);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);




//            last_fps_check = new Date().getTime();
        }

        public Camera getCamera() {
            return camera;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            int W = 16;
            int H = 9;
            Rectangle b = e.getComponent().getBounds();
            e.getComponent().setBounds(b.x,b.y,b.width,b.width*H/W);
//            e.getComponent().setBounds(getWidth(),getWidth()*16/9);
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }


        private class TAdapter extends KeyAdapter {
            int i = 0;



            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_F3) {
                    GameUtils.toggleDebug();
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                    camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX() - 1, (int) camera.getViewport().getLocation().getY());
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                    camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX(), (int) camera.getViewport().getLocation().getY() - 1);
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                    camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX() + 1, (int) camera.getViewport().getLocation().getY());
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                    camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX(), (int) camera.getViewport().getLocation().getY() + 1);
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
                    camera = new Camera(getWidth(), getHeight());
                }
                if (e.getKeyCode() == KeyEvent.VK_ADD) {
                    i=i+1;
                    ((LevelScene) Main.getWindow().getScreen().getCamera().current_board).player.animationController.setAnimation(i);
                }
                if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                    i=i-1;
                    ((LevelScene) Main.getWindow().getScreen().getCamera().current_board).player.animationController.setAnimation(i);
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                   camera = new Camera(100,100);


                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Main.getWindow().getScreen().getCamera().queueBoard(new LevelScene(1));
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    ((LevelScene) Main.getWindow().getScreen().getCamera().current_board).player.animationController.setAnimation(7);


                }

            }

        }

    }

    class Heartbeat extends Thread {

        private final Screen screen;

        public Heartbeat(Screen screen) {
            this.screen = screen;
        }

        public void run() {

            while (true) {
                screen.tick();

            }

        }

    }

}
