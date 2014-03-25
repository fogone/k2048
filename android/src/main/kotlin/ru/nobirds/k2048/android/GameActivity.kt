package ru.nobirds.k2048.android

import android.app.Activity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import ru.nobirds.k2048.GameModel
import android.widget.TextView
import java.util.HashMap
import java.awt.event.MouseAdapter
import android.view.GestureDetector
import android.view.View
import android.view.MotionEvent
import ru.nobirds.k2048.TurnType
import android.widget.LinearLayout
import android.widget.FrameLayout
import android.view.ViewGroup

public class GameActivity : Activity() {

    private val model = GameModel(4)

    private val views = HashMap<String, TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        val table = findViewById(R.id.table) as TableLayout

        val field = model.field

        model.putRandom()
        model.putRandom()

        field.indices.forEach { x ->
            val row = TableRow(this)
            /*row.setLayoutParams(TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            )*/

            field.indices.forEach { y ->
                val textView = TextView(this)

                textView.setLayoutParams(ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                )

                textView.setText(field[x, y].toString())

                views["$x,$y"] = textView

                row.addView(textView)
            }

            table.addView(row)
        }

        table.setOnTouchListener(object : SwipeTouchListener(this) {
            override fun onSwipeRight() {
                model.turn(TurnType.right)
                update()
            }
            override fun onSwipeLeft() {
                model.turn(TurnType.left)
                update()
            }
            override fun onSwipeTop() {
                model.turn(TurnType.up)
                update()
            }
            override fun onSwipeBottom() {
                model.turn(TurnType.down)
                update()
            }
        })
    }

    private fun update() {
        model.field.indices.forEach { x ->
            model.field.indices.forEach { y ->
                views["$x,$y"]!!.setText(model.field[x, y].toString())
            }
        }
    }

}