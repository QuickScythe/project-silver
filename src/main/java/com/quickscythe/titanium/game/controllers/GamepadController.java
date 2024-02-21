package com.quickscythe.titanium.game.controllers;

import com.studiohartman.jamepad.ControllerState;
import com.quickscythe.titanium.utils.GameUtils;

public class GamepadController implements EntityController {

    int id;

    public GamepadController(int controllerId){
        this.id = controllerId;
    }

    @Override
    public boolean requestingUp() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.leftStickY > 0.15 || state.dpadUp;
    }

    @Override
    public boolean requestingDown() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.leftStickY < -0.15 || state.dpadDown;
    }

    @Override
    public boolean requestingLeft() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.leftStickX < -0.15 || state.dpadLeft;
    }

    @Override
    public boolean requestingRight() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.leftStickX > 0.15 || state.dpadRight;
    }

    @Override
    public double getX() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.leftStickX;
    }

    @Override
    public double getY() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.leftStickY;
    }

    @Override
    public boolean requestingPause() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.guide;
    }

    @Override
    public boolean requestingMenu() {
        ControllerState state = GameUtils.getControllerManager().getState(id);
        return state.back;
    }
}
