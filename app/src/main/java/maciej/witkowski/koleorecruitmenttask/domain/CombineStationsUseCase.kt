package maciej.witkowski.koleorecruitmenttask.domain

import maciej.witkowski.koleorecruitmenttask.domain.common.ObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.Station

interface CombineStationsUseCase : ObservableUseCase<Unit, NetworkResult<List<Station>>>