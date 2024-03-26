package maciej.witkowski.koleorecruitmenttask.domain

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.BaseObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult

class GetStationsUseCaseImpl(
    private val stationsRepository: StationsRepository,
) : GetStationsUseCase, BaseObservableUseCase<Unit, NetworkResult<List<StationsItem>>>() {

    override fun createFlow(request: Unit): Flow<NetworkResult<List<StationsItem>>> = flow {
        emitAll(stationsRepository.getStations())
    }
}