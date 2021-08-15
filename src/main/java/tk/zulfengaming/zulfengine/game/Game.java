package tk.zulfengaming.zulfengine.game;

import org.lwjgl.glfw.GLFW;
import tk.zulfengaming.zulfengine.game.events.InputEvents;
import tk.zulfengaming.zulfengine.game.events.WindowEvents;
import tk.zulfengaming.zulfengine.render.DisplayInstance;
import tk.zulfengaming.zulfengine.render.MainRenderer;
import tk.zulfengaming.zulfengine.render.shader.SimpleShader;

public class Game  {

    private final DisplayInstance displayInstance;

    private final MainRenderer renderer;

    private final InputEvents inputEvents;
    private final WindowEvents windowEvents;

    public Game() {

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialise GLFW");
        }

        this.displayInstance = new DisplayInstance(800, 600, "ZulfEngine");
        this.renderer = new MainRenderer(displayInstance, new SimpleShader());

        this.inputEvents = new InputEvents(displayInstance.getWindowHandle());
        this.windowEvents = new WindowEvents(renderer);
    }

    public void render() {

        renderer.render();

        GLFW.glfwSwapBuffers(displayInstance.getWindowHandle());

    }

    public void update() {

        if (inputEvents.isKeyDown(GLFW.GLFW_KEY_S)) {
            renderer.getCamera().moveZ(0.01f);
        }
        if (inputEvents.isKeyDown(GLFW.GLFW_KEY_W)) {
            renderer.getCamera().moveZ(-0.01f);
        }
        if (inputEvents.isKeyDown(GLFW.GLFW_KEY_A)) {
            renderer.getCamera().moveX(-0.01f);
        }
        if (inputEvents.isKeyDown(GLFW.GLFW_KEY_D)) {
            renderer.getCamera().moveX(0.01f);
        }

        GLFW.glfwPollEvents();

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

    public InputEvents getInputEvents() {
        return inputEvents;
    }

    public WindowEvents getWindowEvents() {
        return windowEvents;
    }
}
