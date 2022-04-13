package com.example.contactsapp


import android.content.Context
import androidx.room.*

@Entity(tableName = "contacts")
data class Contacts (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val fname: String?,
    @ColumnInfo val lname: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val phoneNumber: String?,
    @ColumnInfo val bDate: String?,
    @ColumnInfo val address:String?,
    @ColumnInfo val notes: String?,
    @ColumnInfo val isFavourite: Boolean,
    @ColumnInfo val isEmergency: Boolean,
    @ColumnInfo val profilePicture: Int
        )

@Dao
interface ContactsDao {
    @Insert
    fun insert(contact: Contacts)

    //Get all
    @Query ("SELECT * FROM contacts")
    fun getAllContacts() : List<Contacts>

    //Get Favourites
    @Query ("SELECT * FROM contacts WHERE isFavourite=1")
    fun getFavourites() : List<Contacts>

    //Get Emergency
    @Query ("SELECT * FROM contacts WHERE isEmergency=1")
    fun getEmergency() : List<Contacts>

    //Get Contact
    @Query ("SELECT * FROM contacts WHERE id=:id")
    fun getContact(id: Int) : List<Contacts>

    @Query ("DELETE FROM contacts WHERE id=:contactID")
    fun deleteContact(contactID: Int)

    @Query ("UPDATE contacts SET fname=:newFname WHERE id=:id")
    fun updateFname(newFname: String, id: Int)
    @Query ("UPDATE contacts SET lname=:newlname WHERE id=:id")
    fun updatelname(newlname: String, id: Int)
    @Query ("UPDATE contacts SET email=:newEmail WHERE id=:id")
    fun updateEmail(newEmail: String, id: Int)
    @Query ("UPDATE contacts SET phoneNumber=:newPhoneNumber WHERE id=:id")
    fun updatePhoneNumber(newPhoneNumber: String, id: Int)
    @Query ("UPDATE contacts SET bdate=:newBdate WHERE id=:id")
    fun updateBirthday(newBdate: String, id: Int)
    @Query ("UPDATE contacts SET address=:newAddress WHERE id=:id")
    fun updateAddress(newAddress: String, id: Int)
    @Query ("UPDATE contacts SET notes=:newNotes WHERE id=:id")
    fun updateNotes(newNotes: String, id: Int)
    @Query ("UPDATE contacts SET isFavourite=:newIsFavourite WHERE id=:id")
    fun updateIsFavourite(newIsFavourite: Boolean, id: Int)
    @Query ("UPDATE contacts SET isEmergency=:newIsEmergency WHERE id=:id")
    fun updateIsEmergency(newIsEmergency: Boolean, id: Int)

}

@Database(entities = [Contacts::class], version = 2)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun ContactDao(): ContactsDao

    companion object {

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java,
                        "contacts_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}