package maciej.witkowski.koleorecruitmenttask.data

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Headers

interface StationsApiService {

    @Headers("X-KOLEO-Version: 1")
    @GET("stations")
    suspend fun getStations(): List<StationsItem>

    @Headers("X-KOLEO-Version: 1")
    @GET("station_keywords")
    suspend fun getKeywords(): List<StationKeywordsItem>

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<StationsApiService>()
    }
}
