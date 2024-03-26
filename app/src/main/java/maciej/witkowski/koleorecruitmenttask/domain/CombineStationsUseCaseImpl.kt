package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.common.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.Station
import maciej.witkowski.koleorecruitmenttask.domain.model.StationKeyword
import maciej.witkowski.koleorecruitmenttask.domain.model.StationWithKeyword

class CombineStationsUseCaseImpl(
    private val getStations: GetStationsUseCase,
    private val getKeywords: GetKeywordsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : CombineStationsUseCase, BaseObservableUseCase<Unit, NetworkResult<List<StationWithKeyword>>>() {

    init {
        getStations.invoke(Unit)
        getKeywords.invoke(Unit)
    }

    override fun createFlow(request: Unit): Flow<NetworkResult<List<StationWithKeyword>>> = combine(
        getStations.data,
        getKeywords.data
    ) { stations, keywords ->
        mergeStations(stations, keywords)
    }.flowOn(dispatcherProvider.default)
}

private fun mergeStations(
    stations: NetworkResult<List<Station>>,
    keywords: NetworkResult<List<StationKeyword>>
): NetworkResult<List<StationWithKeyword>> {
    return if (!stations.data.isNullOrEmpty() && !keywords.data.isNullOrEmpty()) {
        val mapArray2 = keywords.data.associate { Pair(it.stationId, it.keyword ?: "") }
        NetworkResult.Success(stations.data.map {
            StationWithKeyword(it.id, mapArray2.getOrDefault(it.id, ""), it.hits, it.latitude, it.longitude)
        }.sortedByDescending { it.hits })
    } else NetworkResult.Error("Error")
}
