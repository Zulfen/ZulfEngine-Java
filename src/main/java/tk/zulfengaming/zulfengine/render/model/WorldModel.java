package tk.zulfengaming.zulfengine.render.model;

import tk.zulfengaming.zulfengine.render.utils.Texture;

public class WorldModel {

    private final float[] vertices;
    private final int[] indices;
    private final float[] textureCoords;

    private final Texture texture;

    private final VBOMesh vboMesh;

    public WorldModel(float[] verticesIn, int[] indicesIn, float[] textureCoordsIn, Texture textureIn, VAOLoader utilIn) {

        this.vertices = verticesIn;
        this.indices = indicesIn;
        this.textureCoords = textureCoordsIn;
        this.texture = textureIn;

        this.vboMesh = utilIn.storeInVAO(0, verticesIn, indicesIn, textureCoordsIn);

    }

    public Texture getTexture() {
        return texture;
    }

    public float[] getVertices() {
        return vertices;
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
