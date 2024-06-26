package maciej.witkowski.koleorecruitmenttask.common

import kotlinx.coroutines.Dispatchers
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider

class DispatcherProviderImpl : DispatcherProvider {

    override val main = Dispatchers.Main
    override val default = Dispatchers.Default
    override val io = Dispatchers.IO
    override val unconfined = Dispatchers.Unconfined
}
