package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.data.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.domain.base.BaseObservableUseCase

class GetKeywordsUseCaseImpl(
    private val stationsRepository: StationsRepository,
) : GetKeywordsUseCase, BaseObservableUseCase<Unit, List<StationKeywordsItem>>() {

    override fun createFlow(request: Unit): Flow<List<StationKeywordsItem>> = flow {
        emitAll(stationsRepository.getStationKeywords())
    }
}