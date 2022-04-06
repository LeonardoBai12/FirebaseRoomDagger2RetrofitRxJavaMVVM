package io.lb.firebaseexample.settings_feature.di

import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.settings_feature.data.data_source.SettingsDataSource
import io.lb.firebaseexample.settings_feature.data.repository.SettingsRepositoryImpl
import io.lb.firebaseexample.settings_feature.domain.repository.SettingsRepository
import io.lb.firebaseexample.settings_feature.domain.use_case.GetSettingAccordingToKeyUseCase
import io.lb.firebaseexample.settings_feature.domain.use_case.SaveSettingAccordingToKeyUseCase
import io.lb.firebaseexample.settings_feature.domain.use_case.SettingsUseCases

@Module
class SettingsModule {
    @Provides
    fun providesSettingsRepository(
        dataSource: SettingsDataSource,
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataSource)
    }

    @Provides
    fun providesSettingsUseCases(repository: SettingsRepository): SettingsUseCases {
        return SettingsUseCases(
            getSettingsUseCase = GetSettingAccordingToKeyUseCase(repository),
            saveSettingUseCase = SaveSettingAccordingToKeyUseCase(repository),
        )
    }
}