package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import maciej.witkowski.koleorecruitmenttask.domain.common.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.Station
import maciej.witkowski.koleorecruitmenttask.domain.model.StationKeyword

interface StationsRepository {

    suspend fun getStations(): Flow<NetworkResult<List<Station>>>

    suspend fun getStationKeywords(): Flow<NetworkResult<List<StationKeyword>>>
}