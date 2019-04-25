#version 330

// list uniforms which are used by this shader
uniform vec3      iResolution;           // viewport resolution (in pixels)
uniform float     iTime;                 // shader playback time (in seconds)
//uniform float     iTimeDelta;            // render time (in seconds)
//uniform int       iFrame;                // shader playback frame
//uniform float     iChannelTimeN;         // channel playback time (in seconds)
//uniform vec3      iChannelResolutionN;   // channel resolution (in pixels)
//uniform vec4      iMouse;                // mouse pixel coords. xy: current (if MLB down), zw: click
//uniform sampler2D iChannelN;             // input channel. XX = 2D/Cube
//uniform vec4      iDate;                 // (year, month, day, time in seconds)
//uniform float     iSampleRate;           // sound sample rate (i.e., 44100)

// -- default output
out vec4 o_color;

void mainImage(out vec4 fragColor, in vec2 fragCoord);

void main() {
  mainImage(o_color, gl_FragCoord.xy);
}


// ------------------------------
//  SHADERTOY CODE BEGINS HERE  -
// ------------------------------

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
  // Normalized pixel coordinates (from 0 to 1)
  vec2 uv = fragCoord/iResolution.xy;

  // Time varying pixel color
  vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));

  // Output to screen
  fragColor = vec4(col,1.0);
}

// ----------------------------
//  SHADERTOY CODE ENDS HERE  -
// ----------------------------
