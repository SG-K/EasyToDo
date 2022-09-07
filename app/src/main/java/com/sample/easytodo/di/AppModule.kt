package com.sample.easytodo.di

import android.app.Application
import androidx.room.Room
import com.sample.easytodo.feature_todo.data.data_source.EasyToDoDatabase
import com.sample.easytodo.feature_todo.data.repository.EasyToDoRepositoryImpl
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository
import com.sample.easytodo.feature_todo.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): EasyToDoDatabase {
        return Room.databaseBuilder(
            app,
            EasyToDoDatabase::class.java,
            EasyToDoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEasyToDoRepository(db: EasyToDoDatabase): EasyToDoRepository {
        return EasyToDoRepositoryImpl(db.easyToDoDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: EasyToDoRepository): EasyToDoUseCases {
        return EasyToDoUseCases(
            addTask = AddTask(repository),
            deleteTask = DeleteTask(repository),
            getTask = GetTask(repository),
            getTasks = GetTasks(repository)
        )
    }

}