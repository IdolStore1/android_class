package com.example.idollapp.search

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBar

class SearchResultActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context, searchKey: String): Intent {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra("searchKey", searchKey)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val searchKey = intent.getStringExtra("searchKey") ?: ""
        Column {
            TitleBar(title = "搜索")
            SearchScreen(modifier = Modifier.fillMaxSize(), key = searchKey)
        }
    }

}
