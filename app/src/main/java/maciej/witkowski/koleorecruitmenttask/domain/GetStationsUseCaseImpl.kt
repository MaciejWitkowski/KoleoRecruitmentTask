package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.base.BaseObservableUseCase

class GetStationsUseCaseImpl(
    private val stationsRepository: StationsRepository,
) : GetStationsUseCase, BaseObservableUseCase<Unit, List<StationsItem>>() {

    override fun createFlow(request: Unit): Flow<List<StationsItem>> = flow {
        emitAll(stationsRepository.getStations())
    }
}