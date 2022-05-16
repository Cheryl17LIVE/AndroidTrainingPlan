package com.example.trainingplanproject

import android.app.Application
import com.example.trainingplanproject.ui.pixabay.PixabayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(viewModelModules))
        }
    }

    private val viewModelModules = module {
        viewModel { PixabayViewModel() }
    }

}