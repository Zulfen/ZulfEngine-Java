#version 150

in vec3 position;
in vec2 texCoordsIn;
out vec2 texCoordsOut;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(){

	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position,1.0);
	texCoordsOut = texCoordsIn;
}