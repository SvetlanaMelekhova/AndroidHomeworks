package by.htp.first.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.htp.first.myproject.database.DatabaseRepository
//import by.htp.first.myproject.database.DatabaseRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)



        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setBackgroundColor(getColor(R.color.textColorPrimary))
        bottomNavigationView.itemRippleColor

        val databaseRepository = DatabaseRepository(applicationContext)


    }
}