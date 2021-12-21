package com.example.assignment4

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

class DashboardActivity : AppCompatActivity() {
    var EmailStored: String? = null
    var Email: TextView? = null
    var LogOUT: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Email = findViewById<View>(R.id.textView1) as TextView
        LogOUT = findViewById<View>(R.id.button1) as Button
        val intent = intent
        EmailStored = intent.getStringExtra(MainActivity.UserEmail)
        Email!!.text = Email!!.text.toString() + EmailStored
        LogOUT!!.setOnClickListener {
            val build = AlertDialog.Builder(this@DashboardActivity)
            build.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialogInterface, i -> finish() }
                .setNegativeButton("No") { dialogInterface, i -> dialogInterface.cancel() }
            val alertDialog = build.create()
            alertDialog.show()
            Toast.makeText(this@DashboardActivity, "Log Out Successful", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_quit ->{
                this.finish()
                true
            }
            else -> true
        }
    }
}