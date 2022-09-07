package com.example.sakana.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.sakana.R


class TouchWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs), View.OnClickListener {

    companion object {
        const val TAG = "TouchWidget"
        const val WIDTH_SIZE = 200
        const val HEIGHT_SIZE = 200
    }



    private var mSakanaImg: ImageView

    private var mSakanaBitmap: Bitmap

    private var mPaint: Paint

    private var mScreenWidth: Int = resources.displayMetrics.widthPixels

    private var mScreenHeight: Int = resources.displayMetrics.heightPixels

    private var mCurrentX = 0f

    private var mCurrentY = 0f

    private var mVelocityTracker: VelocityTracker? = null

    private val mOriginX: Int = mScreenWidth / 2

    private val mOriginY: Int = mScreenHeight / 2

    init {

        mSakanaImg = ImageView(context).apply {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.sakana))
            setBackgroundColor(R.color.white)
            x = mOriginX.toFloat()
            y = mOriginY.toFloat()
            this@TouchWidget.addView(this, WIDTH_SIZE, HEIGHT_SIZE)
        }

        mSakanaBitmap = BitmapFactory.decodeResource(resources, R.drawable.sakana)


        mPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.RED
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20f
        }


//        initListener()
    }

    private fun initListener() {
        mSakanaImg.setOnTouchListener { view, event ->
            val originX = mSakanaImg.x + mSakanaImg.width / 2
            val originY = mSakanaImg.y + mSakanaImg.height / 2
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain()
            }
            mVelocityTracker?.addMovement(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mCurrentX = event.x
                    mCurrentY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = mCurrentX - originX
                    val dy = mCurrentY - originY

                }
                MotionEvent.ACTION_UP -> {
                    mVelocityTracker?.computeCurrentVelocity(1000)
                    Log.e(
                        TAG,
                        "mVelocityTracker.x = ${mVelocityTracker?.xVelocity} mVelocityTracker.y = ${mVelocityTracker?.yVelocity}"
                    )
                }
            }

            super.onTouchEvent(event)
        }

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawBitmap(mSakanaBitmap,mCurrentX.toFloat(),mCurrentY.toFloat(),mPaint)
        mSakanaImg.x = mCurrentX - mSakanaImg.width / 2
        mSakanaImg.y = mCurrentY - mSakanaImg.height / 2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mCurrentX = event.x
                mCurrentY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = mCurrentX - mOriginX
                val dy = mCurrentY - mOriginY

                mCurrentX = event.x
                mCurrentY = event.y
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker?.computeCurrentVelocity(1000)
                Log.e(
                    TAG,
                    "mVelocityTracker.x = ${mVelocityTracker?.xVelocity} mVelocityTracker.y = ${mVelocityTracker?.yVelocity}"
                )
            }
        }
        invalidate()
        return true
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onClick(view: View) {
    }

}