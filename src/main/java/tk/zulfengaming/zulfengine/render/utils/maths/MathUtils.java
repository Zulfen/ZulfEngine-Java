package tk.zulfengaming.zulfengine.render.utils.maths;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.CallbackI;
import tk.zulfengaming.zulfengine.render.Camera;

public class MathUtils {

    // creates a matrix which defines how to scale, translate and rotate our vertex positions
    @org.jetbrains.annotations.NotNull
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rotX, float rotY, float rotZ,
                                                       float scale) {

        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        // translates our matrix by the given vector
        matrix.translate(translation);

        // rotates matrix by radians rotX, rotY and rotZ
        matrix.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0));
        matrix.rotate((float) Math.toRadians(rotY), new Vector3f(0, 1, 0));
        matrix.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 0, 1));

        matrix.scale(scale);

        return matrix;

    }

    public static Matrix4f createProjectionMatrix(int displayWidth, int displayHeight, float fov,
    float nearPlane, float farPlane) {

        // calculates the aspect ratio of the display
        float aspectRatio = (float) displayWidth / (float) displayHeight;

        return new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio, nearPlane, farPlane);

    }

    public static Matrix4f createViewMatrix(Camera camera) {

        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();

        // rotates matrix
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(1, 0, 1));

        Vector3f cameraPos = camera.getPosition();
        Vector3f inverseCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        //translates it in the opposite direction
        viewMatrix.translate(inverseCameraPos);

        return viewMatrix;

    }
}
