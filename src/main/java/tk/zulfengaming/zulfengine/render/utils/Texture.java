package tk.zulfengaming.zulfengine.render.utils;

import java.nio.ByteBuffer;

public class Texture {

    private final int id;
    private final ByteBuffer imageData;

    public Texture(int id, ByteBuffer dataIn) {
        this.id = id;
        this.imageData = dataIn;
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public int getId() {
        return id;
    }

}
