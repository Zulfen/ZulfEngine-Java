package tk.zulfengaming.zulfengine.render.model;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import tk.zulfengaming.zulfengine.render.utils.Texture;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class VAOLoader {

    // keeps track of all VBOs and VAOs created
    private final ArrayList<Integer> vaos = new ArrayList<>();
    private final ArrayList<Integer> vbos = new ArrayList<>();
    private final ArrayList<Integer> textures = new ArrayList<>();

    public VBOMesh storeInVAO(int vaoIndex, float[] vertices, int[] indices, float[] texCoords) {

        // creates and gets the ID of our VAO
        int vaoID = createVAO();

        // gives OGL our indices
        createIndicesBuf(indices);

        // stores into an attribute index to put into the VAO the vertices and texture coords
        storeInAB(vaoIndex, 3, vertices);
        storeInAB(vaoIndex + 1, 2, texCoords);

        // unbinds our VAO after use
        GL30.glBindVertexArray(0);

        // returns the new BasicModel

        // 2nd argument is our actual number of vertices, and that is equal to the number of indices
        return new VBOMesh(vaoID, indices.length);
    }

    public void cleanUp() {

        // deletes every VAO registered from OGL
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }

        // deletes every VBO registered from OGL
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }

    private void createIndicesBuf(int[] indices) {

        // creates an empty VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        // tells OGL to bind to this buffer, but instead tell it that it's an element array this time
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        IntBuffer buffer = storeInIB(indices);

        // give OGL this buffer data
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private int createVAO() {

        // creates new VAO and returns it's ID
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);

        // activate / bind this VAO to be used
        GL30.glBindVertexArray(vaoID);

        return vaoID;

    }

    private void storeInAB(int attribIndex, int dataSize, float[] data) {
        // the data parameter in this method will most likely be vertices

        // creates empty VBO and returns it's ID so we can put stuff in there
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        // give OGL this new buffer to bind to, with the enum specifying that we want to use a VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        FloatBuffer buffer = storeInFB(data);

        // put our data into this new VBO and tell OGL that we won't need to modify this data again through
        // GL_STATIC_DRAW
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // tells OGL about our data to put into the VAO. time to explain the args in order!
        // index of where we want to put this in the VAO, the length of our data
        // , is it normalised (no), distance between each vertex (0), and where to start reading our
        // data from (the start of course, so index of zero)
        GL20.glVertexAttribPointer(attribIndex, dataSize, GL11.GL_FLOAT, false, 0, 0);

        // we have finished defining this VBO, so we must unbind it

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

    }

    // must be in PNG format
    public Texture loadTexture(String filePath) throws IOException {

        // loads the image in with the PNG decoder
        PNGDecoder decoder = new PNGDecoder(new FileInputStream(filePath));

        // creates a byte buffer to store RGBA values
        ByteBuffer buffer = ByteBuffer.allocateDirect(
                4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

        // flips the image so it can be read
        buffer.flip();

        // creates a blank texture and returns its ID
        int textureID = GL11.glGenTextures();
        textures.add(textureID);

        // binds the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // tells OGL how to unpack our bytes
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // describes our texture filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        // uploads our texture data
        // texture type, level of detail (for mipmapping), the internal format, width, height, border, format, type of
        // data and our data
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0
        , GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        // generates our mipmap
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        return new Texture(textureID, buffer);

    }

    private IntBuffer storeInIB(int[] data) {

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;

    }

    private FloatBuffer storeInFB(float[] data) {

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;

    }

}
