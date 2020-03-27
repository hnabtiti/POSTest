package x2020.com.posrockettask.database.room

import android.content.Context
import androidx.room.*
import org.koin.dsl.module.module
import x2020.com.posrockettask.database.room.entities.DiscountEntity
import x2020.com.posrockettask.database.room.entities.ExtraChargeEntity
import x2020.com.posrockettask.database.room.entities.OrdersEntity
import x2020.com.posrockettask.database.room.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<ProductEntity>?

    @Query("SELECT * FROM product WHERE name IN (:names)")
    fun loadAllByName(names: List<String>): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity)

    @Delete
    fun delete(product: ProductEntity)

    @Query("DELETE FROM product")
    fun nukeTable()
}

@Dao
interface DiscountDao {
    @Query("SELECT * FROM discount")
    fun getAll(): List<DiscountEntity>?

    @Query("SELECT * FROM discount WHERE name IN (:names)")
    fun loadAllByName(names: List<String>): List<DiscountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(discountEntity: List<DiscountEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(discountEntity: DiscountEntity)

    @Delete
    fun delete(discountEntity: DiscountEntity)

    @Query("DELETE FROM discount")
    fun nukeTable()
}

@Dao
interface ExtraChargeDao {
    @Query("SELECT * FROM extra_charge")
    fun getAll(): List<ExtraChargeEntity>?

    @Query("SELECT * FROM extra_charge WHERE name IN (:names)")
    fun loadAllByName(names: List<String>): List<ExtraChargeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(discount: List<ExtraChargeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(extraCharge: ExtraChargeEntity)

    @Delete
    fun delete(extraCharge: ExtraChargeEntity)

    @Query("DELETE FROM extra_charge")
    fun nukeTable()
}

@Dao
interface OrdersDao {
    @Query("SELECT * FROM Orders")
    fun getAll(): List<OrdersEntity>?

    @Query("SELECT * FROM Orders WHERE customerId IN (:customerId)")
    fun loadAllByCustomerId(customerId: List<Int>): List<OrdersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(orders: List<OrdersEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orders: OrdersEntity)

    @Delete
    fun delete(orders: OrdersEntity)

    @Query("DELETE FROM Orders")
    fun nukeTable()
}


@Database(
    entities = [ProductEntity::class, DiscountEntity::class, ExtraChargeEntity::class, OrdersEntity::class],
    version = 1
)
abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun discountDao(): DiscountDao
    abstract fun extraChargeDao(): ExtraChargeDao
    abstract fun orderDao(): OrdersDao
}

fun getDataBaseInstance(context: Context): RoomAppDatabase {
    return Room.databaseBuilder(
        context,
        RoomAppDatabase::class.java, "testRoom.db"
    ).build()
}

val DatabaseRepository = module {
    single { getDataBaseInstance(get()) }
}
