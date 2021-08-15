package tk.zulfengaming.zulfengine.render.utils;

public class Texture {

    private final int id;
    private final int width;
    private final int height;

    public Texture(int id, int widthIn, int heightIn) {
        this.id = id;
        this.width = widthIn;
        this.height = heightIn;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

}
