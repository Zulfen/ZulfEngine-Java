package tk.zulfengaming.zulfengine.game.events;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {

    private final boolean[] lastKeyPressed = new boolean[GLFW.GLFW_KEY_LAST];
    private final boolean[] lastMousePressed = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private double mouseX, mouseY;

    private final GLFWKeyCallback keyboardCallback;
    private final GLFWCursorPosCallback mousePosCallback;
    private final GLFWMouseButtonCallback mouseButtonCallback;

    public Input(long windowHandleIn) {

        this.keyboardCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2, int i3) {

                lastKeyPressed[i] = (i2 != GLFW.GLFW_RELEASE);

            }
        };

        this.mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2) {

                lastMousePressed[i] = (i1 != GLFW.GLFW_RELEASE);

            }
        };

        this.mousePosCallback = new GLFWCursorPosCallback() {

            @Override
            public void invoke(long l, double v, double v1) {
                mouseX = v;
                mouseY = v1;
            }


        };

        GLFW.glfwSetKeyCallback(windowHandleIn, keyboardCallback);
        GLFW.glfwSetCursorPosCallback(windowHandleIn, mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(windowHandleIn, mouseButtonCallback);

    }

    public void cleanUp() {
        keyboardCallback.free();
        mousePosCallback.free();
        mouseButtonCallback.free();
    }

    public boolean isKeyDown(int key) {
        return lastKeyPressed[key];
    }

    public boolean isMouseDown(int button) {
        return lastMousePressed[button];
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
