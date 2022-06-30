package com.example.trainingplanproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.example.trainingplanproject.R
import com.example.trainingplanproject.databinding.ActivityMainBinding
import com.example.trainingplanproject.ui.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}
