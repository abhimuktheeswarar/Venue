package com.msabhi.venue

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Checkable
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import kotlin.math.max

/**
 * Created by Abhi Muktheeswarar on 17-October-2021.
 */

class CircularRevealAnimatedButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs), Checkable {

    private val placeHolderView: View
    val textView: TextView

    private var checked: Boolean = false

    private val animationDuration: Long

    private lateinit var textPair: Pair<String, String>

    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    private val overshootAnimation by lazy {
        AnimationUtils.loadAnimation(context,
            R.anim.overshoot_animation)
    }

    init {

        inflate(context, R.layout.custom_view_animated_button, this)
        placeHolderView = findViewById(R.id.view_cvab)
        textView = findViewById(R.id.text_cvab)

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.CircularRevealAnimatedButton)

        minimumWidth =
            attributes.getDimension(R.styleable.CircularRevealAnimatedButton_android_minWidth, 120f)
                .toInt()
        placeHolderView.minimumWidth =
            attributes.getDimension(R.styleable.CircularRevealAnimatedButton_android_minWidth, 120f)
                .toInt()
        textView.minimumWidth =
            attributes.getDimension(R.styleable.CircularRevealAnimatedButton_android_minWidth, 120f)
                .toInt() - 24.toPx()

        background = attributes.getDrawable(R.styleable.CircularRevealAnimatedButton_strokeDrawable)
        placeHolderView.background =
            attributes.getDrawable(R.styleable.CircularRevealAnimatedButton_solidDrawable)

        textView.setTextColor(attributes.getColor(R.styleable.CircularRevealAnimatedButton_android_textColor,
            Color.BLACK))

        animationDuration =
            attributes.getInt(R.styleable.CircularRevealAnimatedButton_android_animationDuration,
                400)
                .toLong()

        checked = attributes.getBoolean(R.styleable.CircularRevealAnimatedButton_checked, false)

        val text0 = attributes.getString(R.styleable.CircularRevealAnimatedButton_textUnChecked)
        val text1 = attributes.getString(R.styleable.CircularRevealAnimatedButton_textChecked)

        attributes.recycle()

        if (text0 != null && text1 != null) {
            textPair = Pair(text0, text1)
            textView.text = if (checked) {
                text1
            } else {
                text0
            }
        }

        if (checked) {
            placeHolderView.visibility = View.INVISIBLE
        } else {
            placeHolderView.visibility = View.VISIBLE
        }

        setOnClickListener {
            toggle()
        }
    }

    override fun setChecked(checked: Boolean) {
        this.checked = checked
        runAnimation()
        onCheckedChangeListener?.onCheckedChanged(checked)
    }

    override fun isChecked(): Boolean = checked

    override fun toggle() {
        isChecked = !checked
    }

    fun setStrokeDrawable(@DrawableRes id: Int) {
        background = ContextCompat.getDrawable(context, id)
    }

    fun setSolidDrawable(@DrawableRes id: Int) {
        placeHolderView.background = ContextCompat.getDrawable(context, id)
    }

    fun setTextPair(textPair: Pair<String, String>) {
        this.textPair = textPair
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        onCheckedChangeListener = listener
    }

    private fun runAnimation() {
        startAnimation(overshootAnimation)
        if (checked) {
            textView.text = textPair.second
            textView.post {
                val initialRadius = placeHolderView.width / 2
                val animation = ViewAnimationUtils.createCircularReveal(placeHolderView,
                    placeHolderView.measuredWidth / 2,
                    placeHolderView.measuredHeight / 2,
                    initialRadius.toFloat(),
                    0f)
                animation.interpolator = AccelerateDecelerateInterpolator()
                animation.doOnEnd {
                    placeHolderView.visibility = View.INVISIBLE
                }
                animation.duration = animationDuration
                animation.start()
            }
        } else {
            textView.text = textPair.first
            textView.post {
                val finalRadius = max(placeHolderView.width, placeHolderView.height) / 2
                val animation = ViewAnimationUtils.createCircularReveal(placeHolderView,
                    placeHolderView.measuredWidth / 2,
                    placeHolderView.measuredHeight / 2,
                    0f,
                    finalRadius.toFloat())
                animation.interpolator = AccelerateDecelerateInterpolator()
                animation.duration = animationDuration
                placeHolderView.visibility = View.VISIBLE
                animation.start()
            }
        }
    }

    fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    interface OnCheckedChangeListener {

        fun onCheckedChanged(checked: Boolean)
    }
}