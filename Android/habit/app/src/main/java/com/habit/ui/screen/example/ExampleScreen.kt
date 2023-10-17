package com.habit.ui.screen.example


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.habit.R
import com.habit.domain.model.example.Post
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "ExampleScreen_싸피"

@Composable
fun ExampleScreen(navController: NavHostController, viewModel: ExampleViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.background
    val configuration = LocalConfiguration.current
    val firstPost by viewModel.firstPost.collectAsState()
    val postList by viewModel.postList.collectAsState()

    LaunchedEffect(Unit) {
        //viewModel.getFirstPost()
    }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }
    firstPost?.let { CardElevation(it.title) }
//    postList?.let{
//        postListView(postList = it)
//    }

}


@Composable
fun postListView(
    modifier: Modifier = Modifier,
    postList: List<Post?>
) {
    LazyColumn(
        modifier = modifier.height(((postList.size * 40) + 40).sdp),
    ) {
        items(postList.size) {
            Column {
                postList[it]?.let { it1 ->
                    CardElevation(
                        title = it1.title
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun CardElevation(
    title : String = "no title yet"
) {
    Surface(
        shape = RoundedCornerShape(8.sdp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(100.sdp)
            .padding(4.sdp),
        shadowElevation = 4.sdp
    ) {
        Row(
            modifier = Modifier.padding(8.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.sdp),
                    modifier = Modifier.wrapContentSize(),
                    color = Color(0xFFD1D5E1)
                ) {
                    Text(
                        text = "title",
                        fontSize =  6.ssp,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 2.sdp, horizontal = 4.sdp)
                    )
                }

                Spacer(modifier = Modifier.height(2.sdp))

                Text(
                    text = title,
                    fontSize =  12.ssp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(1.sdp))

                Text(text = "Price : 300$")

                Spacer(modifier = Modifier.height(1.sdp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "4.0",
                        fontSize =  7.ssp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(2.sdp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        tint = Color(0xFFF6B266),
                        contentDescription = null
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        tint = Color(0xFFF6B266),
                        contentDescription = null
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        tint = Color(0xFFF6B266),
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        tint = Color(0xFFF6B266),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(2.sdp))

                OutlinedButton(
                    shape = RoundedCornerShape(4.sdp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Read More",
                        fontSize =  5.ssp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier.size(width = 50.sdp, height = 70.sdp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
    }
}


//@Composable
//fun MovieListContent(allMovies: LazyPagingItems<Movie>, navController: NavHostController) {
//    LazyColumn(
//        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
//    ) {
//        items(
//            items = allMovies,
//            key = { movie ->
//                movie.pk
//            }
//        ) { movie ->
//            if (movie != null) {
//                MovieListItem(movie = movie, navController = navController)
//            }
//        }
//    }
//}




