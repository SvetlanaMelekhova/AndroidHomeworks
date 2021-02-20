package by.htp.first.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.fragment.FragmentLoader
import by.htp.first.myproject.fragment.FragmentPersonList
//import by.htp.first.myproject.database.DatabaseRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), FragmentLoader {

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setBackgroundColor(getColor(R.color.textColorPrimary))
        bottomNavigationView.itemRippleColor

        val databaseRepository = DatabaseRepository(applicationContext)
        showFragment()


    }

    private fun showFragment() {
        supportFragmentManager.commit {
            add<FragmentPersonList>(R.id.fragmentContainer)
        }
    }



    override fun loadFragment(fragmentClass: Class<out Fragment>, transitionCode: Int, bundle: Bundle) {
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