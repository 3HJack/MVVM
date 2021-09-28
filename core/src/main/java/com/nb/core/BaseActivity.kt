package com.nb.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var fragment: BaseFragment
    protected open val containerViewId = android.R.id.content

    protected abstract fun onCreateFragment(): BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        // Fragment state is very complicated, we do not rebuild it.
        savedInstanceState?.remove("android:support:fragments")
        super.onCreate(savedInstanceState)
        fragment = onCreateFragment()
        supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment, fragment.javaClass.name).commit()
    }
}