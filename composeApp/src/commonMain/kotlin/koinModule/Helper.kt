package koinModule

import org.koin.core.context.startKoin
import org.koin.dsl.module
import repository.ApiRepository
import viewModels.PikaViewModel
import webApi.ApiClient

fun initKoin() = startKoin{
    modules(listOf(singleModule, factoryModule))
}

 val singleModule = module {
    single { ApiClient() }

}
 val factoryModule = module {
    factory { PikaViewModel(ApiRepository(get())) }
}