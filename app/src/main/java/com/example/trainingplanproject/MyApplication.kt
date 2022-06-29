package com.example.trainingplanproject

import android.app.Application
import androidx.room.Room
import com.example.trainingplanproject.db.AppDatabase
import com.example.trainingplanproject.network.ApiService
import com.example.trainingplanproject.network.OkHttpClientProvider
import com.example.trainingplanproject.ui.pixabay.repository.PixabayRepository
import com.example.trainingplanproject.ui.pixabay.viewmodel.PixabayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(viewModelModules, apiModules, repositoryModules, databaseModules))
        }
    }

    private val apiModules: Module = module {
        single { get<OkHttpClientProvider>().createOkHttpClient() }
        single { get<ApiService>().createPixabayService() }
        single { ApiService(get()) }
        single { OkHttpClientProvider() }
    }

    private val repositoryModules = module {
        single { PixabayRepository(get(), get()) }
    }

    private val viewModelModules = module {
        viewModel { PixabayViewModel(get(), get()) }
    }

    private val databaseModules: Module = module {
        single { AppDatabase.instance }
        single { get<AppDatabase>().searchHistoryDao() }
    }

}