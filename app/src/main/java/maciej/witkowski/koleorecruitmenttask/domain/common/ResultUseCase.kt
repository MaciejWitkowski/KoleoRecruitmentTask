package maciej.witkowski.koleorecruitmenttask.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ResultUseCase<in R, O> {
    operator fun invoke(request: R): Flow<O>

    suspend fun executeSync(request: R): O
}

abstract class BaseResultUseCase<in R, O> {
    operator fun invoke(request: R): Flow<O> = flow {
        emit(doWork(request))
    }

    suspend fun executeSync(request: R): O = doWork(request)

    protected abstract suspend fun doWork(request: R): O
}
