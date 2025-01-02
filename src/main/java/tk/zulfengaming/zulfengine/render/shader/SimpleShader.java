package tk.zulfengaming.zulfengine.render.shader;

import org.joml.Matrix4f;
import tk.zulfengaming.zulfengine.render.Camera;
import tk.zulfengaming.zulfengine.render.utils.maths.MathUtils;

public class SimpleShader extends ShaderProgram {

    // Quick hack to get this to not use hardcoded paths
    private final static String VERTEX_SHADER_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\tk\\zulfengaming\\zulfengine\\render\\shader\\shaders\\vertex.vs";
    private final static String FRAGMENT_SHADER_PATH = System.getProperty("user.dir") +"\\src\\main\\java\\tk\\zulfengaming\\zulfengine\\render\\shader\\shaders\\fragment.vs";

    // i probably should implement a system so i dont have to define these manually
    // but kind of tired
    private int locTransMat;
    private int locProjMat;
    private int viewMat;

    public SimpleShader() {
        super(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
    }

    @Override
    protected void bindShaderAttributes() {
        // where in the VAO the VBO is, name of the variable
        bindShaderAttribute(0, "position");
        bindShaderAttribute(1, "texCoordsIn");
    }

    @Override
    protected void getAllUniformLocations() {
        locTransMat = getUniformLocation("transformationMatrix");
        locProjMat = getUniformLocation("projectionMatrix");
        viewMat = getUniformLocation("viewMatrix");
    }

    public void loadTransMat(Matrix4f matrixIn) {

        // stores our matrix into the uniform we got
        storeMatrixUniform(locTransMat, matrixIn);

    }

    public void loadProjMat(Matrix4f matrixIn) {

        storeMatrixUniform(locProjMat, matrixIn);

    }

    public void loadViewMat(Camera cameraIn) {
        Matrix4f viewMatrix = MathUtils.createViewMatrix(cameraIn);
        storeMatrixUniform(viewMat, viewMatrix);
    }
}
