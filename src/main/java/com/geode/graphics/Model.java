package com.geode.graphics;

import com.geode.core.Resource;
import com.geode.core.reflections.Extensions;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.meshing.MeshAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model implements Resource {

    private static final Logger logger = LogManager.getLogger(Model.class);

    private static class Vertex {
        public int orderId = -1;
        public float[] coords;
    }

    private final List<Texture> textures = new ArrayList<>();
    private final List<Vector3f> pos = new ArrayList<>();
    private final List<Vector2f> uvs = new ArrayList<>();
    private final List<Vector3f> norms = new ArrayList<>();
    private final List<Integer> elements = new ArrayList<>();
    private final HashMap<String, Integer> verticesMap = new HashMap<>();
    private final List<Vertex> vertices = new ArrayList<>();
    private final Mesh mesh = new Mesh();
    private boolean hasTex = false;
    private boolean hasNorm = false;
    private final String path;

    public Model(String path) {
        if (!path.endsWith("/"))
            this.path = path + "/";
        else
            this.path = path;
    }

    @Override
    public boolean isLoaded() {
        return mesh.isReady();
    }

    @Override
    public void close() throws Exception {
        logger.info("close {} textures", textures.size());

        for (Texture texture : textures) {
            texture.close();
        }
        logger.info("close mesh");
        mesh.close();
    }

    @Override
    public void init() throws GeodeException {
        File directory = new File(path);
        System.err.println(path);
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getAbsolutePath().endsWith(Extensions.MODEL_OBJ.asExtension())) {
                logger.info("loading 3D model {}", file.getAbsolutePath());
                loadModel(file);
            } else if (file.getAbsolutePath().endsWith(Extensions.TEX_JPG.toString()) || file.getAbsolutePath().endsWith(Extensions.TEX_PNG.toString())) {
                logger.info("loading texture {}", file.getAbsolutePath());
                textures.add(new Texture(file.getAbsolutePath()));
            }
        }
        for(Texture texture : textures)
            texture.init();
        fillMesh();
        pos.clear();
        norms.clear();
        uvs.clear();
        verticesMap.clear();
        vertices.clear();
        elements.clear();
    }

    private void fillMesh() throws GeodeException {
        List<MeshAttribute> meshAttributes = new ArrayList<>();
        int vertexSize = 3;
        meshAttributes.add(MeshAttribute.createFloat(3));
        if (hasTex) {
            vertexSize += 2;
            meshAttributes.add(MeshAttribute.createFloat(2));
        }
        if (hasNorm) {
            vertexSize += 3;
            meshAttributes.add(MeshAttribute.createFloat(3));
        }
        final float[] data = new float[vertices.size() * (vertexSize)];
        final int[] i = {0, 0};
        int[] elementArray = new int[elements.size()];
        for(Vertex vertex : vertices) {
            for (float value : vertex.coords) {
                data[i[0]++] = value;
            }
        }
        elements.forEach(integer -> elementArray[i[1]++] = integer);
        mesh.init();
        mesh.fill(meshAttributes, data, elementArray);
        logger.info("mesh built: " + elementArray.length + " elements");
        logger.info("uvs: " + uvs.size() + ", pos: " + pos.size() + ", norms: " + norms.size());
    }

    private void loadModel(File file) throws GeodeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String buffer;
            Pattern vPattern = Pattern.compile("^v\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)$");
            Pattern vtPattern = Pattern.compile("^vt\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)$");
            Pattern vnPattern = Pattern.compile("^vn\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)$");
            Pattern fPos = Pattern.compile("^f\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)$");
            Pattern fPosTex = Pattern.compile("^f\\s+(\\d+)/(\\d+)\\s+(\\d+)/(\\d+)\\s+(\\d+)/(\\d+)$");
            Pattern fPosTexNorm = Pattern.compile("^f\\s+(\\d+)/(\\d+)/(\\d+)\\s+(\\d+)/(\\d+)/(\\d+)\\s+(\\d+)/(\\d+)/(\\d+)$");
            Pattern fPosNorm = Pattern.compile("^f\\s+(\\d+)//(\\d+)\\s+(\\d+)//(\\d+)\\s+(\\d+)//(\\d+)$");
            while ((buffer = reader.readLine()) != null) {
                buffer = buffer.trim();
                if (!buffer.isBlank() && !buffer.startsWith("#")) {
                    Matcher matcher = vPattern.matcher(buffer);
                    if (matcher.matches()) {
                        pos.add(new Vector3f(Float.parseFloat(matcher.group(1)), Float.parseFloat(matcher.group(2)), Float.parseFloat(matcher.group(3))));
                    } else if ((matcher = vtPattern.matcher(buffer)).matches()) {
                        hasTex = true;
                        uvs.add(new Vector2f(Float.parseFloat(matcher.group(1)), Float.parseFloat(matcher.group(2))));
                    } else if ((matcher = vnPattern.matcher(buffer)).matches()) {
                        hasNorm = true;
                        norms.add(new Vector3f(Float.parseFloat(matcher.group(1)), Float.parseFloat(matcher.group(2)), Float.parseFloat(matcher.group(3))));
                    } else if ((matcher = fPos.matcher(buffer)).matches()) {
                        createVertex(Integer.parseInt(matcher.group(1)) - 1, -1, -1);
                        createVertex(Integer.parseInt(matcher.group(2)) - 1, -1, -1);
                        createVertex(Integer.parseInt(matcher.group(3)) - 1, -1, -1);
                    } else if ((matcher = fPosTex.matcher(buffer)).matches()) {
                        createVertex(Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2)) - 1, -1);
                        createVertex(Integer.parseInt(matcher.group(3)) - 1, Integer.parseInt(matcher.group(4)) - 1, -1);
                        createVertex(Integer.parseInt(matcher.group(5)) - 1, Integer.parseInt(matcher.group(6)) - 1, -1);
                    } else if ((matcher = fPosTexNorm.matcher(buffer)).matches()) {
                        createVertex(Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(3)) - 1);
                        createVertex(Integer.parseInt(matcher.group(4)) - 1, Integer.parseInt(matcher.group(5)) - 1, Integer.parseInt(matcher.group(6)) - 1);
                        createVertex(Integer.parseInt(matcher.group(7)) - 1, Integer.parseInt(matcher.group(8)) - 1, Integer.parseInt(matcher.group(9)) - 1);
                    } else if ((matcher = fPosNorm.matcher(buffer)).matches()) {
                        createVertex(Integer.parseInt(matcher.group(1)) - 1, -1, Integer.parseInt(matcher.group(2)) - 1);
                        createVertex(Integer.parseInt(matcher.group(3)) - 1, -1, Integer.parseInt(matcher.group(4)) - 1);
                        createVertex(Integer.parseInt(matcher.group(5)) - 1, -1, Integer.parseInt(matcher.group(6)) - 1);
                    }
                }
            }
        } catch (IOException e) {
            throw new GeodeException(e);
        }
    }

    private void createVertex(int posId, int texId, int normId) {
        String id = posId + "/" + texId + "/" + normId;
        Integer order = verticesMap.get(id);
        if (order == null) {
            Vertex vertex = new Vertex();
            int vertexSize = 3;
            Vector3f position = pos.get(posId);
            Vector2f uv = null;
            Vector3f norm = null;
            int i = 0;
            if (texId != -1) {
                vertexSize += 2;
                uv = uvs.get(texId);
            }
            if (normId != -1) {
                vertexSize += 3;
                norm = norms.get(normId);
            }
            vertex.coords = new float[vertexSize];
            vertex.coords[i++] = position.x;
            vertex.coords[i++] = position.y;
            vertex.coords[i++] = position.z;
            if (uv != null) {
                vertex.coords[i++] = uv.x;
                vertex.coords[i++] = 1-uv.y;
            }
            if (norm != null) {
                vertex.coords[i++] = norm.x;
                vertex.coords[i++] = norm.y;
                vertex.coords[i] = norm.z;
            }
            vertex.orderId = vertices.size();
            order = vertex.orderId;
            vertices.add(vertex);
            verticesMap.put(id, order);
        }
        elements.add(order);
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public boolean isHasTex() {
        return hasTex;
    }

    public boolean isHasNorm() {
        return hasNorm;
    }

    public String getPath() {
        return path;
    }
}
