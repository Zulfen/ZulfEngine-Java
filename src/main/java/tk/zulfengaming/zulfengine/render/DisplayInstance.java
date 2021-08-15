package tk.zulfengaming.zulfengine.render;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayInstance {

    private final int width;
    private final int height;

    private final long windowHandle;

    public DisplayInstance(int widthIn, int heightIn, String titleIn) {

        this.width = widthIn;
        this.height = heightIn;

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);

        this.windowHandle = GLFW.glfwCreateWindow(widthIn, heightIn, titleIn, 0, 0);

        if (windowHandle == 0) {
            throw new IllegalStateException("GLFW window failed to initialise");
        }

        GLFW.glfwShowWindow(windowHandle);
        GLFW.glfwSwapInterval(0);
        GLFW.glfwMakeContextCurrent(windowHandle);

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowHandle() {
        return windowHandle;
    }

}
