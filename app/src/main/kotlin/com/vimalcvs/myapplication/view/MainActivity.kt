package com.vimalcvs.myapplication.view


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.vimalcvs.myapplication.ErrorScreen
import com.vimalcvs.myapplication.LoadingScreen
import com.vimalcvs.myapplication.R
import com.vimalcvs.myapplication.Toolbar
import com.vimalcvs.myapplication.ToolbarBack
import com.vimalcvs.myapplication.model.ModelPost
import com.vimalcvs.myapplication.repository.AppNavigation
import com.vimalcvs.myapplication.ui.theme.MyApplicationTheme
import com.vimalcvs.myapplication.viewmodel.PostViewModel
import com.vimalcvs.myapplication.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: PostViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Column {
        Toolbar(title = "Home")
        when (uiState) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Error -> ErrorScreen(
                message = (uiState as UiState.Error).message,
                onRetryClick = {
                    viewModel.getPosts()
                })
            is UiState.Success -> PostList(
                posts = (uiState as UiState.Success).posts,
                navController = navController
            )
        }
    }
}

@Composable
fun PostList(posts: List<ModelPost>, navController: NavHostController) {
    LazyColumn {
        items(posts) { post ->
            ListItem(post, navController)
        }
    }
}

@Composable
fun ListItem(post: ModelPost, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                val postJson = Uri.encode(Gson().toJson(post))
                navController.navigate("post/$postJson")
            }
    ) {
        Text(
            text = post.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun DetailScreen(post: ModelPost, navController: NavHostController) {
    Column {
        ToolbarBack(
            title = post.title,
            onBackClick = { navController.popBackStack() }
        )
        Text(
            text = post.body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1000L)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth(0.3f),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}