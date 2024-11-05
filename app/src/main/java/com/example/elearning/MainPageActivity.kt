package com.example.elearning

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat

class MainPageActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)  // Ensure this matches your layout file

        // Initialize the DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Set up ActionBarDrawerToggle (Hamburger Icon)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,  // String resource for "open drawer"
            R.string.navigation_drawer_close  // String resource for "close drawer"
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up NavigationView listener for menu item clicks
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleMenuClick(menuItem)
            true
        }

        // Handle the Menu Button (Hamburger Icon) Click
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)  // Open the navigation drawer
        }
    }

    // Method to handle the navigation drawer menu item clicks
    private fun handleMenuClick(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.navigationView -> {
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                // Handle the home navigation here
            }
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                // Handle the profile navigation here
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
                // Handle the settings navigation here
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show()
                // Handle logout logic here
            }
        }
        // Close the drawer after a menu item is clicked
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    // Handle back button press to close the drawer if it's open
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // Sync the state of the toggle with the navigation drawer
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }
}
