package maciej.witkowski.koleorecruitmenttask.domain

import maciej.witkowski.koleorecruitmenttask.data.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.base.ObservableUseCase

interface GetStationsUseCase : ObservableUseCase<Unit, List<StationsItem>>