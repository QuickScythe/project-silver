package com.quickscythe.silver.gui;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {

    private static final Map<String, Gui> GUI_MAP = new HashMap<>();

    public static Gui createGui(String id){
        Gui gui = new Gui(id);
        GUI_MAP.put(id,gui);
        return gui;
    }
}
