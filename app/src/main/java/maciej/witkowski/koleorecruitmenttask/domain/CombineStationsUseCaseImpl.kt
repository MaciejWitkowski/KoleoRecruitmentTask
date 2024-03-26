package maciej.witkowski.koleorecruitmenttask.domain

import android.util.Log
import androidx.compose.ui.layout.LookaheadScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.Station

class CombineStationsUseCaseImpl(
    private val getStations: GetStationsUseCase,
    private val getKeywords: GetKeywordsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : CombineStationsUseCase, BaseObservableUseCase<Unit, NetworkResult<List<Station>>>() {

    init {
        getStations.invoke(Unit)
        getKeywords.invoke(Unit)
    }

    override fun createFlow(request: Unit): Flow<NetworkResult<List<Station>>> = combine(
        getStations.data,
        getKeywords.data
    ) { stations, keywords ->
        mergeStations(stations, keywords)
    }.flowOn(dispatcherProvider.default)
}

private fun mergeStations(
    stations: NetworkResult<List<StationsItem>>,
    keywords: NetworkResult<List<StationKeywordsItem>>
): NetworkResult<List<Station>> {
    return if (!stations.data.isNullOrEmpty() && !keywords.data.isNullOrEmpty()) {
        val mapArray2 = keywords.data.associate { Pair(it.station_id, it.keyword ?: "") }
        NetworkResult.Success(stations.data.map {//TODO
            Station(it.id, mapArray2.getOrDefault(it.id, ""), it.hits, it.latitude, it.longitude)
        }.sortedByDescending { it.hits })
    } else NetworkResult.Error("Error")
}
