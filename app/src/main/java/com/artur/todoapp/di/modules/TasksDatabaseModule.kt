package com.artur.todoapp.di.modules

import android.content.Context
import androidx.room.Room
import com.artur.todoapp.filter.TaskDao
import com.artur.todoapp.filter.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasksDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(context.applicationContext, TasksDatabase::class.java, "todo_database").build()
    }

    @Provides
    fun provideTaskDao(database: TasksDatabase): TaskDao {
        return database.taskDao()
    }
}