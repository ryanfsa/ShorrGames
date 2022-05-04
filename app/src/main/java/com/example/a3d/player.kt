package com.example.a3d

import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log

class Player(var rend : MyGLRenderer) {
    val lift = 0.8f
    val xDrag = 2f
    val yDrag = -0.5f
    val zDrag = -0.1f
    val rotInertia = -10f
    var pos : FloatArray = floatArrayOf(0f,0f,0f)
    var vel : FloatArray = floatArrayOf(0f,0f,5f)
    var localVel : FloatArray = floatArrayOf(0f,0f,0f)
    var rot : FloatArray = floatArrayOf(0f,0f,0f)
    var rotV: FloatArray = floatArrayOf(0f,0f,0f)
    var dX : Float = 0f
    var dY : Float = 0f
    var mModelMatrix = FloatArray(16)
    var inv = FloatArray(16)
    var cam : Camera = Camera(this)
    private var lastTime : Long = SystemClock.elapsedRealtime()
    var fT : Float = 0f
    init {
        Matrix.setIdentityM(mModelMatrix,0)
    }
    fun update(){

        fT = (SystemClock.elapsedRealtime() - lastTime).toFloat()
        fT /= 1000f
        lastTime = SystemClock.elapsedRealtime()
        Log.d("MainActivity",magnitude(vel).toString())
        pos = addV(pos,multV(vel,fT))
        rot = addV(rot,rotV)

        localVel = w2l(vel)
        Matrix.translateM(mModelMatrix,0,localVel[0] * fT,localVel[1] * fT,localVel[2] * fT)
        var w = w2l(floatArrayOf(1f,0f,0f))
        Matrix.rotateM(mModelMatrix,0,rotV[0] * fT,1f,0f,0f)
        //Matrix.rotateM(mModelMatrix,0,rotV[1],0f,1f,0f)
        Matrix.rotateM(mModelMatrix,0,rotV[2] * fT,0f,0f,1f)

        //localVel[1] += 3f * fT
        if(pos[1] <= -5){pos[1] = -5f
            vel[1] = 0f
        }
        rotV[0] *= 0.9f
        rotV[1] *= 0.9f
        rotV[2] *= 0.9f
        //vel = addV(vel,l2w(floatArrayOf(0f,0f,0.1f)))
        forceAtPoint(l2w(floatArrayOf(0f,localVel[1]*yDrag,0f)), l2w(floatArrayOf(0f,0f,-0.2f)))
        //forceAtPoint(l2w(floatArrayOf(0f,0f,localVel[2]*zDrag)), l2w(floatArrayOf(0f,0f,-2f)))
        vel = addV(vel, floatArrayOf(0f,-1f*fT,0f))
        localVel = w2l(vel)
        localVel[0] *= 0.9f
        localVel[1] *= 0.8f
        localVel[2] *= 0.99f
        localVel[2] += 1.5f * fT
        vel = l2w(localVel)

        rend.targets.forEach{
            if(magnitude(addV(pos,multV(it.pos,-1f)))<5f) it.hitTarget()
        }

        var pitch = floatArrayOf(-dY/20f,0f,0f)
        pitch[2] += dX/20f
        //var roll = floatArrayOf(0f,0f,-dX/200f)
        rotV = addV(rotV,pitch)
        //forceAtPoint(floatArrayOf(0f,-2f,0f), floatArrayOf(0f,0f,1f))

        cam.update()
    }
    fun addV(a : FloatArray, b : FloatArray) : FloatArray{
        return floatArrayOf(a[0]+b[0],a[1]+b[1],a[2]+b[2])
    }
    fun multV(a : FloatArray, b : Float) : FloatArray{
        return floatArrayOf(a[0]*b,a[1]*b,a[2]*b)
    }
    fun magnitude(a : FloatArray) : Float{
        return Math.sqrt((a[0]*a[0]+a[1]*a[1]+a[2]*a[2]).toDouble()).toFloat()
    }
    fun normalize(a : FloatArray) : FloatArray{
        val sum : Float = Math.abs(a[0]) + Math.abs(a[1]) + Math.abs(a[2])
        return floatArrayOf(a[0]/sum,a[1]/sum,a[2]/sum)
    }
    fun addPos(a : FloatArray) : FloatArray{
        return a
    }
    fun l2w(v : FloatArray) : FloatArray{
        var world = floatArrayOf(0f,0f,0f,0f)
        Matrix.multiplyMV(world,0,mModelMatrix,0,v+0f,0)
        return floatArrayOf(world[0],world[1],world[2])
    }
    fun w2l(v : FloatArray) : FloatArray{
        var local = floatArrayOf(0f,0f,0f,0f)
        Matrix.invertM(inv,0,mModelMatrix,0)
        Matrix.multiplyMV(local,0,inv,0,v+0f,0)
        return floatArrayOf(local[0],local[1],local[2])
    }
    fun dotProd(a : FloatArray, b: FloatArray) : Float {
        return a[0]*b[0]+a[1]*b[1]+a[2]*b[2]
    }
    fun crossProd(a : FloatArray, b : FloatArray) : FloatArray {
        var res = floatArrayOf(0f,0f,0f)
        res[0] = a[1] * b[2] - a[2] * b[1]
        res[1] = a[2] * b[0] - a[0] * b[2]
        res[2] = a[0] * b[1] - a[1] * b[0]
        return res
    }
    fun forceAtPoint(f : FloatArray, p : FloatArray){
        vel = addV(vel,multV(f,fT))
        rotV = addV(rotV,multV(crossProd(f,p),rotInertia))
    }
}