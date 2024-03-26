package maciej.witkowski.koleorecruitmenttask.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.data.mapper.toStation
import maciej.witkowski.koleorecruitmenttask.data.mapper.toStationKeywords
import maciej.witkowski.koleorecruitmenttask.domain.StationsRepository
import maciej.witkowski.koleorecruitmenttask.domain.common.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.Station
import maciej.witkowski.koleorecruitmenttask.domain.model.StationKeyword

class StationsRepositoryImpl(
    private val stationsApiService: StationsApiService
) : StationsRepository {

    override suspend fun getStations(): Flow<NetworkResult<List<Station>>> = flow {
        stationsApiService.getStations().onSuccess { data ->
            emit(NetworkResult.Success(data.map { it.toStation() }))
        }
        stationsApiService.getStations().onFailure {
            emit(NetworkResult.Error(it.toString()))
        }
    }

    override suspend fun getStationKeywords(): Flow<NetworkResult<List<StationKeyword>>> = flow {
        stationsApiService.getKeywords().onSuccess { data ->
            emit(NetworkResult.Success(data.map { it.toStationKeywords() }))
        }
        stationsApiService.getKeywords().onFailure {
            emit(NetworkResult.Error(it.toString()))
        }
    }
}