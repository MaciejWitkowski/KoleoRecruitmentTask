package maciej.witkowski.koleorecruitmenttask.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.StationsRepository

class StationsRepositoryImpl(
    private val stationsApiService: StationsApiService,
    private val dispatcherProvider: DispatcherProvider,
) : StationsRepository {

    override suspend fun getStations(): Flow<List<StationsItem>> = flow {
        stationsApiService.getStations().onSuccess {
            emit(it)
        }
        stationsApiService.getStations().onFailure {
            //TODO
        }
    }

    override suspend fun getStationKeywords(): Flow<List<StationKeywordsItem>> = flow {
        stationsApiService.getKeywords().onSuccess {
            emit(it)
        }
        stationsApiService.getKeywords().onFailure {
            //TODO
        }
    }
}