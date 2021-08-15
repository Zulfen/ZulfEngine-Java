package tk.zulfengaming.zulfengine.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.CallbackI;
import tk.zulfengaming.zulfengine.game.entity.Entity;
import tk.zulfengaming.zulfengine.render.model.FileMeshLoader;
import tk.zulfengaming.zulfengine.render.model.WorldModel;
import tk.zulfengaming.zulfengine.render.model.VAOLoader;
import tk.zulfengaming.zulfengine.render.shader.SimpleShader;
import tk.zulfengaming.zulfengine.render.utils.Texture;
import tk.zulfengaming.zulfengine.render.utils.maths.MathUtils;

import java.io.IOException;
import java.util.Iterator;

public class MainRenderer {

    private Entity cote;
    private Entity zulf;
    private final SimpleShader simpleShader;

    private final DisplayInstance displayInstance;

    private final Camera camera;

    private final VAOLoader vaoLoader;
    private final FileMeshLoader fileMeshLoader;

    public MainRenderer(DisplayInstance displayIn, SimpleShader shaderIn) {

        this.displayInstance = displayIn;
        this.vaoLoader = new VAOLoader();
        this.fileMeshLoader = new FileMeshLoader(vaoLoader);

        this.camera = new Camera();
        camera.setPosition(new Vector3f(0, 0, 0));
        this.simpleShader = shaderIn;

        resizeRenderer(displayIn.getWidth(), displayIn.getHeight());

        WorldModel model = fileMeshLoader.loadMeshFromFile("C:\\Users\\passj\\Desktop\\cube.obj", "C:\\Users\\passj\\Desktop\\");

        cote = new Entity(model, new Vector3f(0 ,0, 0), 0, 0, 0, 1);

    }

    public void renderEntity(Entity entityIn, SimpleShader shaderIn) {

        // binds the VAO we want to use
        GL30.glBindVertexArray(entityIn.getModel().getVboMesh().getVaoID());

        // enables the VBO in index 0 of the VAO (vertices)
        GL20.glEnableVertexAttribArray(0);

        // enables the VBO in index 1 of the VAO (texture coords)
        GL20.glEnableVertexAttribArray(1);

        // creates a transformation matrix from our entity's data
        Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(entityIn.getPosition(), entityIn.getRotationX(),
                entityIn.getRotationY(), entityIn.getRotationZ(), entityIn.getScale());

        // loads our transformation matrix into our given shader
        shaderIn.loadTransMat(transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entityIn.getModel().getTextures().get(0).getId());

        // draws it! we specify we want triangles to be drawn w/ the indices
        GL11.glDrawElements(GL11.GL_TRIANGLES, entityIn.getModel().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // disables the VAO in index <number> of the VAO
        GL20.glDisableVertexAttribArray(0);

        // disables the VAO in index <number> of the VAO
        GL20.glDisableVertexAttribArray(1);

        // disables the VAO we are using!
        GL30.glBindVertexArray(0);

    }

    public void resizeRenderer(int width, int height) {

        Matrix4f projectionMatrix = MathUtils.createProjectionMatrix(width, height,
                70, 0.1f, 1000f);

        simpleShader.enable();

        simpleShader.loadProjMat(projectionMatrix);

        simpleShader.disable();

        GL11.glViewport(0, 0, width, height);

    }

    public void render() {

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0f, 0.0f, 1);

        simpleShader.enable();
        simpleShader.loadViewMat(camera);

        renderEntity(cote, simpleShader);

        simpleShader.disable();

    }

    public VAOLoader getVaoLoader() {
        return vaoLoader;
    }

    public Entity getCote() {
        return cote;
    }

    public Camera getCamera() {
        return camera;
    }

    public SimpleShader getSimpleShader() {
        return simpleShader;
    }

    public DisplayInstance getDisplayInstance() {
        return displayInstance;
    }
}
