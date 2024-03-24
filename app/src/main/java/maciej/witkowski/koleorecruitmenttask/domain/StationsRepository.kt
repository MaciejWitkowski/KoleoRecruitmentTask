package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import maciej.witkowski.koleorecruitmenttask.data.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.data.StationsItem

interface StationsRepository {

    suspend fun getStations(): Flow<List<StationsItem>>

    suspend fun getStationKeywords(): Flow<List<StationKeywordsItem>>
}