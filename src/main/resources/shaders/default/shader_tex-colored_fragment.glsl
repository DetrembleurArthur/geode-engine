#version 330 core

uniform vec4 uColor;
uniform sampler2D texture1;
out vec4 FragColor;

in vec2 TexCoord;
in vec4 vColor;

void main()
{
    FragColor = texture(texture1, TexCoord) * vColor * uColor;
} 
