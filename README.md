# Graphics-Final
cmsc381 final


Starter code to get OpenGL up and running was found at: https://developer.android.com/training/graphics/opengl

I couldn't get 3d to work, I have the code the initialize 3d objects (See Cube and Pyramid Classes).

OpenGL in Android is quite different than in c++, you have to define a vertex and fragment shader for each individual class object, and can define and use different view matrices for each object.

It is much more modular, as it defines functions to be called at different stages of the program. You can look in "MyGLSurfaceView.java" and "MyGLRenderer.java" for these.

I tried to implement another behaviour where you can click on a screen and a shape will appear where you clicked, but the object just disappears as soon as you click, even after I adjusted for the bounds of the screen. It might have something to do with how I'm using the translation matrix to move the object, but I'm not sure.

I tried to get the bouncing objects to reset to a slower speed after it goes over a threshold, but that just makes it very stuttery for some reason, and will not work. 

You can drag your finger on the screen to rotate the smaller triangles around the center.

As of writing this, it is pending publciation on the app store under the name "381Final" for anybody who wants to download it.
