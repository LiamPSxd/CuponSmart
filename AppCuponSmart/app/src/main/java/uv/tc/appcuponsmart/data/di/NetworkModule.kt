package uv.tc.appcuponsmart.data.di

import android.content.Context
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.builder.Builders
import com.koushikdutta.ion.builder.LoadBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    @Singleton
    @Provides
    fun provideIon(@ApplicationContext context: Context): LoadBuilder<Builders.Any.B>{
        Ion.getDefault(context).conscryptMiddleware.enable(false)
        return Ion.with(context)
    }

    @Singleton
    @Provides
    fun provideGson() = Gson()
}