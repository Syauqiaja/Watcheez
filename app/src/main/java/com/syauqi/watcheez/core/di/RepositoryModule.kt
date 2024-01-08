package com.syauqi.watcheez.core.di

import com.syauqi.watcheez.core.data.PeopleRepository
import com.syauqi.watcheez.domain.repository.IPeopleRepository
import com.syauqi.watcheez.domain.usecase.PeopleInteractor
import com.syauqi.watcheez.domain.usecase.PeopleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsUseCase(peopleInteractor: PeopleInteractor): PeopleUseCase

    @Binds
    abstract fun bindsRepository(peopleRepository: PeopleRepository): IPeopleRepository
}