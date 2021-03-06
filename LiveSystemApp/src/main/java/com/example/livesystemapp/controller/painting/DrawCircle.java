package com.example.livesystemapp.controller.painting;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DrawCircle {
    double x1, y1, x2;
    Canvas canvas_effect;
    GraphicsContext gc, gc_effect;
    Boolean isActive;

    public DrawCircle(Canvas canvas_effect, GraphicsContext gc, GraphicsContext gc_effect) {
        this.canvas_effect = canvas_effect;
        this.gc = gc;
        this.gc_effect = gc_effect;
        isActive = false;
    }

    private final EventHandler<MouseEvent> pressed = (event) -> {
        x1 = event.getX();
        y1 = event.getY();
    };

    private final EventHandler<MouseEvent> dragged = (event) -> {
        x2 = event.getX();
        gc_effect.clearRect(0, 0, canvas_effect.getWidth(), canvas_effect.getHeight());
        if (x2 > x1) {
            gc_effect.strokeOval(x1, y1, x2 - x1, x2 - x1);
//            x2 - x1 是半径
        } else if (x2 < x1) {
            gc_effect.strokeOval(x1, y1, Math.abs(x1 - x2), Math.abs(x1 - x2));
        }
    };

    private final EventHandler<MouseEvent> released = (event) -> {
        gc_effect.clearRect(0, 0, canvas_effect.getWidth(), canvas_effect.getHeight());
        if (x2 > x1) {
            gc.strokeOval(x1, y1, x2 - x1, x2 - x1);
        } else if (x2 < x1) {
            gc.strokeOval(x1, y1, Math.abs(x1 - x2), Math.abs(x1 - x2));
        }
    };

    public void takeAction() {
        canvas_effect.addEventHandler(MouseEvent.MOUSE_PRESSED, pressed);
        canvas_effect.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragged);
        canvas_effect.addEventHandler(MouseEvent.MOUSE_RELEASED, released);
        isActive = true;
    }

    public void removeAction() {
        canvas_effect.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressed);
        canvas_effect.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragged);
        canvas_effect.removeEventHandler(MouseEvent.MOUSE_RELEASED, released);
        isActive = false;
    }
}
