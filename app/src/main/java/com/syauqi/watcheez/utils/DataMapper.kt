package com.syauqi.watcheez.utils

import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity
import com.syauqi.watcheez.core.data.source.local.entity.people_w_movies.PeopleWithMoviesEntity
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people_detail.PersonDetailResponse
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.model.PersonDetail
import com.syauqi.watcheez.utils.DataMapper.toPeople
import java.lang.StringBuilder

object DataMapper {
    fun List<PeopleEntity>.toPeopleArrayList():ArrayList<People>{
        val result = ArrayList<People>()
        this.map { peopleEntity ->
            result.add(
                People(
                    id = peopleEntity.id,
                    name = peopleEntity.name,
                    photoUrl = peopleEntity.photoUrl,
                    popularity = peopleEntity.popularity,
                    gender = peopleEntity.gender,
                    isFavorite = peopleEntity.isFavorite
                )
            )
        }
        return result
    }

    fun List<PeopleResponse>.toPeopleEntityArrayList():List<PeopleEntity>{
        val result = ArrayList<PeopleEntity>()
        this.map { peopleResponse ->
            if(peopleResponse.profilePath != null && peopleResponse.popularity != null){
                result.add(
                    PeopleEntity(
                        id = peopleResponse.id,
                        name = peopleResponse.name,
                        photoUrl = peopleResponse.profilePath,
                        popularity = peopleResponse.popularity,
                        gender = peopleResponse.gender
                    )
                )
            }
        }
        return result
    }

    fun PeopleWithMoviesEntity.toPeople(): People {
        val peopleEntity = peopleEntity
        return People(
            id = peopleEntity.id,
            name = peopleEntity.name,
            photoUrl = peopleEntity.photoUrl,
            popularity = peopleEntity.popularity,
            gender = peopleEntity.gender,
            isFavorite = peopleEntity.isFavorite,
            knownForItem = movies.map { it.toMovie() }
        )
    }

    fun PeopleResponse.toPeopleWithMoviesEntity():PeopleWithMoviesEntity{
        val movieList = ArrayList<MovieEntity>()
        this.knownFor?.filter {
            (it.popularity != null) && (it.backdropPath != null) && (it.posterPath != null)
        }?.map {
            movieList.add(it.toMovieEntity())
        }
        this.also { peopleResponse ->
            return PeopleWithMoviesEntity(
                peopleEntity = PeopleEntity(
                    id = peopleResponse.id,
                    name = peopleResponse.name,
                    photoUrl = peopleResponse.profilePath!!,
                    popularity = peopleResponse.popularity!!,
                    gender = peopleResponse.gender
                ),
                movies = peopleResponse.knownFor?.filter {
                    (it.popularity != null) && (it.backdropPath != null) && (it.posterPath != null)
                }?.map {
                    it.toMovieEntity()
                } ?: ArrayList()
            )
        }
    }
//
    fun List<PeopleResponse>.toPeopleArrayList():List<People>{
        val result = ArrayList<People>()
        this.map { peopleResponse ->
            if(peopleResponse.profilePath != null && peopleResponse.popularity != null)
                result.add(
                    peopleResponse.toPeopleEntity().toPeople()
                )
        }
        return result
    }

    fun PeopleResponse.toPeopleEntity(): PeopleEntity {
        return PeopleEntity(
            id = this.id,
            name = this.name,
            photoUrl = this.profilePath!!,
            popularity = this.popularity!!,
            gender = this.gender,
            isFavorite = false
        )
    }

    fun PeopleEntity.toPeople(): People {
        return People(
            id = this.id,
            name = this.name,
            photoUrl = this.photoUrl,
            popularity = this.popularity,
            gender = this.gender,
            isFavorite = isFavorite
        )
    }

    fun People.toPeopleEntity(): PeopleEntity{
        return PeopleEntity(
            id = this.id,
            name = this.name,
            photoUrl = this.photoUrl,
            popularity = this.popularity,
            gender = this.gender,
            isFavorite = isFavorite
        )
    }

    fun People.switchFavorite(): People{
        return People(
            id = this.id,
            name = this.name,
            photoUrl = this.photoUrl,
            popularity = this.popularity,
            gender = this.gender,
            isFavorite = !this.isFavorite
        )
    }

    private fun MovieResponse.toMovieEntity(): MovieEntity{
        return MovieEntity(
            id = id,
            title = title,
            popularity = popularity!!,
            backdropPath = backdropPath!!,
            posterPath = posterPath!!,
            genreIds = genreIds,
            overview = overview,
            video = video,
            voteAverage = voteAverage
        )
    }

    private fun MovieEntity.toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            popularity = popularity,
            backdropPath = backdropPath,
            genreIds = genreIds,
            posterPath = posterPath,
            overview = overview,
            video = video,
            voteAverage = voteAverage
        )
    }

    fun PersonDetailResponse.toPersonDetail(): PersonDetail {
        return PersonDetail(
        birthday =   this.birthday,
        gender = this.gender,
        profilePath = this.profilePath,
        biography = this.biography,
        popularity = this.popularity,
        name = this.name,
        id = this.id,
        externalIds = this.externalIds,
        placeOfBirth = this.placeOfBirth,
        images = this.images.profiles,
        movieCredits = this.movieCredits?.cast?.filter {
            castItem -> castItem.backdropPath != null
        },
        )
    }

    fun List<Movie>.toStringOfTitles(limit: Int): String{
        val builder = StringBuilder()
        val newList = if(this.size > limit) this.subList(0,limit) else this
        newList.forEach {
            builder.append(it.title)
        }
        return builder.toString()
    }
    fun List<MovieResponse>.toListMovie(): List<Movie>{
        return filter { (it.popularity != null) && (it.backdropPath != null) && (it.posterPath != null) }
            .map { it.toMovieEntity().toMovie() }
    }

    fun MovieDetailResponse.toMovieDetail(): MovieDetail{
        return MovieDetail(
            id = id,
            title = title
        )
    }
}