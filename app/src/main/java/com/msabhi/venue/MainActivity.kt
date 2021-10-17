package com.msabhi.venue

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable

class MainActivity : AppCompatActivity() {

    var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<CircularRevealAnimatedButton>(R.id.button_0)

        button.setOnCheckedChangeListener(object :
            CircularRevealAnimatedButton.OnCheckedChangeListener {
            override fun onCheckedChanged(checked: Boolean) {
                println("onCheckedChanged = $checked")
            }
        })
    }

    private fun setupDemoView() {
        val view0 = findViewById<View>(R.id.view_0)
        val gradientDrawable = view0.background as GradientDrawable

        view0.setOnClickListener { view ->

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

    private fun setupLottie() {

        val textView = findViewById<TextView>(R.id.text_0)


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

    }
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()