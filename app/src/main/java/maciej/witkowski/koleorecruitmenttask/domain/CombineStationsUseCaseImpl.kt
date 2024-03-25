package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.Station

class CombineStationsUseCaseImpl(
    private val getStations: GetStationsUseCase,
    private val getKeywords: GetKeywordsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : CombineStationsUseCase, BaseObservableUseCase<Unit, List<Station>>() {

    init {
        getStations.invoke(Unit)
        getKeywords.invoke(Unit)
    }

    override fun createFlow(request: Unit): Flow<List<Station>> = combine(
        getStations.data,
        getKeywords.data
    ) { stations, keywords ->
        mergeStations(stations, keywords)
    }.flowOn(dispatcherProvider.default)
}

private fun mergeStations(stations: List<StationsItem>, keywords: List<StationKeywordsItem>): List<Station> {
    val mapArray2 = keywords.associate { Pair(it.station_id, it.keyword ?: "" ) }
    return stations.map {
        Station(it.id, mapArray2.getOrDefault(it.id, ""), it.hits, it.latitude, it.longitude)
    }.sortedByDescending { it.hits }
}