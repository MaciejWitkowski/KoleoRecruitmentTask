package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult

interface StationsRepository {

    suspend fun getStations(): Flow<NetworkResult<List<StationsItem>>>

    suspend fun getStationKeywords(): Flow<NetworkResult<List<StationKeywordsItem>>>
}