package io.lb.firebaseexample.settings_feature.di

import dagger.Provides
import io.lb.firebaseexample.settings_feature.data.data_source.SettingsDataSource
import io.lb.firebaseexample.settings_feature.data.repository.SettingsRepositoryImpl
import io.lb.firebaseexample.settings_feature.domain.repository.SettingsRepository


class SettingsModule {
    @Provides
    fun providesTodoRepository(
        dataSource: SettingsDataSource,
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataSource)
    }
}