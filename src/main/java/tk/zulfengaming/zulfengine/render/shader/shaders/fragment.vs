#version 150

in vec2 texCoordsOut;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(){

	out_Color = texture(textureSampler, texCoordsOut);

}