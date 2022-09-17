package com.beans.likeanimationproject

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.beans.likeanimationproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    private var mAnimationIndex = 0
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val  mGestureDetector = GestureDetector(this)
        mGestureDetector.setOnDoubleTapListener(this)
        binding.root.setOnTouchListener { v, event ->
            mGestureDetector.onTouchEvent(event)
        }


    }

    override fun onDown(e: MotionEvent?): Boolean = true

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean  = false

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean=false

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean  = false

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean  = false
    override fun onDoubleTap(e: MotionEvent): Boolean {
        doubleClickAnimation(binding.fragmentLayout,mAnimationIndex,e.rawX,e.rawY)
        mAnimationIndex++
       return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {

       return true
    }

    //双击关注动画
    private fun doubleClickAnimation(frameLayout: FrameLayout, index: Int, x: Float, y: Float) {
        val lottieAnimationView = LottieAnimationView(this)
        val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            dp2px(this, 210),
            dp2px(this, 394)
        )
        lottieAnimationView.layoutParams = layoutParams
        lottieAnimationView.id = index
        lottieAnimationView.progress = 0f
        LottieCompositionFactory.fromRawRes(this, R.raw.data).addListener { result ->
            lottieAnimationView.setComposition(
                result


            )
        }
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                lottieAnimationView.cancelAnimation()
                lottieAnimationView.removeAllAnimatorListeners()
                frameLayout.removeView(lottieAnimationView)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        lottieAnimationView.playAnimation()
        frameLayout.addView(lottieAnimationView)
        lottieAnimationView.translationX = (x - dp2px(this, 80))
        lottieAnimationView.translationY = (y - dp2px(this, 200))
    }
    private fun dp2px(context: Context, dp:Int) = (context.resources.displayMetrics.density*dp+0.5).toInt()
}