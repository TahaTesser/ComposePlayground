package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PullToRefreshScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePlaygroundTheme {
        PullToRefreshScreen(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun PullToRefreshScreen(modifier: Modifier = Modifier) {
    var items by remember { mutableStateOf(listOf("Item 1", "Item 2", "Item 3")) }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    PullToRefreshBasicSample(
        items = items, isRefreshing = isRefreshing, onRefresh = {
            isRefreshing = true

            coroutineScope.launch {
                delay(2000)

                items = listOf("New Item 1", "New Item 2", "New Item 3", "New Item 4")

                isRefreshing = false
            }


        }, modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshBasicSample(
    items: List<String>, isRefreshing: Boolean, onRefresh: () -> Unit, modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing, onRefresh = onRefresh, modifier = modifier
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(items) {
                ListItem({ Text(text = it) })
            }
        }
    }
}