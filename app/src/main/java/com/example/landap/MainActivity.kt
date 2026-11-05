package com.example.landap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.landap.data.LandRepository
import com.example.landap.ui.screens.AboutScreen
import com.example.landap.ui.screens.DirectoryScreen
import com.example.landap.ui.screens.SavedRecordsScreen
import com.example.landap.ui.screens.SearchScreen
import com.example.landap.ui.theme.LandAPTheme
import com.example.landap.ui.theme.OrangeDark
import com.example.landap.ui.theme.OrangePrimary

enum class AppTab(val label: String, val tag: String) {
    SEARCH("Search", "nav_tab_search"),
    SAVED("Saved", "nav_tab_saved"),
    DIRECTORY("Directory", "nav_tab_directory"),
    ABOUT("About", "nav_tab_about")
}

class MainActivity : ComponentActivity() {

    private lateinit var repository: LandRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        repository = LandRepository(applicationContext)

        setContent {
            LandAPTheme {
                var currentTab by remember { mutableStateOf(AppTab.SEARCH) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = OrangeDark,
                            tonalElevation = 8.dp
                        ) {
                            NavigationBarItem(
                                selected = currentTab == AppTab.SEARCH,
                                onClick = { currentTab = AppTab.SEARCH },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                },
                                label = { Text(AppTab.SEARCH.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OrangeDark,
                                    selectedTextColor = Color.White,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.White.copy(alpha = 0.65f),
                                    unselectedTextColor = Color.White.copy(alpha = 0.65f)
                                ),
                                modifier = Modifier.testTag(AppTab.SEARCH.tag)
                            )

                            NavigationBarItem(
                                selected = currentTab == AppTab.SAVED,
                                onClick = { currentTab = AppTab.SAVED },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Saved"
                                    )
                                },
                                label = { Text(AppTab.SAVED.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OrangeDark,
                                    selectedTextColor = Color.White,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.White.copy(alpha = 0.65f),
                                    unselectedTextColor = Color.White.copy(alpha = 0.65f)
                                ),
                                modifier = Modifier.testTag(AppTab.SAVED.tag)
                            )

                            NavigationBarItem(
                                selected = currentTab == AppTab.DIRECTORY,
                                onClick = { currentTab = AppTab.DIRECTORY },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationCity,
                                        contentDescription = "Directory"
                                    )
                                },
                                label = { Text(AppTab.DIRECTORY.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OrangeDark,
                                    selectedTextColor = Color.White,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.White.copy(alpha = 0.65f),
                                    unselectedTextColor = Color.White.copy(alpha = 0.65f)
                                ),
                                modifier = Modifier.testTag(AppTab.DIRECTORY.tag)
                            )

                            NavigationBarItem(
                                selected = currentTab == AppTab.ABOUT,
                                onClick = { currentTab = AppTab.ABOUT },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "About"
                                    )
                                },
                                label = { Text(AppTab.ABOUT.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OrangeDark,
                                    selectedTextColor = Color.White,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.White.copy(alpha = 0.65f),
                                    unselectedTextColor = Color.White.copy(alpha = 0.65f)
                                ),
                                modifier = Modifier.testTag(AppTab.ABOUT.tag)
                            )
                        }
                    }
                ) { innerPadding ->
                    when (currentTab) {
                        AppTab.SEARCH -> {
                            SearchScreen(
                                repository = repository,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppTab.SAVED -> {
                            SavedRecordsScreen(
                                repository = repository,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppTab.DIRECTORY -> {
                            DirectoryScreen(
                                repository = repository,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppTab.ABOUT -> {
                            AboutScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
