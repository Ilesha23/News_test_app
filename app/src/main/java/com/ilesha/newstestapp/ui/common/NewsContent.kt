package com.ilesha.newstestapp.ui.common

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ilesha.newstestapp.R
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.model.Source
import com.ilesha.newstestapp.utils.ext.formatToLocaleString
import java.time.Instant
import androidx.core.net.toUri

@Composable
fun NewsContent(
    articles: LazyPagingItems<Article>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val openBrowser: (String) -> Unit = remember(context) {
        { url ->
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }

    val loadStateLocal = articles.loadState.mediator?.refresh
    when {
        loadStateLocal is LoadState.Error -> {
            NewsContentMessageBox(
                stringResId = R.string.search_screen_search_error,
                modifier = modifier
            )
        }

        loadStateLocal is LoadState.NotLoading && articles.itemCount == 0 -> {
            NewsContentMessageBox(
                stringResId = R.string.search_screen_news_not_found,
                modifier = modifier
            )
        }

        loadStateLocal is LoadState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
            ) {
                CircularProgressIndicator()
            }
        }

        loadStateLocal == null -> {
            NewsContentMessageBox(
                stringResId = R.string.search_screen_empty_list_placeholder,
                modifier = modifier
            )
        }

        else -> {
            NewsList(
                articles = articles,
                loadState = articles.loadState.append,
                onArticleClick = openBrowser,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun NewsContentMessageBox(
    @StringRes stringResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(stringResId),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NewsList(
    articles: LazyPagingItems<Article>,
    loadState: LoadState,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        items(
            count = articles.itemCount,
            key = articles.itemKey { it.url }
        ) { index ->
            articles[index]?.let { article ->
                ArticleCardItem(
                    article = article,
                    onArticleClick = onArticleClick
                )
            }
        }
        item {
            if (loadState is LoadState.Loading) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ArticleCardItem(
    article: Article,
    onArticleClick: (url: String) -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .clickable(
                onClick = {
                    onArticleClick(article.url)
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = article.source.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .padding(vertical = 8.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .build(),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = article.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxSize()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = article.author,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = article.publishedAt?.formatToLocaleString() ?: "",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleCardItemPreview() {
    ArticleCardItem(
        Article(
            source = Source(
                id = "some source",
                name = "Source Name"
            ),
            author = "Name Surname Name",
            title = "Today's top news in your country or something interesting",
            description = "some description about news",
            url = "https://longlonglink.com",
            urlToImage = "https://longlonglink.com",
            publishedAt = Instant.now(),
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eget dolor ultricies, varius magna eget, venenatis arcu. Vivamus libero sem, blandit vel tincidunt vitae, bibendum ac diam. Fusce vitae orci a nisl iaculis ultrices. Etiam ut congue tellus. Donec faucibus tortor vitae ligula pretium, eu molestie orci tempus. Cras porta arcu nisi, nec pretium est hendrerit id. Pellentesque posuere arcu non lorem efficitur, et pellentesque risus euismod. Quisque blandit fringilla sapien a efficitur. Curabitur viverra nisl sit amet aliquam pretium. Morbi egestas, libero ac tincidunt bibendum, velit ligula vulputate nunc, a suscipit sapien odio at purus. Nunc sed pretium ligula, eget accumsan nibh. Duis egestas laoreet dolor, non ultrices turpis feugiat vitae. Aenean odio dui, luctus vitae facilisis eget, ullamcorper ut magna. Etiam vehicula sit amet eros vitae volutpat. In feugiat, diam et molestie laoreet, tellus purus posuere neque, vel ullamcorper orci enim sagittis ipsum. Sed ac volutpat dui, vel dignissim erat.",
        ),
        onArticleClick = {}
    )
}