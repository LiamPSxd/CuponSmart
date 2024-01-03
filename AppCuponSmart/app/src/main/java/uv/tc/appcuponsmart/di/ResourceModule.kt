package uv.tc.appcuponsmart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uv.tc.appcuponsmart.core.FuncionHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceModule{
    @Singleton
    @Provides
    fun provideFunciones() = FuncionHelper
}