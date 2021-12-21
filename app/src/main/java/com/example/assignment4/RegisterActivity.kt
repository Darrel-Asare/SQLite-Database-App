package com.example.assignment4

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import android.text.TextUtils
import android.view.View
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    var Email: EditText? = null
    var Password: EditText? = null
    var Name: EditText? = null
    var Register: Button? = null
    var StoreName: String? = null
    var StoreEmail: String? = null
    var StorePassword: String? = null
    var EditTextEmptyHolder: Boolean? = null
    lateinit var sqLiteDatabaseObj: SQLiteDatabase
    var SQLiteDataBaseQueryHolder: String? = null
    var sqLiteHelper: SQLiteHelper? = null
    lateinit var cursor: Cursor
    var F_Result = "Not_Found"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Register = findViewById<View>(R.id.buttonRegister) as Button
        Email = findViewById<View>(R.id.editEmail) as EditText
        Password = findViewById<View>(R.id.editPassword) as EditText
        Name = findViewById<View>(R.id.editName) as EditText
        sqLiteHelper = SQLiteHelper(this)
        Register!!.setOnClickListener {
            SQLiteDataBaseBuild()
            SQLiteTableBuild()
            CheckEditTextStatus()
            CheckingEmailAlreadyExistsOrNot()
            EmptyEditTextAfterDataInsert()
        }
    }

    fun SQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, MODE_PRIVATE, null)
    }

    fun SQLiteTableBuild() {
        sqLiteDatabaseObj!!.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Email + " VARCHAR, " + SQLiteHelper.Table_Column_3_Password + " VARCHAR);")
    }

    fun InsertDataIntoSQLiteDatabase() {
        if (EditTextEmptyHolder == true) {
            SQLiteDataBaseQueryHolder =
                "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (name,email,password) VALUES('" + StoreName + "', '" + StoreEmail + "', '" + StorePassword + "');"
            sqLiteDatabaseObj!!.execSQL(SQLiteDataBaseQueryHolder)
            sqLiteDatabaseObj!!.close()
            Toast.makeText(this@RegisterActivity, "User Registered Successfully", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(
                this@RegisterActivity,
                "Please Fill All The Required Fields.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun EmptyEditTextAfterDataInsert() {
        Name!!.text.clear()
        Email!!.text.clear()
        Password!!.text.clear()
    }

    fun CheckEditTextStatus() {
        StoreName = Name!!.text.toString()
        StoreEmail = Email!!.text.toString()
        StorePassword = Password!!.text.toString()
        EditTextEmptyHolder =
            if (TextUtils.isEmpty(StoreName) || TextUtils.isEmpty(StoreEmail) || TextUtils.isEmpty(
                    StorePassword
                )
            ) {
                false
            } else {
                true
            }
    }

    fun CheckingEmailAlreadyExistsOrNot() {
        sqLiteDatabaseObj = sqLiteHelper!!.writableDatabase
        cursor = sqLiteDatabaseObj.query(
            SQLiteHelper.TABLE_NAME,
            null,
            " " + SQLiteHelper.Table_Column_2_Email + "=?",
            arrayOf(StoreEmail),
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst()
                F_Result = "Email Found"
                cursor.close()
            }
        }
        CheckFinalResult()
    }

    fun CheckFinalResult() {
        if (F_Result.equals("Email Found", ignoreCase = true)) {
            Toast.makeText(this@RegisterActivity, "Email Already Exists", Toast.LENGTH_LONG).show()
        } else {
            InsertDataIntoSQLiteDatabase()
        }
        F_Result = "Not_Found"
    }
}