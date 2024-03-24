package maciej.witkowski.koleorecruitmenttask

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal val BASE_URL_QUALIFIER = named("BASE_URL")

object MainModuleProvider : ModuleProvider {

    override fun get() = module {

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