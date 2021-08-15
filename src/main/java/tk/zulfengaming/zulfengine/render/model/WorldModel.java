package tk.zulfengaming.zulfengine.render.model;

import tk.zulfengaming.zulfengine.render.utils.Texture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldModel {

    private final float[] vertices;
    private final int[] indices;
    private final float[] textureCoords;

    private final ArrayList<Texture> textures = new ArrayList<>();

    private final VBOMesh vboMesh;

    public WorldModel(float[] verticesIn, int[] indicesIn, float[] textureCoordsIn, String texturePathIn, VAOLoader utilIn) {

        this.vertices = verticesIn;
        this.indices = indicesIn;
        this.textureCoords = textureCoordsIn;

        try {

            textures.add(utilIn.loadTextureFromFile(texturePathIn));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.vboMesh = utilIn.storeInVAO(0, verticesIn, indicesIn, textureCoordsIn);


    }

    public WorldModel(float[] verticesIn, int[] indicesIn, float[] textureCoordsIn, Texture[] texturesIn, VAOLoader utilIn) {

        this.vertices = verticesIn;
        this.indices = indicesIn;
        this.textureCoords = textureCoordsIn;

        textures.addAll(Arrays.asList(texturesIn));

        this.vboMesh = utilIn.storeInVAO(0, verticesIn, indicesIn, textureCoordsIn);


    }


    public float[] getVertices() {
        return vertices;
    }

    public ArrayList<Texture> getTextures() {
        return textures;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public int[] getIndices() {
        return indices;
    }

    public VBOMesh getVboMesh() {
        return vboMesh;
    }

}
