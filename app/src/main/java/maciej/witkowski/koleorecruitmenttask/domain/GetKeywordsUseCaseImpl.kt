package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult

class GetKeywordsUseCaseImpl(
    private val stationsRepository: StationsRepository,
) : GetKeywordsUseCase, BaseObservableUseCase<Unit, NetworkResult<List<StationKeywordsItem>>>() {

    override fun createFlow(request: Unit): Flow<NetworkResult<List<StationKeywordsItem>>> = flow {
        emitAll(stationsRepository.getStationKeywords())
    }
}