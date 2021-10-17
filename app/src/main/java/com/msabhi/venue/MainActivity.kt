package com.msabhi.venue

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.button.MaterialButton
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val materialButton = findViewById<MaterialButton>(R.id.button_0)
        val appCompatButton = findViewById<AppCompatButton>(R.id.button_1)
        val textView = findViewById<TextView>(R.id.text_0)
        val view0 = findViewById<View>(R.id.view_0)
        val view1 = findViewById<View>(R.id.view_1)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout_0)
        val textView1 = findViewById<TextView>(R.id.text_i0)
        val view2 = findViewById<View>(R.id.view_i1)

        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation)
        //val interpolator = BounceInterpolator()
        //interpolator.fre

        constraintLayout.setOnClickListener {

            it.startAnimation(bounceAnimation)

            println("constraintLayout clicked")

            //val fr = hypot(it.width.toDouble(), it.height.toDouble()).toFloat()

            if (isChecked) {
                textView1.text = "Following"
                textView1.post {
                    val initialRadius = view2.width / 2
                    val animation = ViewAnimationUtils.createCircularReveal(view2,
                        view2.measuredWidth / 2,
                        view2.measuredHeight / 2,
                        initialRadius.toFloat(),
                        0f)
                    animation.interpolator = AccelerateDecelerateInterpolator()
                    animation.doOnEnd {
                        view2.visibility = View.INVISIBLE
                    }
                    animation.duration = 400
                    animation.start()
                }
            } else {
                textView1.text = "Follow"
                textView1.post {
                    val finalRadius = max(view2.width, view2.height) / 2
                    val animation = ViewAnimationUtils.createCircularReveal(view2,
                        view2.measuredWidth / 2,
                        view2.measuredHeight / 2,
                        0f,
                        finalRadius.toFloat())
                    animation.interpolator = AccelerateDecelerateInterpolator()
                    animation.duration = 400
                    view2.visibility = View.VISIBLE
                    animation.start()
                }
            }
            isChecked = !isChecked
        }

        view1.setOnClickListener { view ->
            if (isChecked) {
                val animator =
                    ValueAnimator.ofObject(ArgbEvaluator(), Color.GRAY, Color.CYAN).apply {
                        duration = 2000
                        addUpdateListener {
                            if (it.animatedValue is Int) {
                                val color = it.animatedValue as Int
                                view.setBackgroundColor(color)
                            }
                        }
                    }

                animator.start()
            } else {
                val animator =
                    ValueAnimator.ofObject(ArgbEvaluator(), Color.CYAN, Color.GRAY).apply {
                        duration = 2000
                        addUpdateListener {
                            if (it.animatedValue is Int) {
                                val color = it.animatedValue as Int
                                view.setBackgroundColor(color)
                            }
                        }
                    }

                animator.start()
            }
            isChecked = !isChecked
        }

        val followLottieDrawable = LottieDrawable().apply {
            LottieCompositionFactory.fromRawRes(this@MainActivity, R.raw.lottie_follow)
                .addListener {
                    composition = it
                }
        }

        val unfollowLottieDrawable = LottieDrawable().apply {
            LottieCompositionFactory.fromRawRes(this@MainActivity, R.raw.lottie_unfollow)
                .addListener {
                    composition = it
                }
        }



        followLottieDrawable.addAnimatorUpdateListener {

            it.doOnEnd {
                //followLottieDrawable.reverseAnimationSpeed()
                println("doOnEnd")
                followLottieDrawable.speed = -1f
            }
        }

        /*unfollowLottieDrawable.addAnimatorUpdateListener {
            it.doOnEnd {
                textView.background = unfollowLottieDrawable
            }
        }*/

        var isRevered = false

        if (!isChecked) {
            //followLottieDrawable.frame = followLottieDrawable.maxFrame.toInt()
            //followLottieDrawable.reverseAnimationSpeed()
            //followLottieDrawable.speed = -1f
            isRevered = true
        }



        textView.background = followLottieDrawable

        val startFrame = followLottieDrawable.minFrame
        val endFrame = followLottieDrawable.maxFrame

        //var isPlayed = true
        //followLottieDrawable.reverseAnimationSpeed()

        textView.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                textView.text = "FOLLOWING"
                followLottieDrawable.playAnimation()
            } else {
                textView.text = "FOLLOW"
                followLottieDrawable.playAnimation()
            }
            //lottieDrawable.progress = 1f
        }

        // CircularRevealWidget.CircularRevealProperty.

        //CircularRevealWidget.CircularRevealEvaluator()

        //val rippleDrawable = ContextCompat.getDrawable(this, R.drawable.ripple_0) as RippleDrawable
        //rippleDrawable.setHotspot(0f, 0f)

        val gradientDrawable = view1.background as GradientDrawable

        //val shapeDrawable = view1.background as ShapeDrawable

        val clipDrawable =
            ClipDrawable(ContextCompat.getDrawable(this, R.drawable.bg_0), Gravity.CENTER, 1)


        //ValueAnimator.ofObject()


        view1.setOnClickListener { view ->

            if (isChecked) {
                val animator =
                    ValueAnimator.ofFloat(2.toPx().toFloat(), 24.toPx().toFloat()).apply {
                        duration = 2000
                        addUpdateListener {
                            gradientDrawable.cornerRadius = it.animatedValue as Float
                        }
                    }
                animator.start()
            } else {
                val animator =
                    ValueAnimator.ofFloat(24.toPx().toFloat(), 2.toPx().toFloat()).apply {
                        duration = 2000
                        addUpdateListener {
                            gradientDrawable.cornerRadius = it.animatedValue as Float
                        }
                    }
                animator.start()
            }
            isChecked = !isChecked
        }
    }
}

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()