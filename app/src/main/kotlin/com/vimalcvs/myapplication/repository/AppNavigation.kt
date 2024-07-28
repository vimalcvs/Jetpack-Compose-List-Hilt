package com.vimalcvs.myapplication.repository

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.vimalcvs.myapplication.model.ModelPost
import com.vimalcvs.myapplication.view.DetailScreen
import com.vimalcvs.myapplication.view.MainScreen
import com.vimalcvs.myapplication.view.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { MainScreen(navController) }
        composable(
            "post/{post}",
            arguments = listOf(navArgument("post") { type = NavType.StringType })
        ) { backStackEntry ->
            val postJson = backStackEntry.arguments?.getString("post")
            val post = Gson().fromJson(
                Uri.decode(postJson),
                ModelPost::class.java
            )
            DetailScreen(post, navController)
        }
    }
}