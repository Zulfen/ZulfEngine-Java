package tk.zulfengaming.zulfengine.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public abstract class ShaderProgram {

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertPath, String fragPath) {

        // loads both the frag and vert shader from the given paths
        this.vertexShaderID = loadShader(vertPath, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderID = loadShader(fragPath, GL20.GL_FRAGMENT_SHADER);

        // creates a new shader program and returns an ID
        this.programID = GL20.glCreateProgram();

        // attaches the vert and frag shader to the program
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        // binds our shader attributes
        bindShaderAttributes();

        // gets our uniform locations
        getAllUniformLocations();

    }

    public void enable() {

        // tells OGL to use this program
        GL20.glUseProgram(programID);

    }

    public void disable() {
        // tells OGL to stop using this program
        GL20.glUseProgram(0);
    }

    public void cleanUp() {

        // detaches our shaders
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);

        // deletes our shaders from the GPU

        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);

        // finally, deletes our program

        GL20.glDeleteProgram(programID);

    }

    protected abstract void bindShaderAttributes();

    protected abstract void getAllUniformLocations();

    protected void bindShaderAttribute(int attributeIndex, String varName) {

        // binds a location in the VAO to a variable in the shader
        // if we put our vertex positions in 0, for example, we would link the gl_Position variable to that VBO :)

        GL20.glBindAttribLocation(programID, attributeIndex, varName);

        // links and validates it
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

    }


    protected int getUniformLocation(String variableName) {
        return GL20.glGetUniformLocation(programID, variableName);
    }

    protected void storeFloatUniform(int uniformLocation,  float value) {

        // sets the uniform to be one float value
        GL20.glUniform1f(uniformLocation, value);

    }

    protected void storeVectorUniform(int uniformLocation, Vector3f vector) {

        // sets the uniform to be 3 floats
        GL20.glUniform3f(uniformLocation, vector.x, vector.y, vector.z);

    }

    protected void storeBooleanUniform(int uniformLocation, boolean state) {

        float toStore = 0;

        if (state) toStore = 1;

        GL20.glUniform1f(uniformLocation, toStore);

    }

    protected void storeMatrixUniform(int uniformLocation, Matrix4f matrix) {

        // stores matrix data into buffer
        matrix.get(matrixBuffer);

        // gives it to OGL

        GL20.glUniformMatrix4fv(uniformLocation, false, matrixBuffer);
    }

    private int loadShader(String filePath, int type) {

        StringBuilder shaderSource = new StringBuilder();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = fileReader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // creates shader from given type and returns its ID
        int shaderID = GL20.glCreateShader(type);

        // gives opengl the shader source
        GL20.glShaderSource(shaderID, shaderSource);

        // compiles it!
        GL20.glCompileShader(shaderID);

        // checks if the shader compiled successfully
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {

            throw new IllegalStateException("Could not compile OpenGL shader: " + filePath);

        }

        return shaderID;

    }
}
