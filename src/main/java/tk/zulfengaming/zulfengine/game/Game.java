package tk.zulfengaming.zulfengine.game;

import org.lwjgl.glfw.GLFW;
import tk.zulfengaming.zulfengine.game.events.Input;
import tk.zulfengaming.zulfengine.render.DisplayInstance;
import tk.zulfengaming.zulfengine.render.MainRenderer;
import tk.zulfengaming.zulfengine.render.shader.SimpleShader;

public class Game  {

    private final DisplayInstance displayInstance;

    private final MainRenderer renderer;

    private final Input inputEvents;

    public Game() {

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialise GLFW");
        }

        this.displayInstance = new DisplayInstance(800, 600, "GLFW Test");
        this.renderer = new MainRenderer(displayInstance, new SimpleShader());

        this.inputEvents = new Input(displayInstance.getWindowHandle());
    }

    public void render() {

        renderer.render();

        GLFW.glfwSwapBuffers(displayInstance.getWindowHandle());

    }

    public void update() {

        GLFW.glfwPollEvents();

    }

    public DisplayInstance getDisplayInstance() {
        return displayInstance;
    }

    public void run() {

        while (!GLFW.glfwWindowShouldClose(displayInstance.getWindowHandle())) {
            update();
            render();
        }

        renderer.getVaoLoader().cleanUp();
        renderer.getSimpleShader().cleanUp();
        inputEvents.cleanUp();

        GLFW.glfwTerminate();


    }

    public Input getInputEvents() {
        return inputEvents;
    }

}
