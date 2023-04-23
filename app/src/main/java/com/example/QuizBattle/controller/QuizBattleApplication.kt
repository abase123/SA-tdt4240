package com.example.QuizBattle.controller

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.QuizBattle.controller.GameCoreController.FragmentLoading
import com.example.QuizBattle.controller.GameCoreController.GameEngine
import com.example.QuizBattle.controller.ScreenAndMediaControllers.ScreenNavigator
/**
 * The QuizBattleApplication class is a custom application class that manages the initialization
 * and provisioning of dependencies for the application, such as the PlayerViewModel, ScreenNavigator,
 * and GameEngine. This class acts as a simple dependency injection container without relying
 * on a framework, which makes it suitable for small-to-medium sized projects.
 *
 * The application class defines factory functions for creating instances of these dependencies
 * and injects them into the corresponding classes when needed.
 */
class QuizBattleApplication:Application() {

    lateinit var playerViewModelFactory: (ViewModelStoreOwner) -> PlayerViewModel
    lateinit var screenNavigatorFactory: (GameActivity, FragmentLoading) -> ScreenNavigator
    lateinit var gameEngineFactory: (GameActivity, LifecycleCoroutineScope, ViewModelStoreOwner) -> GameEngine

    override fun onCreate() {
        super.onCreate()
        initializeDependencies()
    }

    private fun initializeDependencies() {
        playerViewModelFactory = { viewModelStoreOwner ->
            ViewModelProvider(viewModelStoreOwner)[PlayerViewModel::class.java]
        }

        screenNavigatorFactory = { context, fragmentLoading ->
            ScreenNavigator(context, fragmentLoading)
        }

        gameEngineFactory = { gameActivity, lifecycleScope, viewModelStoreOwner ->
            val fragmentLoading = FragmentLoading()
            GameEngine(
                gameActivity,
                lifecycleScope,
                playerViewModelFactory(viewModelStoreOwner),
                screenNavigatorFactory(gameActivity, fragmentLoading),
                fragmentLoading
            )
        }
    }
}