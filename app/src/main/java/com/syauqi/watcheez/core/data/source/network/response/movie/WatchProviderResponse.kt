package com.syauqi.watcheez.core.data.source.network.response.movie

import com.google.gson.annotations.SerializedName
import com.syauqi.watcheez.domain.movie.model.MovieProviders

data class WatchProviderResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: ProviderResults? = null
){
	fun toMovieProviderDomain(limit: Int = 5): MovieProviders{
		val us : US = this.results?.uS ?: US(null, "", null, null, null)
		val providers: ArrayList<MovieProviders.WatchProvider> = arrayListOf()
		us.buy?.let { list ->
			providers.addAll(
				list.map {
					MovieProviders.WatchProvider(
						it.logoPath,
						it.providerId,
						it.displayPriority,
						it.providerName,
					)
				}
			)
		}
		us.rent?.let { list ->
			providers.addAll(
				list.map {
					MovieProviders.WatchProvider(
						it.logoPath,
						it.providerId,
						it.displayPriority,
						it.providerName,
					)
				}
			)
		}
		us.flatrate?.let { list ->
			providers.addAll(
				list.map {
					MovieProviders.WatchProvider(
						it.logoPath,
						it.providerId,
						it.displayPriority,
						it.providerName,
					)
				}
			)
		}
		providers.sortBy { it.displayPriority }
		return MovieProviders(
			link = us.link,
			providers =  if(providers.size > limit){
				providers.subList(0, limit)
			}else{
				providers
			}
		)
	}
}

data class RentItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)
data class FlatrateItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)


data class BuyItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)

data class US(

	@field:SerializedName("buy")
	val buy: List<BuyItem>? = null,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("rent")
	val rent: List<RentItem>? = null,

	@field:SerializedName("flatrate")
	val flatrate: List<FlatrateItem>? = null,

	@field:SerializedName("ads")
	val ads: List<AdsItem>? = null
)

data class AdsItem(

	@field:SerializedName("logo_path")
	val logoPath: String,

	@field:SerializedName("provider_id")
	val providerId: Int,

	@field:SerializedName("display_priority")
	val displayPriority: Int,

	@field:SerializedName("provider_name")
	val providerName: String
)

data class ProviderResults(
	@field:SerializedName("US")
	val uS: US? = null,
)