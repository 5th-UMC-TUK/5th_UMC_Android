package umc.mission.roomdbpractice

import androidx.room.*

@Dao
interface ProfileDAO {
    @Insert
    fun insert(profile: Profile)

    @Update
    fun update(profile: Profile)

    @Delete
    fun delete(profile: Profile)

    @Query("SELECT * FROM Profile")
    fun getAll(): List<Profile>

    @Query("DELETE FROM profile")
    fun deleteAllProfile()

}