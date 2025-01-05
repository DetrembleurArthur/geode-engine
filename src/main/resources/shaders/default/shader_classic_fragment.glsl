#version 330 core

uniform vec4 uColor;
out vec4 FragColor;

in vec4 vColor;

void main()
{
    FragColor = vColor * uColor;
} 
