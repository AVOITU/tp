package com.example.eni_shop.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

    interface Destination{
        val route : String
    }

    object HomeDestination : Destination{
        override val route: String
            get() = "home"
    }

    object DetailDestination : Destination{
        override val route: String
            get() = "articleDetail"

        val idArg = "id"
        val arguments = listOf(
            navArgument(idArg){ type = NavType.LongType }
        )

        val routeWithArgs = "${route}/{$idArg}"
    }

    object AddArticleDestination : Destination{
        override val route: String
            get() = "addArticle"

        val nameArg = "name"
        val arguments = listOf(
            navArgument(nameArg){ type = NavType.StringType }
        )

        val routeWithArgs = "$route/{$nameArg}"
    }