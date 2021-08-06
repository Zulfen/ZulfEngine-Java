package tk.zulfengaming.zulfengine.game.entity;

import org.joml.Vector3f;
import tk.zulfengaming.zulfengine.render.model.WorldModel;

public class Entity {

    private final WorldModel model;

    private Vector3f position;

    private float rotationX, rotationY, rotationZ, scale;

    public Entity(WorldModel model, Vector3f position, float rotationX, float rotationY, float rotationZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scale = scale;
    }

    public void changeRotation(float dx, float dy, float dz) {
        rotationX += dx;
        rotationY += dy;
        rotationZ += dz;
    }

    public void changePosition(float dx, float dy, float dz) {
        position.x += dx;
        position.y += dy;
        position.z += dz;
    }

    public void changeScale(float scaleDelta) {
        scale += scaleDelta;
    }

    public WorldModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotationX() {
        return rotationX;
    }

    public void setRotationX(float rotationX) {
        this.rotationX = rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    public float getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
