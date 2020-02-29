package com.thul.filmee.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thul.filmee.R
import com.thul.filmee.view.activity.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(){

    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dummymain)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val title = findViewById<TextView>(R.id.appTitle)
        title.text = resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_popular, R.id.navigation_top_rated, R.id.navigation_favorites
            )
        )
        val bottomNavDestinationIds = setOf(
            R.id.navigation_popular, R.id.navigation_top_rated, R.id.navigation_favorites
        )
        val appBarConfig = AppBarConfiguration(bottomNavDestinationIds)
        setupActionBarWithNavController(navController, appBarConfig)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id in bottomNavDestinationIds) {
                appBarLayout.setExpanded(true, true)

            }
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

/*    override fun onSupportNavigateUp(): Boolean {
    *//*    val f: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        Log.v("onSupportNavigateUp", " "+ f?.fragmentManager)
        if(f is HomeFragment){
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }*//*

//        return super.onSupportNavigateUp()
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }*/

    override fun onBackPressed() {
//        super.onBackPressed()
        findNavController(R.id.nav_host_fragment).navigateUp()
    }

/*    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_layout, menu)

        val item = menu?.findItem(R.id.search)

        searchView = item?.getActionView() as SearchView
        searchView.setQueryHint("Search View Hint")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)

    }*/
    fun searchIconClick(view : View){
    val intent = Intent(this, SearchActivity::class.java)
    startActivity(intent)
    }
}