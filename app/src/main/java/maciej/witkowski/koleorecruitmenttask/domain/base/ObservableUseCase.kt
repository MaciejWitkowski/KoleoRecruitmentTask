package maciej.witkowski.koleorecruitmenttask.domain.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
interface ObservableUseCase<R : Any, O> {
    val data: Flow<O>
    operator fun invoke(request: R)
}

abstract class BaseObservableUseCase<R : Any, O> : ObservableUseCase<R, O> {
    private val requestState = MutableSharedFlow<R>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val data: Flow<O> = requestState
        .distinctUntilChanged()
        .flatMapLatest(::createFlow)
        .distinctUntilChanged()

    override operator fun invoke(request: R) {
        requestState.tryEmit(request)
    }

    protected abstract fun createFlow(request: R): Flow<O>
}
