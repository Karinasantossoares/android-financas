package com.example.desafioandroidmobills.extension

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher

@SuppressLint("ClickableViewAccessibility")
fun EditText.setOnClickLeft(onClick: () -> Unit) {
    setOnTouchListener { _, event ->
        if (event.x < this.totalPaddingLeft && event.action == MotionEvent.ACTION_UP) {
            onClick.invoke()
            return@setOnTouchListener true
        }
        return@setOnTouchListener false
    }
}

fun EditText.addMask(mask:String){
    val smf = SimpleMaskFormatter(mask)
    val mtw = MaskTextWatcher(this, smf)
    addTextChangedListener(mtw)
}