package maciej.witkowski.koleorecruitmenttask.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.StationsRepository
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult

class StationsRepositoryImpl(
    private val stationsApiService: StationsApiService,
    private val dispatcherProvider: DispatcherProvider,
) : StationsRepository {

    override suspend fun getStations(): Flow<NetworkResult<List<StationsItem>>> = flow {
        stationsApiService.getStations().onSuccess {
            emit(NetworkResult.Success(it))
        }
        stationsApiService.getStations().onFailure {
            emit(NetworkResult.Error(it.toString()))
        }
    }

    override suspend fun getStationKeywords(): Flow<NetworkResult<List<StationKeywordsItem>>> = flow {
        stationsApiService.getKeywords().onSuccess {
            emit(NetworkResult.Success(it))
        }
        stationsApiService.getKeywords().onFailure {
            emit(NetworkResult.Error(it.toString()))
        }
    }
}