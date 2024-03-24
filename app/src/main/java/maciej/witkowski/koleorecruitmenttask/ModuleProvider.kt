package maciej.witkowski.koleorecruitmenttask

import org.koin.core.module.Module

interface ModuleProvider {
    fun get(): Module
}