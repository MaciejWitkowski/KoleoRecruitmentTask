package maciej.witkowski.koleorecruitmenttask.domain

import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.domain.common.ObservableUseCase

interface GetKeywordsUseCase : ObservableUseCase<Unit, List<StationKeywordsItem>>