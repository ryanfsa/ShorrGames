package com.example.a3d.flyingShorrs

class World {
}

fun getTriArray(): Array<Tri>{
    var result : Array<Tri> = Array(0){ Tri(triangleCoords,floatArrayOf(1f,0f,0f,1f)) }
    result += Tri(floatArrayOf(-5000f,-6f,-5000f , -5000f,-6f,5000f , 5000f,-6f,5000f), floatArrayOf(0f,0f,0.5f,1f))
    result += Tri(floatArrayOf(-5000f,-6f,-5000f ,  5000f,-6f,-5000f, 5000f,-6f,5000f), floatArrayOf(0f,0f,0.5f,1f))
    var heights = Array(12) {FloatArray(12)}
    for(x in -5..6){
        for(z in -5..6){
            heights[x+5][z+5] = Math.random().toFloat() * 0f - 5f
        }
    }
    for(x in -5..5){
        for(z in -5..5){
            if(Math.abs(x) != 5 && Math.abs(z) != 5){
            result += Tri(floatArrayOf(x*20f,heights[x+5][z+5],z*20f , (x+1f)*20f,heights[x+6][z+6],(z+1f)*20f , x*20f,heights[x+5][z+6],(z+1f)*20f), floatArrayOf(0.3f,0.8f,0.3f,1f))
            result += Tri(floatArrayOf(x*20f,heights[x+5][z+5],z*20f , (x+1f)*20f,heights[x+6][z+5],z*20f , (x+1f)*20f,heights[x+6][z+6],(z+1f)*20f), floatArrayOf(0.2f,0.6f,0.2f,1f))
            }
            else{
                result += Tri(floatArrayOf(x*20f,heights[x+5][z+5],z*20f , (x+1f)*20f,heights[x+6][z+6],(z+1f)*20f , x*20f,heights[x+5][z+6],(z+1f)*20f), floatArrayOf(0.76f,0.69f,0.50f,1f))
                result += Tri(floatArrayOf(x*20f,heights[x+5][z+5],z*20f , (x+1f)*20f,heights[x+6][z+5],z*20f , (x+1f)*20f,heights[x+6][z+6],(z+1f)*20f), floatArrayOf(0.76f,0.69f,0.50f,1f))
            }
        }
    }
    //result += Tri(floatArrayOf(-100f,-1f,-100f , 100f,-1f,100f, 100f,-1f,-100f),floatArrayOf(0.3f,0.8f,0.3f,1f))
    //result += Tri(floatArrayOf(-100f,-1f,-100f , -100f,-1f,100f, 100f,-1f,100f),floatArrayOf(0.3f,0.8f,0.3f,1f))
    for(i in 1..8){
        var posX = Math.random().toFloat() * 160f - 80f
        var posZ = Math.random().toFloat() * 160f - 80f
        var gray = floatArrayOf(0.6f,0.6f,0.6f,1f)
        result += Tri(floatArrayOf(posX+10f,-5f,posZ+10f , posX,7f,posZ , posX+10f,-5f,posZ-10f),gray)
        result += Tri(floatArrayOf(posX+10f,-5f,posZ+10f , posX,7f,posZ , posX-10f,-5f,posZ+10f),gray)
        result += Tri(floatArrayOf(posX-10f,-5f,posZ-10f , posX,7f,posZ , posX+10f,-5f,posZ-10f),gray)
        result += Tri(floatArrayOf(posX-10f,-5f,posZ-10f , posX,7f,posZ , posX-10f,-5f,posZ+10f),gray)
    }

    return result
}
fun getPlane(): Array<Tri>{
    var result : Array<Tri> = Array(0){ Tri(triangleCoords,floatArrayOf(1f,0f,0f,1f)) }
    var blue = floatArrayOf(0.3f,0.3f,0.6f,1f)
    var gray = floatArrayOf(0.5f,0.5f,0.5f,1f)
    result += Tri(floatArrayOf(0f,0.5f,0f , 0f,0f,1f , 0.5f,0f,0f), gray)
    result += Tri(floatArrayOf(0f,0.5f,0f , 0f,0f,1f , -0.5f,0f,0f), gray)
    result += Tri(floatArrayOf(0f,0.5f,0f , 0f,0f,-3f , 0.5f,0f,0f), blue)
    result += Tri(floatArrayOf(0f,0.5f,0f , 0f,0f,-3f , -0.5f,0f,0f), blue)
    result += Tri(floatArrayOf(0.5f,0f,0f , 0f,0f,-3f , -0.5f,0f,0f), blue)

    result += Tri(floatArrayOf(0.4f,0f,-0.5f ,  3f,0.3f,-2f ,  0.3f,0f,-1.2f), blue)
    result += Tri(floatArrayOf(-0.4f,0f,-0.5f, -3f,0.3f,-2f ,  -0.3f,0f,-1.2f), blue)

    result += Tri(floatArrayOf(0.05f,0f,-2.8f ,  1.5f,0.3f,-3.2f ,  0.15f,0f,-2.2f), gray)
    result += Tri(floatArrayOf(-0.05f,0f,-2.8f , -1.5f,0.3f,-3.2f , -0.15f,0f,-2.2f), gray)
    result += Tri(floatArrayOf(0f,0.05f,-2.8f ,  0f,0.8f,-2.6f    , 0f, 0.8f, -2.2f),gray)
    result += Tri(floatArrayOf(0f,0.05f,-2.8f ,  0f,0.1f,-1.6f    , 0f, 0.8f, -2.2f),gray)
    //result += Tri(floatArrayOf(0f,0f,-3.2f ,  1.5f,0f,-3.2f , 0f,0f,-3f), floatArrayOf(0.5f,0.5f,0.5f,1f))
    //result += Tri(floatArrayOf(0f,0f,-3.2f , -1.5f,0f,-3.2f , 0f,0f,-3f), floatArrayOf(0.5f,0.5f,0.5f,1f))






    //result += Tri(floatArrayOf(0f,0f,0f , -3f,0f,1f , 0f,0f,2f), floatArrayOf(0f,1f,0f,1f))
    //result += Tri(floatArrayOf(0f,0f,0f ,  3f,0f,1f , 0f,0f,2f), floatArrayOf(1f,1f,0f,1f))
    //result += Tri(floatArrayOf(-1f,0f,1f, 0f,0f,-3f , 1f,0f,1f), floatArrayOf(1f,1f,1f,1f))
    return result
}
fun getTarget(): Array<Tri>{
    var result : Array<Tri> = Array(0){ Tri(triangleCoords,floatArrayOf(1f,0f,0f,1f)) }
    var red = floatArrayOf(0.7f,0.2f,0.2f,1f)
    for(angle in 0..7){
        result += Tri(floatArrayOf(Math.cos(angle*0.7854).toFloat() * 4f,Math.sin(angle*0.7854).toFloat() * 4f,0f
            , Math.cos(angle*0.7854+0.7854).toFloat()*4f,Math.sin(angle*0.7854+0.7854).toFloat()*4f,0f
            , 0f,0f,0f),red)
    }

    return result
}