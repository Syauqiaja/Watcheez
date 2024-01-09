package com.syauqi.watcheez.core.di

import com.syauqi.watcheez.core.data.repositories.MovieRepository
import com.syauqi.watcheez.core.data.repositories.PeopleRepository
import com.syauqi.watcheez.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.domain.movie.usecase.MovieInteractor
import com.syauqi.watcheez.domain.movie.usecase.MovieUseCase
import com.syauqi.watcheez.domain.people.repository.IPeopleRepository
import com.syauqi.watcheez.domain.people.usecase.PeopleInteractor
import com.syauqi.watcheez.domain.people.usecase.PeopleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsPeopleUseCase(peopleInteractor: PeopleInteractor): PeopleUseCase

    @Binds
    abstract fun bindsPeopleRepository(peopleRepository: PeopleRepository): IPeopleRepository

    @Binds
    abstract fun bindsMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    abstract fun bindsMovieRepository(movieRepository: MovieRepository): IMovieRepository
}