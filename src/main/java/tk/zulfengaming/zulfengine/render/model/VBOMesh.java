package tk.zulfengaming.zulfengine.render.model;

public class VBOMesh {

    // represents a basic model to put into the VAO. has no other attributes

    private final int vaoID;
    private final int vertexCount;

    public VBOMesh(int vaoIDIn, int vertexCountIn) {
        this.vaoID = vaoIDIn;
        this.vertexCount = vertexCountIn;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
