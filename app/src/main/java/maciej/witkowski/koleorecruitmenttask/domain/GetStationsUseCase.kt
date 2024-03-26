package maciej.witkowski.koleorecruitmenttask.domain

import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.ObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult

interface GetStationsUseCase : ObservableUseCase<Unit, NetworkResult<List<StationsItem>>>