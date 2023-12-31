package com.example.cocktailapp

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "favorites")

object DataStoreUtils {
    private suspend fun setFavoriteFromId(context: Context, id: String, isFavorite: Boolean) {
        val key = intPreferencesKey(id)
        context.dataStore.edit { favorites ->
            favorites[key] = if (isFavorite) 1 else 0
        }
    }

    suspend fun getFavoriteFromId(context: Context, id: String): Boolean {
        val key = intPreferencesKey(id)
        val intFavoriteValue = context.dataStore.data.first()[key] ?: 0
        return intFavoriteValue != 0
    }

    suspend fun changeFavoriteFromId(context: Context, id: String): Boolean {
        val newValue = !getFavoriteFromId(context, id)
        setFavoriteFromId(context, id, newValue)
        return newValue
    }

    suspend fun getAllFavorites(context: Context): Set<Preferences.Key<*>>? {
        val keys = context.dataStore.data
            .map {
                it.asMap().filter { entry -> entry.value != 0 }.keys
            }
        return keys.firstOrNull()
    }
}
