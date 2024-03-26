package maciej.witkowski.koleorecruitmenttask.domain

import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.ObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.model.NetworkResult

interface GetKeywordsUseCase : ObservableUseCase<Unit, NetworkResult<List<StationKeywordsItem>>>