package com.pereyrarg11.navigation.presentation.navigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigationBar(
    menuItems: List<BottomNavigationItem>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        menuItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = {
                    onClickItem(index)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(
                                        text = item.badgeCount.toString()
                                    )
                                }
                            } else {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedIndex) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.title,
                        )
                    }
                }
            )
        }
    }
}
