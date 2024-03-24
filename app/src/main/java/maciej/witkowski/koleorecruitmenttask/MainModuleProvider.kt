package maciej.witkowski.koleorecruitmenttask

import android.system.Os.bind
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProvider
import maciej.witkowski.koleorecruitmenttask.common.DispatcherProviderImpl
import maciej.witkowski.koleorecruitmenttask.data.StationsApiService
import maciej.witkowski.koleorecruitmenttask.data.StationsRepositoryImpl
import maciej.witkowski.koleorecruitmenttask.domain.CombineStationsUseCase
import maciej.witkowski.koleorecruitmenttask.domain.CombineStationsUseCaseImpl
import maciej.witkowski.koleorecruitmenttask.domain.GetKeywordsUseCase
import maciej.witkowski.koleorecruitmenttask.domain.GetKeywordsUseCaseImpl
import maciej.witkowski.koleorecruitmenttask.domain.GetStationsUseCase
import maciej.witkowski.koleorecruitmenttask.domain.GetStationsUseCaseImpl
import maciej.witkowski.koleorecruitmenttask.domain.StationsRepository
import maciej.witkowski.koleorecruitmenttask.ui.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal val BASE_URL_QUALIFIER = named("BASE_URL")

object MainModuleProvider : ModuleProvider {

    override fun get() = module {

        singleOf(StationsApiService::invoke)
        factoryOf(::StationsRepositoryImpl) { bind<StationsRepository>() }
        factoryOf(::CombineStationsUseCaseImpl) { bind<CombineStationsUseCase>() }
        factoryOf(::GetStationsUseCaseImpl) { bind<GetStationsUseCase>() }
        factoryOf(::GetKeywordsUseCaseImpl) { bind<GetKeywordsUseCase>() }
        viewModelOf(::MainViewModel)
        single<DispatcherProvider> { DispatcherProviderImpl() }

        single {
            provideRetrofit(
                baseUrl = get(BASE_URL_QUALIFIER),
                moshi = get(),
                client = get(),
            )
        }
        single { provideMoshi() }

        single { provideOkHttpClient() }

        factory(BASE_URL_QUALIFIER) { "https://koleo.pl/api/v2/main/" }
    }
}

private fun provideMoshi(): Moshi =
    Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun provideRetrofit(
    baseUrl: String,
    moshi: Moshi,
    client: OkHttpClient,
): Retrofit =
    Retrofit
        .Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient
        .Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE },
        ).build()