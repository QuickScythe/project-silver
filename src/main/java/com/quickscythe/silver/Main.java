package com.quickscythe.silver;

import com.quickscythe.silver.game.scene.Scene;
import com.quickscythe.silver.game.scene.SceneManager;
import com.quickscythe.silver.gdx.Client;

public class Main {

    public static Window window;

    public static void main(String[] args){

        new Client();

//        SceneManager.init();
//        window = new Window();
//        window.init();
//        Scene scene = SceneManager.getMenuScene();
//        if(args.length > 0){
//            if(args[0].equalsIgnoreCase("-le") || args[0].equalsIgnoreCase("leveleditor")){
//                scene = SceneManager.getLevelEditorScene(args.length >= 2 ? Integer.parseInt(args[1]) : 1);
//            }
//        }
//        window.getScreen().queueScene(scene);

    }

    public static Window getWindow(){
        return window;
    }
}
