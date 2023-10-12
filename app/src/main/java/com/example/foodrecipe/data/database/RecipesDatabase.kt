package com.example.foodrecipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Provides

@Database(
    entities = [RecipesEntity::class],
    version =1,
    exportSchema =false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase :RoomDatabase(){
abstract fun recipesDao(): RecipesDao
}