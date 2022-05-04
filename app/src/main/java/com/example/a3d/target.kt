package com.example.a3d

import android.opengl.Matrix

class target(var pos : FloatArray) {
    lateinit var targetArray : Array<Tri>
    var done = false
    var mModelMatrix = FloatArray(16)
    init {
        Matrix.setIdentityM(mModelMatrix,0)
        Matrix.translateM(mModelMatrix,0,pos[0],pos[1],pos[2])
    }
    fun hitTarget(){
        done = true
        targetArray.forEach {
            it.color = floatArrayOf(0.2f,1f,0.2f,1f)
        }
    }
}