package com.geode.graphics;

import com.geode.core.Closeable;
import com.geode.core.Initializable;
import com.geode.core.Resource;
import com.geode.core.SceneManager;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.*;

public class Shader implements Initializable, Closeable, Resource {

    private static final Logger logger = LogManager.getLogger(Shader.class);

    private int vertexShader = 0;
    private int fragmentShader = 0;
    private int program = 0;
    private final String vertexShaderPath;
    private final String fragmentShaderPath;

    public Shader(String vertexShaderPath, String fragmentShaderPath) {
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;
    }

    private String[] loadSources() throws IOException {
        String[] sources = new String[2];
        try(BufferedReader br = new BufferedReader(new FileReader(vertexShaderPath))) {
            sources[0] = "";
            String buffer;
            while((buffer = br.readLine()) != null) {
                sources[0] += buffer + "\n";
            }
        }
        try(BufferedReader br = new BufferedReader(new FileReader(fragmentShaderPath))) {
            sources[1] = "";
            String buffer;
            while((buffer = br.readLine()) != null) {
                sources[1] += buffer + "\n";
            }
        }
        return sources;
    }

    @Override
    public void init() throws GeodeException {
        try {
            String[] sources = loadSources();
            vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
            fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
            logger.info("vertexShader sources: {}", sources[0]);
            logger.info("fragmentShader sources: {}", sources[1]);
            GL20.glShaderSource(vertexShader, sources[0]);
            GL20.glShaderSource(fragmentShader, sources[1]);
            GL20.glCompileShader(vertexShader);
            GL20.glCompileShader(fragmentShader);
            int[] status = new int[1];
            GL20.glGetShaderiv(vertexShader, GL20.GL_COMPILE_STATUS, status);
            if(status[0] == GL20.GL_FALSE) {
                String log = GL20.glGetShaderInfoLog(vertexShader);
                logger.error("cannot compile vertex shader: {}", log);
                throw new GeodeException(log);
            }
            GL20.glGetShaderiv(fragmentShader, GL20.GL_COMPILE_STATUS, status);
            if(status[0] == GL20.GL_FALSE) {
                String log = GL20.glGetShaderInfoLog(fragmentShader);
                GL20.glDeleteShader(vertexShader);
                vertexShader = 0;
                fragmentShader = 0;
                logger.error("cannot compile fragment shader: {}", log);
                throw new GeodeException(log);
            }
            program = GL20.glCreateProgram();
            GL20.glAttachShader(program, vertexShader);
            GL20.glAttachShader(program, fragmentShader);
            GL20.glLinkProgram(program);
            GL20.glGetProgramiv(program, GL20.GL_LINK_STATUS, status);
            if(status[0] == GL20.GL_FALSE) {
                String log = GL20.glGetProgramInfoLog(program);
                logger.error("cannot create program: {}", log);
                GL20.glDeleteShader(vertexShader);
                GL20.glDeleteShader(fragmentShader);
                vertexShader = 0;
                fragmentShader = 0;
                throw new GeodeException(log);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("shader created");
    }

    @Override
    public void close() throws Exception {
        if(vertexShader != 0) {
            GL20.glDeleteShader(vertexShader);
            vertexShader = 0;
        }
        if(fragmentShader != 0) {
            GL20.glDeleteShader(fragmentShader);
            fragmentShader = 0;
        }
        if(program != 0) {
            GL20.glDeleteProgram(program);
            program = 0;
        }
        logger.info("shader closed");
    }
}
