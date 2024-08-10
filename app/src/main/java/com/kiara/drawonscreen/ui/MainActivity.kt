package com.kiara.drawonscreen.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiara.drawonscreen.databinding.MainActivityBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val drawView = binding.drawingView
//        drawView.changeBrushSize(20.toFloat())
    }

}