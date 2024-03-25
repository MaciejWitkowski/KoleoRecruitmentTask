package maciej.witkowski.koleorecruitmenttask.domain

import android.location.Location
import maciej.witkowski.koleorecruitmenttask.domain.common.ResultUseCase

interface CalculateDistanceUseCase : ResultUseCase<StationsCoords, String>