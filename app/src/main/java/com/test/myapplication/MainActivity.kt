package com.test.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.myapplication.state.GistListContentAvailableState
import com.test.myapplication.ui.theme.MyApplicationTheme
import com.test.myapplication.viewModel.DetailViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myApplication = application as MyApplication
        val gistListViewModel = myApplication.gistListViewModel
        gistListViewModel.start()

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val uiState = gistListViewModel.flow.collectAsState(
                        GistListContentAvailableState(
                            emptyList()
                        )
                    )

                    LazyColumn(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                        items(items = uiState.value.gistList) { item ->
                            Column(modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clickable {
                                    myApplication.detailViewModel = DetailViewModel(
                                        myApplication.fetchGistListUseCase,
                                        myApplication.favouriteGistUseCase,
                                        item
                                    )

                                    goToDetailActivity()
                                }) {
                                Text(text = "id: ${item.id}", overflow = TextOverflow.Ellipsis)
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(text = "url: ${item.url}", overflow = TextOverflow.Ellipsis)
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = "userName: ${item.userName}",
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = "fileName: ${item.fileName}",
                                    overflow = TextOverflow.Ellipsis
                                )
                                favouritePart(
                                    hasFavourite = item.hasFavourite ?: false,
                                    onFavourite = { gistListViewModel.onFavourite(item) },
                                    onUnFavourite = { gistListViewModel.onUnFavourite(item) }
                                )
                                if (item.count > 5) {
                                    Text(
                                        text = "${item.userName} shared ${item.count}",
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.background(Color.LightGray)
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    fun goToDetailActivity() {
        this.startActivity(Intent(this, DetailActivity::class.java))
    }
}

@Composable
fun favouritePart(hasFavourite: Boolean, onFavourite: () -> Unit, onUnFavourite: () -> Unit) {
    if (hasFavourite) {
        IconButton(onClick = onUnFavourite) {
            Icon(Icons.Filled.Delete, contentDescription = "favourite")
        }
    } else {
        IconButton(onClick = onFavourite) {
            Icon(Icons.Outlined.Star, contentDescription = "favourite")
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}