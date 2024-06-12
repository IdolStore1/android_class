package com.example.idollapp.collection

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBar
import kotlinx.coroutines.launch

class CollectionListActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, CollectionListActivity::class.java)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val collectionRepository = remember {
            CollectionRepositoryLocal(
                AppDatabase.getInstance(this).goodsFavoriteDao(),
                AppDatabase.getInstance(context).shoppingCartDao()
            )
        }

        val collectAsLazyPagingItems =
            collectionRepository.getCollectionList().collectAsLazyPagingItems()
        val coroutineScope = rememberCoroutineScope()

        Column {
            TitleBar(title = "我的收藏")
            CollectionListScreen(
                Modifier.fillMaxSize(),
                collections = collectAsLazyPagingItems,
                {
                    coroutineScope.launch {
                        collectionRepository.removeFromCollection(it)
                        collectAsLazyPagingItems.refresh()
                    }
                },
                {
                    coroutineScope.launch {
                        collectionRepository.addToShoppingCart(it)
                    }
                })
        }

    }

}
