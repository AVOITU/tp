package com.example.eni_shop.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

class Destination {

    interface Destination{
        val route : String
    }

    object HomeDestination : Destination{
        override val route: String
            get() = "home"
    }

    object SignInDestination : Destination{
        override val route: String
            get() = "signIn"
    }

    object ProfileDestination : Destination{
        override val route: String
            get() = "profil"

        val nameArg = "name"
        val arguments = listOf(
            navArgument(nameArg){ type = NavType.StringType }
        )

        val routeWithArgs = "$route/{$nameArg}"
    }
}