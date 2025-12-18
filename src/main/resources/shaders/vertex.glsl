#version 330 core

layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aUV;

out vec2 vUV;

uniform vec2 uPosition;
uniform vec2 uSize;

void main() {
    vec2 pos = aPos * uSize + uPosition;
    vUV = aUV;
    gl_Position = vec4(pos, 0.0, 1.0);
}
