package tk.zulfengaming.zulfengine.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import tk.zulfengaming.zulfengine.game.entity.Entity;
import tk.zulfengaming.zulfengine.render.model.WorldModel;
import tk.zulfengaming.zulfengine.render.model.VAOLoader;
import tk.zulfengaming.zulfengine.render.shader.SimpleShader;
import tk.zulfengaming.zulfengine.render.utils.maths.MathUtils;

import java.io.IOException;

public class MainRenderer {

    private Entity cote;
    private Entity zulf;
    private SimpleShader simpleShader;
    private final VAOLoader vaoLoader;

    public MainRenderer(DisplayInstance displayIn, SimpleShader shaderIn) {

        this.vaoLoader = new VAOLoader();

        Matrix4f projectionMatrix = MathUtils.createProjectionMatrix(displayIn.getWidth(), displayIn.getHeight(),
                150, 0.1f, 1000f);

        this.simpleShader = shaderIn;

        simpleShader.enable();
        simpleShader.loadProjMat(projectionMatrix);
        simpleShader.disable();

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
            0, 1, 3, // left triangle
            3, 1, 2 // right triangle
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        try {

            WorldModel coteModel = new WorldModel(vertices, indices, textureCoords,
                    vaoLoader.loadTexture("D:\\Java\\ZulfEngine\\src\\main\\java\\tk\\zulfengaming\\zulfengine\\render\\utils\\textures\\cote.png"), vaoLoader);

            WorldModel zulfModel = new WorldModel(vertices, indices, textureCoords,
                    vaoLoader.loadTexture("D:\\Java\\ZulfEngine\\src\\main\\java\\tk\\zulfengaming\\zulfengine\\render\\utils\\textures\\zulf.png"), vaoLoader);

            cote = new Entity(coteModel, new Vector3f(0.0f ,0, -1), 0, 0, 0, 1);
            zulf = new Entity(zulfModel, new Vector3f(0.0f ,0, -5), 0, 0, 0, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderModel(Entity entityIn, SimpleShader shaderIn) {

        // enables the shader for change
        shaderIn.enable();

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

        // enables texture bank 0
        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        // binds to our texture we defined!
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entityIn.getModel().getTexture().getId());

        // draws it! we specify we want triangles to be drawn w/ the indices
        GL11.glDrawElements(GL11.GL_TRIANGLES, entityIn.getModel().getVboMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        // disables the VAO in index <number> of the VAO
        GL20.glDisableVertexAttribArray(0);

        // disables the VAO we are using!
        GL30.glBindVertexArray(0);

        // disables the shader to stop change
        shaderIn.disable();

    }
    public void render() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0,0, 0, 0);

        renderModel(cote, simpleShader);
        renderModel(zulf, simpleShader);

    }

    public VAOLoader getVaoLoader() {
        return vaoLoader;
    }

    public Entity getCote() {
        return cote;
    }

    public Entity getZulf() {
        return zulf;
    }

    public SimpleShader getSimpleShader() {
        return simpleShader;
    }
}
