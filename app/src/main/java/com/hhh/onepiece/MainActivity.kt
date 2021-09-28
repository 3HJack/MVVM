package com.hhh.onepiece

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        findViewById<View>(R.id.new_paging).setOnClickListener {
            startActivity(Intent(this@MainActivity, com.nb.core.test.main.HomeActivity::class.java))
        }
        findViewById<View>(R.id.old_paging).setOnClickListener {
            startActivity(Intent(this@MainActivity, com.hhh.onepiece.main.HomeActivity::class.java))
        }
    }
}