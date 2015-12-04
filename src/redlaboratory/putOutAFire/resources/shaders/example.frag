varying vec4 color;

uniform int textureMode;
uniform sampler2D texture0;
uniform sampler2D texture1;

void main() {
	if (textureMode == 0) {
		gl_FragColor = color;
	} else if (textureMode == 1) {
		gl_FragColor = texture2D(texture0, gl_TexCoord[0].st);
	}
}
