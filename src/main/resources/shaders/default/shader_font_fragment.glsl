#version 330 core

uniform vec4 uColor;
uniform sampler2D texture1;
out vec4 FragColor;

in vec2 TexCoord;

void main()
{
    FragColor = uColor * vec4(1, 1, 1, texture(texture1, TexCoord).r);
} 
