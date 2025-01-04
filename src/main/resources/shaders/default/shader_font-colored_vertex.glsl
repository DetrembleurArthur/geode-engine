#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec2 aTexCoord;
layout (location=2) in vec4 aColor;

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uModel;

out vec2 TexCoord;
out vec4 vColor;

void main()
{
    gl_Position = uProjection * uView * uModel * vec4(aPos, 1.0);
    TexCoord = aTexCoord;
    vColor = aColor;
}
