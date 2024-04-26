package com.amirhusseinsoori.hyperlink.data.di


import com.amirhusseinsoori.hyperlink.HyperViewModel
import com.amirhusseinsoori.hyperlink.data.db.DatabaseDriverFactoryImp
import com.amirhusseinsoori.hyperlink.data.repository.HyperRepository
import com.amirhusseinsoori.hyperlink.data.repository.HyperRepositoryImp
import com.amirhusseinsoori.sqldeLightHyperLink.HyperLinkDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val dbModule = module {
    single {
        DatabaseDriverFactoryImp().createDriver(androidApplication())
    }
}

val appModule = module {
    single<HyperRepository> {
        HyperRepositoryImp(HyperLinkDatabase(get()))
    }
    viewModel { HyperViewModel(get()) }
}
