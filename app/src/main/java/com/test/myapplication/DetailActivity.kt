package com.test.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.test.myapplication.state.GistDetailState
import com.test.myapplication.ui.theme.MyApplicationTheme
import com.test.myapplication.viewModel.DetailViewModel

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myApplication = application as MyApplication
        val viewModel = myApplication.detailViewModel



        setContent {
            MyApplicationTheme() {
                androidx.compose.material.Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainLayout(viewModel = viewModel!!)
                }
            }
        }
    }

    @Composable
    fun mainLayout(viewModel: DetailViewModel) {
        val uiState by remember(viewModel, "detail"){
          viewModel.flow
        }.collectAsState(initial = GistDetailState(viewModel.gist))

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "id: ${uiState.gist.id}", overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "url: ${uiState.gist?.url}",
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "userName: ${uiState.gist?.userName}",
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "fileName: ${uiState.gist?.fileName}",
                overflow = TextOverflow.Ellipsis
            )
            favouriteDetailPart(
                hasFavourite = uiState.gist?.hasFavourite ?: false,
                onFavourite = { viewModel.onFavourite(uiState.gist) },
                onUnFavourite = { viewModel.onUnFavourite(uiState.gist) }
            )
            if ((uiState.gist?.count ?: 0) > 5) {
                Text(
                    text = "${uiState.gist?.userName} shared ${uiState.gist?.count}",
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.background(Color.LightGray)
                )
            }
        }
    }

    @Composable
    fun favouriteDetailPart(
        hasFavourite: Boolean,
        onFavourite: () -> Unit,
        onUnFavourite: () -> Unit
    ) {
        Log.i("test", "checkUpdateFavouriteUI")
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

}