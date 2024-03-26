package maciej.witkowski.koleorecruitmenttask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.common.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.Station

class GetStationsUseCaseImpl(
    private val stationsRepository: StationsRepository,
) : GetStationsUseCase, BaseObservableUseCase<Unit, NetworkResult<List<Station>>>() {

    override fun createFlow(request: Unit): Flow<NetworkResult<List<Station>>> = flow {
        emitAll(stationsRepository.getStations())
    }
}