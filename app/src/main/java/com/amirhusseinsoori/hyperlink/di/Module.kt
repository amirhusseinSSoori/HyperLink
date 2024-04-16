package com.amirhusseinsoori.hyperlink.di

import com.amirhusseinsoori.hyperlink.HyperRepository
import com.amirhusseinsoori.hyperlink.HyperRepositoryImp
import com.amirhusseinsoori.hyperlink.HyperViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module


val appModule = module {
    single<HyperRepository> {HyperRepositoryImp() }
    viewModel { HyperViewModel(get()) }
}