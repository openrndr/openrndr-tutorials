#version 330

// -- part of the filter interface
in vec2 v_texCoord0;
uniform sampler2D tex0;


// -- user parameters
uniform float gain;
uniform float time;

// -- default output
out vec4 o_color;

#define HASHSCALE 443.8975
vec2 hash22(vec2 p) {
	vec3 p3 = fract(vec3(p.xyx) * HASHSCALE);
    p3 += dot(p3, p3.yzx+19.19);
    return fract(vec2((p3.x + p3.y)*p3.z, (p3.x+p3.z)*p3.y));
}

void main() {
    float n = hash22(v_texCoord0+vec2(time)).x;
    vec4 color = texture(tex0, v_texCoord0) + vec4(vec3(n), 0.0) * gain;
    o_color = color;
}