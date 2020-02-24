package x2020.com.posrockettask.database

import android.content.Context
import androidx.room.*
import org.koin.dsl.module.module
import x2020.com.posrockettask.App
import x2020.com.posrockettask.api.Discount

@Dao
interface DiscountDao {
    @Query("SELECT * FROM discount")
    fun getAll(): List<Discount>?

    @Query("SELECT * FROM discount WHERE name IN (:userIds)")
    fun loadAllByIds(names: List<String>): List<Discount>

    @Insert
    fun insertAll(vararg discount: Discount)

    @Delete
    fun delete(discount: Discount)

    @Query("DELETE FROM discount")
    fun nukeTable()
}

@Database(entities = [Discount::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun discountDao(): DiscountDao
}

fun getDataBaseInstance(context:Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()
}

val DatabaseRepository = module {
    single { getDataBaseInstance(get()) }
}
