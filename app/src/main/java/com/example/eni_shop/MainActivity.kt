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
import com.example.eni_shop.ui.Destination
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
                ArticleDetailScreen(articleId = 3)
                EniShopApp()
            }
        }
    }

    @Composable
    fun EniShopApp() {
        val navHostController = rememberNavController()
        EniShopNavHost(navHostController = navHostController)
    }

    private @Composable
    fun EniShopNavHost(navHostController: NavHostController) {

        NavHost(
            navController = navHostController,
            startDestination = Destination.HomeDestination.route
        ) {
            composable(Destination.HomeDestination.route) {
            }
        }
    }
}

