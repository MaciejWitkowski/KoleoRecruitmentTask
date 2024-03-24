package maciej.witkowski.koleorecruitmenttask.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.StationsRepository

class StationsRepositoryImpl(
    private val stationsApiService: StationsApiService,
    private val dispatcherProvider: DispatcherProvider,
) : StationsRepository {

    override suspend fun getStations(): Flow<List<StationsItem>>  = flowOf(stationsApiService.getStations()).flowOn(dispatcherProvider.io)

    override suspend fun getStationKeywords(): Flow<List<StationKeywordsItem>> = flowOf(stationsApiService.getKeywords()).flowOn(dispatcherProvider.io)

}