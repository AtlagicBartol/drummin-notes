package com.example.drummingnotes.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.drummingnotes.R
import com.example.drummingnotes.Routes
import com.example.drummingnotes.data.DrummingNotesViewModel
import com.example.drummingnotes.data.Song

@Composable
fun SongsScreen(
    modifier: Modifier = Modifier,
    navigation : NavController,
    viewModel: DrummingNotesViewModel
) {
    var currentActiveButton by remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LibraryHeader()

        LibraryFilters(
            currentActiveButton = currentActiveButton,
            onCategorySelected = { selectedButton ->
                currentActiveButton = selectedButton
            }
        )


        Text(
            text = "Songs",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
        )

        SongsList(viewModel,navigation,currentActiveButton)

    }
}


@Composable
fun SongsList(
    viewModel: DrummingNotesViewModel,
    navigation: NavController,
    currentCategory: Int
) {
    val category = when (currentCategory) {
        0 -> "Rock"
        1 -> "Pop"
        2 -> "Jazz"
        3 -> "Liked Songs"
        else -> ""
    }

    val filteredSongs = if (currentCategory < 3) {
        viewModel.songData.filter { it.category == category }
    } else {
        viewModel.songData.filter { it.isFavorited }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(filteredSongs.sortedBy { it.album }) { song ->
            LibraryItemRow(
                item = LibraryItem(
                    title = song.title,
                    subtitle = song.subtitle,
                    album = song.album,
                    difficulty = song.difficulty
                )
            ) {
                navigation.navigate(
                    Routes.getSongDetailsPath(viewModel.songData.indexOf(song))
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}






@Composable
fun LibraryItemRow(item: LibraryItem,
                   onClick: () -> Unit,) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = when (item.album) {
                        "nevermind" -> R.drawable.nirvana_nevermind
                        "color_and_the_shape" -> R.drawable.color_and_the_shape
                        "Good Girl Gone Bad" -> R.drawable.good_girl_gone_bad
                        "in_utero" -> R.drawable.in_utero
                        "moving_pictures" -> R.drawable.moving_pictures
                        "red" -> R.drawable.red
                        else -> R.drawable.no_drums
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.subtitle,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(
                    id = when (item.difficulty) {
                        "beginner" -> R.drawable.beginner_level
                        "Intermediate" -> R.drawable.intermediate_level
                        "expert" -> R.drawable.expert_level
                        else -> R.drawable.no_drums
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(30.dp)
            )
        }

    }
}


data class LibraryItem(
    val title: String,
    val subtitle: String,
    val album: String,
    val difficulty: String
)


@Composable
fun LibraryHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your Library",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun TabButton(modifier: Modifier = Modifier,text: String, isActive: Boolean,onClick: () -> Unit) {
    Button(shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if(isActive) Color.White else Color.DarkGray,
            contentColor = if (isActive) Color.Black else Color.White),
        onClick = { onClick() }){
        Text( text,
            color = if(isActive) Color.Black else Color.White,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun LibraryFilters(currentActiveButton: Int, onCategorySelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TabButton(text = "Rock", isActive = currentActiveButton == 0) { onCategorySelected(0) }
        TabButton(text = "Pop", isActive = currentActiveButton == 1) { onCategorySelected(1) }
        TabButton(text = "Jazz", isActive = currentActiveButton == 2) { onCategorySelected(2) }
        TabButton(text = "Liked Songs", isActive = currentActiveButton == 3) { onCategorySelected(3) }
    }
}
