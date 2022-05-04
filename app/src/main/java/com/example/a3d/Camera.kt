package com.example.a3d

class Camera(var p : Player){
    var pos : FloatArray = floatArrayOf(10f,5f,0f)
    var vel : FloatArray = floatArrayOf(0f,0f,0f)

    fun update(){
        pos = p.addV(pos,vel)
        vel = p.multV(vel,0.8f)
        var d : FloatArray = floatArrayOf(p.pos[0]-pos[0],p.pos[1]-pos[1],p.pos[2]-pos[2])
        d = p.addV(d, p.l2w(floatArrayOf(0f,1f,-3f)))
        var distSqr = d[0]*d[0]+d[1]*d[1]+d[2]*d[2]
        distSqr = distSqr/10000f
        vel = p.addV(vel,p.multV(d,distSqr))
        //pos = p.addV(pos,p.multV(d,distSqr))
    }
}