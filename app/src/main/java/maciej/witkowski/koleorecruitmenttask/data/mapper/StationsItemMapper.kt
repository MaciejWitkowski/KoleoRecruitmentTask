package maciej.witkowski.koleorecruitmenttask.data.mapper

import maciej.witkowski.koleorecruitmenttask.data.model.StationsItem
import maciej.witkowski.koleorecruitmenttask.domain.model.Station

internal fun StationsItem.toStation(): Station =
    Station(
        id = id,
        hits = hits,
        longitude = longitude,
        latitude = latitude
    )