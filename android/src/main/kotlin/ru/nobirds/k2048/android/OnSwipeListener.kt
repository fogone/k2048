package ru.nobirds.k2048.android

import android.view.View.OnTouchListener
import android.view.GestureDetector
import android.content.Context
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View

class GestureListener(val listener: SwipeListener) : SimpleOnGestureListener() {

    private val SWIPE_THRESHOLD = 100;
    private val SWIPE_VELOCITY_THRESHOLD = 100;

    override fun onDown(e: MotionEvent): Boolean = true

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        val result = false

        val diffY = e2!!.getY() - e1!!.getY()
        val diffX = e2.getX() - e1.getX()

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
                if (diffX > 0) listener.onSwipeRight()
                else listener.onSwipeLeft()
        } else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD)
                if (diffY > 0) listener.onSwipeBottom()
                else listener.onSwipeTop()
        }

        return result;
    }

}

abstract class SwipeTouchListener(val context:Context) : OnTouchListener, SwipeListener {

    private val detector = GestureDetector(context, GestureListener(this))

    override fun onTouch(v: View?, event: MotionEvent): Boolean
            = detector.onTouchEvent(event)

}


public trait SwipeListener {

    fun onSwipeRight()

    fun onSwipeLeft()

    fun onSwipeTop()

    fun onSwipeBottom()

}