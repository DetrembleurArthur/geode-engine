#version 330 core

uniform vec4 uColor;
uniform sampler2D texture1;
out vec4 FragColor;

in vec3 vNorms;
in vec2 TexCoord;

void main()
{
    vec3 norm = normalize(vNorms);
    vec3 lightDirNorm = normalize(vec3(1, -0.5, -0.5));

    // Calcul de la lumière diffuse (Lambertian)
    float diff = max(dot(norm, lightDirNorm), 0.0);

    // Couleur finale
    vec4 diffuse = diff * vec4(1, 1, 1, 1);

    FragColor = diffuse * texture(texture1, TexCoord) * uColor;
    FragColor.w = 1;
} 
