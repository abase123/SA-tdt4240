package com.example.QuizBattle.controller.GameCoreController

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.ScreenAndMediaControllers.ScreenNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(ActivityComponent::class)
object GameEngineModule {
    @Provides
    fun providePlayerViewModel(viewModelStoreOwner: ViewModelStoreOwner): PlayerViewModel {
        return ViewModelProvider(viewModelStoreOwner)[PlayerViewModel::class.java]
    }

    @Provides
    fun provideScreenNavigator(context: GameActivity, fragmentLoadingState: FragmentLoadingState): ScreenNavigator {
        return ScreenNavigator(context, fragmentLoadingState)
    }

    @Provides
    fun provideFragmentLoadingState(): FragmentLoadingState {
        return FragmentLoadingState()
    }
}