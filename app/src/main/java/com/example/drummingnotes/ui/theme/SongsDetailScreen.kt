package com.example.drummingnotes.ui.theme

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.drummingnotes.data.DrummingNotesViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.drummingnotes.R
import com.example.drummingnotes.Routes
import com.example.drummingnotes.data.Song
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun SongsDetailScreen(
    viewModel: DrummingNotesViewModel,
    navigation: NavController,
    songId: Int
) {
    val song = remember { mutableStateOf(viewModel.songData[songId]) }
    val backgroundColor = remember(song.value.color) {
        try {
            if (song.value.color.isNotEmpty()) {
                Color(android.graphics.Color.parseColor(song.value.color))
            } else {
                Color.Red
            }
        } catch (e: IllegalArgumentException) {
            Color.Red
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .background(backgroundColor.copy(alpha = 0.5f))
            )
        }
        item {
            Box(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth()
                    .background(backgroundColor.copy(alpha = 0.5f))
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.TopStart)
                        .clickable {
                            navigation.popBackStack(
                                Routes.SCREEN_ALL_SONGS,
                                false
                            )
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            song.value.isFavorited = !song.value.isFavorited
                            viewModel.updateSong(song.value)
                        }
                ) {
                    Image(
                        painter = painterResource(id = if (song.value.isFavorited) R.drawable.heart else R.drawable.favorite_border),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Brush.verticalGradient(listOf(backgroundColor.copy(alpha = 0.5f), Color.Black))),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = when (song.value.album) {
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
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                        .aspectRatio(1f)
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(30.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = song.value.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = song.value.subtitle,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Description",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "${song.value.description}\n\nDrummer: ${song.value.drummer}\n\n" +
                                "Difficulty: ${song.value.difficulty}\n\nDuration: ${song.value.duration}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Drum tabs",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val pdfResId = when (song.value.title) {
                                "Smells Like Teen Spirit" -> R.raw.smells_like_teen_spirit_drum_tab
                                "Everlong" -> R.raw.everlong_drum_tab
                                "Umbrella" -> R.raw.umbrella_drum_tab
                                "In Bloom" -> R.raw.in_bloom_drum_tab
                                "Come As You Are" -> R.raw.come_as_you_are_drum_tab
                                "Scentless Apprentice" -> R.raw.scentless_apprentice_drum_tab
                                "Heart Shaped Box" -> R.raw.heart_shaped_box_drum_tab
                                "All Apologies" -> R.raw.all_apologies_drum_tab
                                "YYZ" -> R.raw.yyz_drum_tab
                                "Tom Sawyer" -> R.raw.tom_sawyer_drum_tab
                                "Drain You" -> R.raw.drain_you_drum_tab
                                else -> R.raw.blank_pdf
                            }
                            navigation.navigate("pdfViewerScreen/$pdfResId")
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(
                            text = "Open drum tabs",
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.musical_note),
                            contentDescription = "Note Icon",
                            modifier = Modifier
                                .width(35.dp)
                                .height(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Song",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    YouTubePlayer(youtubeVideoId = song.value.youtubelink,
                        lifecycleOwner = LocalLifecycleOwner.current)
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        }
    }
}

@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {
    // State za kontrolu playera
    val youTubePlayerInstance = remember { mutableStateOf<YouTubePlayer?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { youTubePlayerInstance.value?.play() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            // Pohrani instancu playera u state
                            youTubePlayerInstance.value = youTubePlayer
                            youTubePlayer.cueVideo(
                                youtubeVideoId,
                                0f
                            ) // Video se priprema, ali ne pušta
                        }
                    })
                }
            }
        )
    }
}



