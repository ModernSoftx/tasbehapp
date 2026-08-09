package com.yourname.tasbehapp.di

import android.content.Context
import com.yourname.tasbehapp.utils.HapticManager
import com.yourname.tasbehapp.utils.PreferenceManager
import com.yourname.tasbehapp.utils.SoundManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideSoundManager(
        @ApplicationContext context: Context,
        preferenceManager: PreferenceManager
    ): SoundManager {
        return SoundManager(context, preferenceManager)
    }
    
    @Provides
    @Singleton
    fun provideHapticManager(
        @ApplicationContext context: Context,
        preferenceManager: PreferenceManager
    ): HapticManager {
        return HapticManager(context, preferenceManager)
    }
    
    @Provides
    @Singleton
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ): PreferenceManager {
        return PreferenceManager(context)
    }
}
