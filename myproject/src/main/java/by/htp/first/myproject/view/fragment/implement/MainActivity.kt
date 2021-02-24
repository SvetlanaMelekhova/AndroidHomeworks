package by.htp.first.myproject.view.fragment.implement

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import by.htp.first.myproject.R
import by.htp.first.myproject.view.fragment.implement.FragmentAllScheduleListImpl
import by.htp.first.myproject.view.fragment.FragmentLoader
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), FragmentLoader {

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
      //  bottomAppBar = findViewById(R.id.bottom_app_bar)
        bottomNavigationView = findViewById(R.id.bottomNavigation)
       // setSupportActionBar(bottomAppBar)
        showFragment()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item1 -> {
                    loadFragment(
                        FragmentAllScheduleListImpl(),
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    )
                }
               R.id.item2 -> { loadFragment(FragmentCalendarView(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN) }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_navigation_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

    }

    private fun showFragment() {
        supportFragmentManager.commit {
            add<FragmentAllScheduleListImpl>(R.id.fragmentContainer)
        }
    }


    override fun loadFragment(
        fragmentClass: Class<out Fragment>,
        transitionCode: Int,
        bundle: Bundle
    ) {
        supportFragmentManager.commit {
            setTransition(transitionCode)
            replace(R.id.fragmentContainer, fragmentClass, bundle)
            addToBackStack(null)
        }
    }

    override fun loadFragment(fragment: Fragment, transitionCode: Int) {
        supportFragmentManager.commit {
            setTransition(transitionCode)
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
        }
    }

}
