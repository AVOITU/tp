package com.example.eni_shop.ui.screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.eni_shop.bo.Article
import com.example.eni_shop.ui.common.TopBar
import com.example.eni_shop.ui.vm.ArticleListViewModel


@Composable
fun ArticleListScreen(
    onClickToDetail: (Long) -> Unit,
    articleListViewModel: ArticleListViewModel = viewModel(factory = ArticleListViewModel.Factory)
) {

    val categories = articleListViewModel.categories
    val articles by articleListViewModel.articles.collectAsState()

    var selectedCategory by rememberSaveable {
        mutableStateOf("")
    }

    val filteredArticles = if (selectedCategory != "") {
        articles.filter {
            it.category == selectedCategory
        }
    } else {
        articles
    }


    Scaffold(modifier = Modifier,
        topBar = { TopBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CategoryFilterChip(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategoryClick = {
                    selectedCategory = it
                }
            )
            ArticleList(articles = filteredArticles, onClickToDetail = onClickToDetail)
        }
    }
}

@Composable
fun CategoryFilterChip(
    categories: List<String>,
    selectedCategory: String,
    onCategoryClick: (String) -> Unit
) {
    LazyRow {
        items(categories) { category ->
            FilterChip(
                modifier = Modifier.padding(4.dp),
                selected = category == selectedCategory,
                onClick = {
                    if (selectedCategory == category) {
                        onCategoryClick("")
                    } else {
                        onCategoryClick(category)
                    }
                },
                leadingIcon = if (category == selectedCategory) {
                    {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done icon"
                        )
                    }
                } else {
                    null
                },
                label = { Text(text = category) }
            )
        }
    }

}


@Composable
fun ArticleList(articles: List<Article>, onClickToDetail: (Long) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(articles) { article ->
            ArticleItem(article, onClickToDetail = onClickToDetail )
        }
    }


}

@Composable
fun ArticleItem(article: Article = Article(),
                onClickToDetail: (Long) -> Unit) {


    Card(
        border = BorderStroke(1.5.dp, Color.Blue),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(4.dp)
            .clickable{
                onClickToDetail(article.id)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = article.name,
                modifier = Modifier
                    .size(80.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
                    .padding(8.dp)
            )

            var paddingBottom by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current.density
            Text(
                modifier = Modifier.padding(bottom = paddingBottom),
                text = article.name,
                textAlign = TextAlign.Justify,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
                onTextLayout = {
                    val lineCount = it.lineCount
                    val height = (it.size.height / density).dp
                    paddingBottom = if (lineCount > 1) 0.dp else height
                }
            )
            Text(text = "${String.format("%.2f", article.price)} €")
        }
    }
}

@Composable
fun floatingActionButton(){
    //floatingActionButton()
}

@Composable
@Preview
fun ArticleListPreview() {
    //ArticleList()
}