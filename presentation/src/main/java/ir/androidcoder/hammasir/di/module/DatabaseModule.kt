package ir.androidcoder.hammasir.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.androidcoder.data.local.MyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase (@ApplicationContext context: Context) : MyDatabase = Room
        .databaseBuilder(context , MyDatabase::class.java , "MyDatabase")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao (db : MyDatabase) = db.myDao()


}