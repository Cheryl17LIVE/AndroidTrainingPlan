package com.example.trainingplanproject

import android.app.Application
import androidx.room.Room
import com.example.trainingplanproject.db.AppDatabase
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
        viewModel { PixabayViewModel(get()) }
    }

    private val databaseModules: Module = module {
        single { AppDatabase.instance }
        single { get<AppDatabase>().searchHistoryDao() }
//        single { Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "AppDatabase.db")
//            .build() }
    }

//    private val databaseModules: Module = module {
//        single { get<AppDatabase>().searchWordDao() }
//        single { AppDatabase.instance }
//    }
}