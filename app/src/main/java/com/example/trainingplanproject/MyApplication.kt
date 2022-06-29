package com.example.trainingplanproject

import android.app.Application
import android.content.Context
import com.example.trainingplanproject.db.AppDatabase
import com.example.trainingplanproject.ui.pixabay.PixabayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    companion object {
        lateinit var myApplicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        myApplicationContext = applicationContext

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(viewModelModules, databaseModules))
        }
    }
    /*
    val apiModules: Module = module {
        single { get<OkHttpClientProvider>().createOkHttpClient() }
        single { get<IApiProvider>().createPixabyApi() }
        single<IApiProvider> { ApiProvider(get()) }
        single { OkHttpClientProvider() }
    }

    val repositoryModules = module {
        factory<IPixabayRepository> { PixabayRepository(get()) }
    }
*/
    private val viewModelModules = module {
        viewModel { PixabayViewModel() }
    }

    private val databaseModules: Module = module {
        single { get<AppDatabase>().searchHistoryDao() }
        single { AppDatabase.instance }
    }

}