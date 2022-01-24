package de.hshl.isd.mensa

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import de.hshl.isd.mensa.ui.theme.MensaTheme
import io.github.italbytz.adapters.meal.OpenMensaGetMealsCommand
import io.github.italbytz.ports.meal.MealCollection
import java.time.LocalDate

@Composable
fun Mensa(navController: NavController) {
    val service = OpenMensaGetMealsCommand()
    val viewModel = MensaViewModel()
    val reload = remember { mutableStateOf("") }

    fun success(collections: List<MealCollection>) {
        val mealCollections = collections.map { it.toCollectionViewModel() }
        viewModel.collections = mealCollections
    }

    fun failure(error: Throwable) {
        Log.e("MainContent", error.localizedMessage!!)
    }

    LaunchedEffect(reload) {
        kotlin.runCatching {
            service.execute(
                MealQueryDTO(42, LocalDate.now())
            )
        }.onSuccess(::success).onFailure(::failure)
    }

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

