package com.syauqi.watcheez.utils

import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people_detail.PersonDetailResponse
import com.syauqi.watcheez.domain.models.People
import com.syauqi.watcheez.domain.models.PersonDetail

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
                    gender = peopleEntity.gender
                )
            )
        }
        return result
    }

    fun List<PeopleResponse>.toPeopleEntityArrayList():List<PeopleEntity>{
        val result = ArrayList<PeopleEntity>()
        this.map { peopleResponse ->
            if(peopleResponse.profilePath != null && peopleResponse.popularity != null)
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
        return result
    }

    fun List<PeopleResponse>.toPeopleArrayList():List<People>{
        val result = ArrayList<People>()
        this.map { peopleResponse ->
            if(peopleResponse.profilePath != null && peopleResponse.popularity != null)
                result.add(
                    People(
                        id = peopleResponse.id,
                        name = peopleResponse.name,
                        photoUrl = peopleResponse.profilePath,
                        popularity = peopleResponse.popularity,
                        gender = peopleResponse.gender
                    )
                )
        }
        return result
    }

    fun PeopleResponse.toPeople(): People {
        return People(
            id = this.id,
            name = this.name,
            photoUrl = this.profilePath!!,
            popularity = this.popularity!!,
            gender = this.gender
        )
    }

    fun PersonDetailResponse.toPersonDetail(): PersonDetail{
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
}