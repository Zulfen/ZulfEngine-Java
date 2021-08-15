package tk.zulfengaming.zulfengine.game.events;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import tk.zulfengaming.zulfengine.game.Game;
import tk.zulfengaming.zulfengine.render.DisplayInstance;
import tk.zulfengaming.zulfengine.render.MainRenderer;

public class WindowEvents {

    private int width, height;

    public WindowEvents(MainRenderer renderer) {

        GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                width = i;
                height = i1;
                renderer.resizeRenderer(width, height);
            }
        };

        GLFW.glfwSetWindowSizeCallback(renderer.getDisplayInstance().getWindowHandle(), windowSizeCallback);
    }
}
