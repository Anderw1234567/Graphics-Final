package com.graphics.android.graphicsfinal;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Circle {

    public final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 uMVPMatrix;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    public final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    int mProgram;


    int vertexCount = 100;
    float radius = .25f;
    float deltaX = .05f;
    float deltaY = .05f;
    float center_X;
    float center_Y;
    boolean bounceX = false;
    boolean bounceY = false;
    float bounceCount = 0;

    float alpha = 0.0f;


    FloatBuffer mVertexBuffer;
    float vertices[] = new float[364 * 3]; // (x,y,alpha) for each vertex

    public Circle(){
        center_X = 0;
        center_Y = 0;
        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 0;

        for(int i =1; i <364; i++){
            vertices[(i * 3)+ 0] = (float) (radius * Math.cos((3.14/180) * (float)i ));
            vertices[(i * 3)+ 1] = (float) (radius * Math.sin((3.14/180) * (float)i ));
            vertices[(i * 3)+ 2] = 0;
        }


        Log.v("Thread",""+vertices[49]+","+vertices[50]+","+vertices[51]);
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vertexByteBuffer.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);

    }

    Circle(float centerX, float centerY, float deltX, float deltY){
        deltaX = deltX;
        deltaY = deltY;
        center_X = centerX;
        center_Y = centerY;
        vertices[0] = centerX;
        vertices[1] = centerY;
        vertices[2] = 0;

        for(int i =1; i <364; i++){
            vertices[(i * 3)+ 0] = (float) centerX + (float) (radius * Math.cos((3.14/180) * (float)i ));
            vertices[(i * 3)+ 1] = (float) centerY + (float) (radius * Math.sin((3.14/180) * (float)i ));
            vertices[(i * 3)+ 2] = 0;
        }


        Log.v("Thread",""+vertices[49]+","+vertices[50]+","+vertices[51]);
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vertexByteBuffer.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        //GLES20.glUseProgram(mProgram);
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);

    }


    private final int vertexStride = 12;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int mCenterHandle;

    float[] tempCent = {.05f, .05f};


    public void draw(float[] mvpMatrix) {

        //tempCent[0] += deltaX;
        //tempCent[1] += deltaY;

        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, vertexStride, mVertexBuffer);

        //Log.e("length: ", Integer.toString(tempCent.length));
        //mCenterHandle = GLES20.glGetUniformLocation(mProgram, "centerVec");

        //GLES20.glUniform2fv(mCenterHandle, 1, tempCent, 0);


        //We may also need to build a whole float array for colors as well to use a triangle fan.

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        float color[] = {.5f, .9f, .5f, .0f};
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);





        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 364);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }



}
