package koinModule

import org.koin.core.context.startKoin
import org.koin.dsl.module
import repository.ApiRepository
import utils.DataHolder
import viewModels.PikaViewModel
import webApi.ApiClient

fun initKoin() = startKoin {
    modules(listOf(singleModule, factoryModule))
}

val singleModule = module {
    single { ApiClient() }
    single { DataHolder() }

}
val factoryModule = module {
    factory { PikaViewModel(ApiRepository(get()),get()) }
}