package com.quickscythe.titanium;

import com.quickscythe.titanium.game.scene.Scene;
import com.quickscythe.titanium.game.scene.SceneManager;

public class Main {

    public static Window window;

    public static void main(String[] args){

        SceneManager.init();
        window = new Window();
        window.init();
        Scene scene = SceneManager.getMenuScene();
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("-le") || args[0].equalsIgnoreCase("leveleditor")){
                scene = SceneManager.getLevelEditorScene(args.length >= 2 ? Integer.parseInt(args[1]) : 1);
            }
        }
        window.getScreen().queueScene(scene);

    }

    public static Window getWindow(){
        return window;
    }
}
