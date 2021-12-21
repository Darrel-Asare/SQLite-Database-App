package com.example.assignment4

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.content.Intent
import android.database.Cursor
import android.widget.Toast
import android.text.TextUtils
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var LoginBtn: Button? = null
    var RegisterBtn: Button? = null
    var Email: EditText? = null
    var Password: EditText? = null
    var StoreEmail: String? = null
    var StorePassword: String? = null
    var EditTextEmptyHolder: Boolean? = null
    lateinit var sqLiteDatabaseObj: SQLiteDatabase
    var sqLiteHelper: SQLiteHelper? = null
    lateinit var cursor: Cursor
    var TempPassword = "NOT_FOUND"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoginBtn = findViewById<View>(R.id.buttonLogin) as Button
        RegisterBtn = findViewById<View>(R.id.buttonRegister) as Button
        Email = findViewById<View>(R.id.editEmail) as EditText
        Password = findViewById<View>(R.id.editPassword) as EditText
        sqLiteHelper = SQLiteHelper(this)
        LoginBtn!!.setOnClickListener {
            CheckEditTextStatus()
            LoginFunction()
        }
        RegisterBtn!!.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun LoginFunction() {
        if (EditTextEmptyHolder!!) {
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
                    TempPassword =
                        cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_3_Password))
                    cursor.close()
                }
            }
            CheckFinalResult()
        } else {
            Toast.makeText(
                this@MainActivity,
                "Please Enter UserName or Password.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun CheckEditTextStatus() {
        StoreEmail = Email!!.text.toString()
        StorePassword = Password!!.text.toString()
        EditTextEmptyHolder =
            if (TextUtils.isEmpty(StoreEmail) || TextUtils.isEmpty(StorePassword)) {
                false
            } else {
                true
            }
    }

    fun CheckFinalResult() {
        if (TempPassword.equals(StorePassword, ignoreCase = true)) {
            Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            intent.putExtra(UserEmail, StoreEmail)
            startActivity(intent)
        } else {
            Toast.makeText(
                this@MainActivity,
                "UserName or Password is Wrong, Please Try Again.",
                Toast.LENGTH_LONG
            ).show()
        }
        TempPassword = "NOT_FOUND"
    }

    companion object {
        const val UserEmail = ""
    }
}