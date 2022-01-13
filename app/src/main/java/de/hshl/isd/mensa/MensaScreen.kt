package de.hshl.isd.mensa

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import de.hshl.isd.mensa.ui.theme.MensaTheme
import io.github.italbytz.adapters.meal.OpenMensaGetMealsCommand
import io.github.italbytz.adapters.meal.OpenMensaMeal
import io.github.italbytz.adapters.meal.OpenMensaMealCollection
import io.github.italbytz.adapters.meal.OpenMensaPrice
import io.github.italbytz.ports.meal.Additives
import io.github.italbytz.ports.meal.Allergens
import io.github.italbytz.ports.meal.Category
import io.github.italbytz.ports.meal.MealCollection
import java.time.LocalDate
import java.util.*

@Composable
fun Mensa(navController: NavController) {
    val service = OpenMensaGetMealsCommand()
    val viewModel = MensaViewModel()

    fun success(collections: List<MealCollection>) {
        val mealCollections = collections.map { it.toCollectionViewModel() }
        viewModel.collections = mealCollections
    }

    fun failure(error: Throwable) {
        Log.e("MainContent", error.localizedMessage!!)
    }

    service.execute(
        MealQueryDTO(42, LocalDate.now()),
        ::success,
        ::failure
    )

    MensaTheme {
        Scaffold {
            LazyColumn {
                items(viewModel.collections) { collection ->
                    SectionHeader(title = collection.name)
                    collection.meals.forEach { meal -> 
                        Column() {
                            MealRow(meal)
                        }
                        
                    }
                }
            }
        }
    }
}

