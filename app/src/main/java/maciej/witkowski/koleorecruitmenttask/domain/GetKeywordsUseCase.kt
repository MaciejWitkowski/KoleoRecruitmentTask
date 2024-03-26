package maciej.witkowski.koleorecruitmenttask.domain

import maciej.witkowski.koleorecruitmenttask.domain.common.ObservableUseCase
import maciej.witkowski.koleorecruitmenttask.domain.common.NetworkResult
import maciej.witkowski.koleorecruitmenttask.domain.model.StationKeyword

interface GetKeywordsUseCase : ObservableUseCase<Unit, NetworkResult<List<StationKeyword>>>