package com.example.eni_shop.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.bo.Article
import com.example.eni_shop.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticleDetailViewModel (private  val articleRepository: ArticleRepository) : ViewModel() {

    private val _article = MutableStateFlow(Article())
    val article :StateFlow<Article> = _article

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadArticle(id: Long) : Article? {
        try {
            val article : Article = articleRepository.getArticle(id)
                ?: throw Exception("Article not found")
            _article.value = article
            return article
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
        return null
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return ArticleListViewModel(
                    ArticleRepository()
                ) as T
            }
        }
    }
}