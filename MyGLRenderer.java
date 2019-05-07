package com.graphics.android.graphicsfinal;

import android.opengl.GLES20;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mCenterTriangle;
    private Triangle mTriangle;
    private Triangle mTriangle1;
    private Triangle mTriangle2;
    private Triangle mTriangle3;
    private Triangle mTriangle4;
    private Triangle mTriangle5;
    private Square mSquare;
    private Cube mCube;
    private Pyramid mPyramid;
    private Circle mCirclefirst;
    private Circle secondCircle;
    private Circle thirdCircle;
    private Circle fourthCircle;

    public volatile float mAngle;
    public volatile float touchX;
    public volatile float touchY;

    public float squareScale = 0.5f;
    public float squareStartX = 0.0f;
    public float squareStartY = 0.0f;
    float curSquarePosX = squareStartX*-1;
    float curSquarePosY = squareStartY*-1;
    float squareBounds = 1.0f * squareScale;



    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        // initialize a triangle
        mCenterTriangle = new Triangle(0.0f, 0.0f, 0.6f);
        mTriangle = new Triangle(-0.5f, 0.5f, 0.25f);
        mTriangle1 = new Triangle(-0.5f, 0.0f, 0.25f);
        mTriangle2 = new Triangle(-0.5f, -0.5f, 0.25f);
        mTriangle3 = new Triangle(0.5f, 0.5f, 0.25f);
        mTriangle4 = new Triangle(0.5f, 0.0f, 0.25f);
        mTriangle5 = new Triangle(0.5f, -0.5f, 0.25f);
        mCirclefirst = new Circle(.45f, .45f, .005f, -.005f );
        secondCircle = new Circle(-.45f, -.45f, -.005f, .005f);
        thirdCircle = new Circle(-.45f, .45f, -.005f, .005f);
        fourthCircle = new Circle(.45f, -.45f, -.005f, .005f);


        // initialize a square
        mSquare = new Square(squareStartX, squareStartY, squareScale);
        // initialize a cube
        mCube = new Cube(0.0f, 0.0f, 0.0f);
        //mCube = new Cube();
        mPyramid = new Pyramid();

    }

    private float[] clickRotationMatrix = new float[16];
    private final float[] bounceMatrix = new float[16];
    private final float[] staticRotMatrix = new float[16];
    private final float[] touchMatrix = new float[16];


    @Override
    public void onDrawFrame(GL10 unused) {

        //Find the bounds of our square based on how much it's scaled.
 /*       curSquarePosX -= mSquare.deltaX;
        curSquarePosY -= mSquare.deltaY;

        if ((curSquarePosX-squareBounds <= -1) && !mSquare.bounceX){
            mSquare.bounceX = true;
            //curSquarePosX += mSquare.deltaX;

            //Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOOX" + curSquarePosX);
        }else if (mSquare.bounceX){
            //curSquarePosX -= mSquare.deltaX;

            if (curSquarePosX+squareBounds >= 1) {
                //Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOOX" + curSquarePosX);

                mSquare.bounceX = false;
            }
        }


        if ((curSquarePosY+squareBounds <= -1) && !mSquare.bounceY){
            mSquare.bounceY = true;
            //curSquarePosY += mSquare.deltaY;

            //Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOOY" + curSquarePosY);
        }else if (mSquare.bounceY){

            //curSquarePosY -= mSquare.deltaY;

            if (curSquarePosY-squareBounds >= 1) {
                //Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOOY" + curSquarePosY);

                mSquare.bounceY = false;
            }
        }
*/

        float[] staticRotScratch = new float[16];
        float[] clickRotScratch = new float[16];
        float[] bounceScratch = new float[16];
        float[] touchScratch = new float[16];

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float curPosX = secondCircle.radius*-1*2;
        //curPos -= secondCircle.center_X;

        curPosX -= secondCircle.deltaX;

        if ((curPosX <= -1) && !secondCircle.bounceX){
            secondCircle.bounceX = true;
            Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOO");
            secondCircle.bounceCount++;
        }else if (secondCircle.bounceX){

            if (curPosX >= 0) {
                secondCircle.bounceCount++;
                secondCircle.bounceX = false;
            }
        }

        float curPosY = secondCircle.radius*-1*2;
        curPosY -= secondCircle.deltaY;

        if ((curPosY <= -1) && !secondCircle.bounceY){
            secondCircle.bounceY = true;
            secondCircle.bounceCount++;
            Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOO");
        }else if (secondCircle.bounceY){

            if (curPosY >= 0) {
                secondCircle.bounceCount++;

                secondCircle.bounceY = false;
            }
        }

        //What moves our objects around.
        Matrix.setIdentityM(bounceMatrix, 0);
        Matrix.translateM(bounceMatrix, 0, secondCircle.deltaX, secondCircle.deltaY, 0);
        Matrix.setIdentityM(touchMatrix, 0);
        Matrix.translateM(touchMatrix, 0, touchX, touchY, 0);
        Log.e("DING!!!!!", "WOOOOOOOOOOOOOOOOOOOOOX" + touchX + " Y: " + touchY);



        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(staticRotMatrix, 0, angle, 0, 0, -1.0f);
        Matrix.setRotateM(clickRotationMatrix, 0, mAngle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(clickRotScratch, 0, vPMatrix, 0, clickRotationMatrix, 0);
        Matrix.multiplyMM(staticRotScratch, 0, vPMatrix, 0, staticRotMatrix, 0);


        Matrix.multiplyMM(bounceScratch, 0, vPMatrix, 0, bounceMatrix, 0);
        Matrix.multiplyMM(touchScratch, 0, vPMatrix, 0, touchMatrix, 0);



        // Draw shape
        mSquare.draw(bounceScratch);

        mCenterTriangle.draw(staticRotScratch);
        mTriangle.draw(clickRotScratch);
        mTriangle1.draw(clickRotScratch);
        mTriangle2.draw(clickRotScratch);
        mTriangle3.draw(clickRotScratch);
        mTriangle4.draw(clickRotScratch);
        mTriangle5.draw(clickRotScratch);
        secondCircle.draw(bounceScratch);
        mCirclefirst.draw(bounceScratch);
        thirdCircle.draw(bounceScratch);
        fourthCircle.draw(bounceScratch);

/*
        if (secondCircle.deltaX >= .2f) {
            secondCircle.deltaX = .005f;
        }
        if (secondCircle.deltaY >= .2f) {
            secondCircle.deltaY = .005f;
        }
        */
        if (secondCircle.bounceX){
            secondCircle.deltaX -= (.005f + (secondCircle.bounceCount/1000));
        }else{
            secondCircle.deltaX += (.005f + (secondCircle.bounceCount/1000));
        }


        if (secondCircle.bounceY){
            secondCircle.deltaY -= (.002f + (secondCircle.bounceCount/1000));
        }else{
            secondCircle.deltaY += (.002f + (secondCircle.bounceCount/1000));

        }

        //mCube.draw(scratch);
        //mPyramid.draw(scratch);

/*
        if (mSquare.bounceX)
        {
            mSquare.deltaX -=.005f;
            //curSquarePosX -= mSquare.deltaX;

        }
        else
        {
            mSquare.deltaX += .005f;
            //curSquarePosX += mSquare.deltaX;

        }
        if (mSquare.bounceY)
        {
            mSquare.deltaY -=.015f;
            //curSquarePosY -= mSquare.deltaY;

        }
        else
        {
            mSquare.deltaY += .015f;
            //curSquarePosY += mSquare.deltaY;

        }

*/

    }


    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public void setTouchX(float x){
        touchX = x;
    }
    public float getTouchX(){
        return touchX;
    }
    public void setTouchY(float y){
        touchY = y;
    }
    public float getTouchY(){
        return touchY;
    }


}
