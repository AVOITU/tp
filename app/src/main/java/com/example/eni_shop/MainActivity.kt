package com.example.eni_shop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eni_shop.repository.ArticleRepository
import com.example.eni_shop.ui.DetailDestination
import com.example.eni_shop.ui.DetailDestination.idArg
import com.example.eni_shop.ui.HomeDestination
import com.example.eni_shop.ui.screen.ArticleDetailScreen
import com.example.eni_shop.ui.screen.ArticleListScreen
import com.example.eni_shop.ui.theme.ENISHOPTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val articleRepository = ArticleRepository()

        val article = articleRepository.getArticle(2)
        Log.i(TAG, article.toString())

        setContent {
            ENISHOPTheme {
//                ArticleDetailScreen(articleId = 3)
                EniShopApp()
            }
        }
    }

    @Composable
    fun EniShopApp() {
        val navHostController = rememberNavController()
        EniShopNavHost(navHostController = navHostController)
    }

    @Composable
    fun EniShopNavHost(navHostController: NavHostController) {

        NavHost(
            navController = navHostController,
            startDestination = HomeDestination.route
        ) {
            composable(HomeDestination.route) {
                ArticleListScreen(onClickToDetail = {
                    navHostController.navigate("${DetailDestination.route}/$it")
                })
            }
            composable(
                route = DetailDestination.routeWithArgs,
                arguments = DetailDestination.arguments
            ) {
                val idArg = it.arguments?.getLong(DetailDestination.idArg) ?: 0
                ArticleDetailScreen(articleId = idArg)
            }
        }
    }

//    @Composable
//    fun
}

