package tk.zulfengaming.zulfengine.render.model;

import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.CallbackI;
import tk.zulfengaming.zulfengine.render.utils.Texture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FileMeshLoader {

    private final VAOLoader vaoLoader;

    public FileMeshLoader(VAOLoader loaderIn) {

        this.vaoLoader = loaderIn;

    }

    public WorldModel loadMeshFromFile(String modelPath, String texturesPath) {

        // loads a scene from a given file path and sets some flags to optimise the model
        AIScene loadedModel = Assimp.aiImportFile(modelPath,
        Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals
                | Assimp.aiProcess_JoinIdenticalVertices);

        if (loadedModel == null) {
            throw new IllegalStateException("Error loading model: " + Assimp.aiGetErrorString());
        }

        // loads the vertices and indices
        PointerBuffer loadedMeshes = loadedModel.mMeshes();

        LinkedList<Float> verticesList = new LinkedList<>();
        LinkedList<Integer> indicesList = new LinkedList<>();
        LinkedList<Texture> texturesList = new LinkedList<>();
        LinkedList<Float> uvCoordsList = new LinkedList<>();

        // processes the textures for the model first
        processTextures(loadedModel, texturesPath, texturesList);

        if (loadedMeshes != null) {

            for (int i = 0; i < loadedModel.mNumMeshes(); i++) {

                AIMesh aiMesh = AIMesh.create(loadedMeshes.get(i));
                processVertices(aiMesh, verticesList);
                processIndices(aiMesh, indicesList);
                processUVCoords(aiMesh, uvCoordsList);

            }

        }

        for (Texture texture : texturesList) {

            System.out.println(texture.getHeight());

        }

        return new WorldModel(Floats.toArray(verticesList), Ints.toArray(indicesList),
                Floats.toArray(uvCoordsList), texturesList.toArray(new Texture[0]), vaoLoader);

    }

    private void processVertices(AIMesh mesh, List<Float> vertices) {

        AIVector3D.Buffer aiVertices = mesh.mVertices();

        while (aiVertices.hasRemaining()) {

            AIVector3D aiVertex = aiVertices.get();

            vertices.add(aiVertex.x());
            vertices.add(aiVertex.y());
            vertices.add(aiVertex.z());


        }

    }

    private void processTextures(AIScene model, String texturesPath, List<Texture> textures) {

        PointerBuffer loadedMaterials = model.mMaterials();

        if (loadedMaterials != null) {

            while (loadedMaterials.hasRemaining()) {

                AIMaterial material = AIMaterial.create(loadedMaterials.get());

                AIString aiPathString = AIString.calloc();

                Assimp.aiGetMaterialTexture(material, Assimp.aiTextureType_DIFFUSE, 0, aiPathString,
                        (IntBuffer) null, null, null, null, null, null);

                String retrievedPath = aiPathString.dataString();

                if (!retrievedPath.isEmpty()) {

                    Path texturePath = Paths.get(texturesPath, retrievedPath);

                    try {
                        textures.add(vaoLoader.loadTextureFromFile(texturePath.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }

    private void processUVCoords(AIMesh mesh, List<Float> uVCoords) {

        AIVector3D.Buffer aiUVCoords = mesh.mTextureCoords(0);

        if (aiUVCoords != null) {

            while (aiUVCoords.hasRemaining()) {

                AIVector3D aiVector3D = aiUVCoords.get();

                uVCoords.add(aiVector3D.x());
                uVCoords.add(aiVector3D.y());

            }

        }



    }

    private void processIndices(AIMesh mesh, List<Integer> indices) {

        AIFace.Buffer aiFaces = mesh.mFaces();

        while (aiFaces.hasRemaining()) {

            AIFace aiFace = aiFaces.get();
            IntBuffer indicesBuffer = aiFace.mIndices();

            for (int i = 0; i < aiFace.mNumIndices(); i++) {
                indices.add(indicesBuffer.get(i));
            }

        }


    }

}
