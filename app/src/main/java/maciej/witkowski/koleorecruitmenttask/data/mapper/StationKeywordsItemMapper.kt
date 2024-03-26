package maciej.witkowski.koleorecruitmenttask.data.mapper

import maciej.witkowski.koleorecruitmenttask.data.model.StationKeywordsItem
import maciej.witkowski.koleorecruitmenttask.domain.model.StationKeyword

internal fun StationKeywordsItem.toStationKeywords(): StationKeyword =
    StationKeyword(
        keyword = keyword,
        stationId = station_id
    )