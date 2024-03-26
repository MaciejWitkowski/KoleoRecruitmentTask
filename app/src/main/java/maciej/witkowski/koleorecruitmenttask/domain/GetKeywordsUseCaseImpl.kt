package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.common.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.StationKeyword

class GetKeywordsUseCaseImpl(
    private val stationsRepository: StationsRepository,
) : GetKeywordsUseCase, BaseObservableUseCase<Unit, NetworkResult<List<StationKeyword>>>() {

    override fun createFlow(request: Unit): Flow<NetworkResult<List<StationKeyword>>> = flow {
        emitAll(stationsRepository.getStationKeywords())
    }
}