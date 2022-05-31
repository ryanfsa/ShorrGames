package com.example.a3d.flyingShorrs

import android.app.Activity
import android.content.Context
import android.opengl.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.example.a3d.R
import io.github.controlwear.virtual.joystick.android.JoystickView
import javax.microedition.khronos.opengles.GL10
class FlyingMainActivity : Activity() {

    lateinit var joyStick : JoystickView
    private lateinit var gLView : MyGLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flying_main)
        gLView = findViewById(R.id.myGLSurfaceView)
        joyStick = findViewById(R.id.view2)
        var speed : TextView = findViewById(R.id.textView)
        var altitude : TextView = findViewById(R.id.textView2)
        var targetsHit : TextView = findViewById(R.id.textView3)
        var homeButton : ImageView = findViewById(R.id.imageView)
        homeButton.setOnClickListener {
            finish()
        }
        joyStick.setOnMoveListener { angle, strength ->
            var a2 : Double = angle * Math.PI/180f
            gLView.renderer.p.dY = -(strength * Math.sin(a2)).toFloat()
            gLView.renderer.p.dX = (strength * Math.cos(a2)).toFloat()
        }
        var p = gLView.renderer.p
        fun repeatUpdate(){
            speed.text = " " + String.format("%.2f",p.magnitude(p.vel)) + " m/s "
            altitude.text = " " + String.format("%.2f", p.pos[1] + 5f) + " m "
            var numHit = 0
            gLView.renderer.targets.forEach{
                if(it.done) numHit++
            }
            targetsHit.text = "$numHit / 6"
            Handler(Looper.getMainLooper()).postDelayed({
                repeatUpdate()
            },200)
        }
        repeatUpdate()
    }

}
class MyGLSurfaceView(context: Context, attrs : AttributeSet) : GLSurfaceView(context,attrs) {
    val renderer : MyGLRenderer
    init {
        setEGLContextClientVersion(2)
        renderer = MyGLRenderer()
        setRenderer(renderer)
    }
}
class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var triArray : Array<Tri>
    private lateinit var planeArray : Array<Tri>
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    var p = Player(this)
    var targets : List<target> = listOf()

    override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        GLES20.glClearColor(0.53f, 0.81f, 0.92f, 1.0f)
        for(i in 0..5){
            targets += target(floatArrayOf((Math.random()*160f-80f).toFloat(),(Math.random()*20f+20f).toFloat(),(Math.random()*160f-80f).toFloat()))
        }
        triArray = getTriArray()
        planeArray = getPlane()
        targets.forEach{
            it.targetArray = getTarget()
        }
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
    }
    override fun onDrawFrame(p0: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        p.update()
        Matrix.setLookAtM(viewMatrix, 0, p.cam.pos[0],p.cam.pos[1],p.cam.pos[2],p.pos[0],p.pos[1],p.pos[2], 0f, 1f, 0f)
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        triArray.forEach{
            it.draw(vPMatrix)
        }
        var scratch = FloatArray(16)
        for(t : target in targets){
            scratch = FloatArray(16)
            Matrix.multiplyMM(scratch,0,vPMatrix,0,t.mModelMatrix,0)
            t.targetArray.forEach {
                it.draw(scratch)
            }
        }
        Matrix.multiplyMM(scratch,0,vPMatrix,0,p.mModelMatrix,0)
        planeArray.forEach {
            it.draw(scratch)
        }
    }
    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        GLES20.glViewport(0,0,p1,p2)
        val ratio: Float = p1.toFloat() / p2.toFloat()
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 1000f)
    }
}
const val COORDS_PER_VERTEX = 3
var triangleCoords = floatArrayOf(     // in counterclockwise order:
    0.0f, 0.622008459f, 0.0f,      // top
    -0.5f, -0.311004243f, 3.0f,    // bottom left
    0.5f, -0.311004243f, 0.0f      // bottom right
)

