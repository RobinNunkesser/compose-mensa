package de.hshl.isd.mensa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MensaViewModel : ViewModel() {
    var collections by mutableStateOf(listOf<CollectionViewModel>())
}
