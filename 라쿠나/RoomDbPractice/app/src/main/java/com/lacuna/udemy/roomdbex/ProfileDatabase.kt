package com.lacuna.udemy.roomdbex

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Profile::class], version = 1) // 테이블 형식이나 개수가 달라지면 version도 바꿔줘야 오류가 안남
abstract class ProfileDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object{

        private var instance: ProfileDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ProfileDatabase?{
            if(instance == null){
                synchronized(ProfileDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileDatabase::class.java,
                        "user-database" // 다른 데이터베이스랑 이름 겹치면 꼬임
                    ).build()
                }
            }
            return instance
        }
    }
}