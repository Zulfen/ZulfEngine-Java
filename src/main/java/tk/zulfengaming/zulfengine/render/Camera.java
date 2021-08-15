package tk.zulfengaming.zulfengine.render;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);

    private float pitch, yaw, roll;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public Camera() {}

    public void moveX(float dx) {
        position.x += dx;
    }

    public void moveY(float dy) {
        position.y += dy;
    }

    public void moveZ(float dz) {
        position.z += dz;
    }

    public void changeYaw(float delta) {
        System.out.println(delta);
        yaw += delta;
    }

    public void changeRoll(float delta) {
        roll += delta;
    }

    public void changePitch(float delta) {
        pitch += delta;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

}
