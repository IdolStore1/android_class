package com.example.idollapp.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.idollapp.cart.CartScreen
import com.example.idollapp.settlement.SettlementActivity
import com.example.idollapp.store.idol.IdolStoreScreen
import com.example.idollapp.store.index.IndexStoreScreen
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.EnableEdgeLight
import com.example.idollapp.ui.view.SpacerDivider
import com.example.idollapp.user.userinfo.PersonalScreen
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : BaseComposeActivity() {


    @Composable
    override fun RenderContent() {
        EnableEdgeLight()
        MainTabsScreen(modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
            listOf(IMainTabs.Store, IMainTabs.Idol, IMainTabs.Cart, IMainTabs.About),
            onTitleChange = {

            })

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MainTabsScreen(
        modifier: Modifier = Modifier,
        tabs: List<IMainTabs>,
        onTitleChange: (IMainTabs) -> Unit
    ) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState { tabs.size }

        Column(modifier = modifier) {
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
                when (tabs[it]) {
                    IMainTabs.Store -> {
                        IndexStoreScreen(modifier = Modifier.fillMaxSize())
                    }

                    IMainTabs.Idol -> {
                        IdolStoreScreen(modifier = Modifier.fillMaxSize())
                    }

                    IMainTabs.Cart -> {
                        CartScreen(modifier = Modifier.fillMaxSize()) {
                            Timber.d(" cart items : ${it.map { it.id }}")
                            startActivity(
                                SettlementActivity.start(
                                    context, goodsIds = it.map { it.id }.joinToString(",")
                                )
                            )
                        }
                    }

                    IMainTabs.About -> {
                        PersonalScreen(modifier = Modifier.fillMaxSize())
                    }
                }
            }
            SpacerDivider()
            TabRow(selectedTabIndex = pagerState.pageCount, containerColor = Color.White) {
                tabs.forEachIndexed { index, title ->
                    val selected = pagerState.targetPage == index
                    Tab(
                        selected = selected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index);
                                onTitleChange(title)
                            }
                        },
                        text = {
                            Icon(
                                painter = painterResource(id = title.icon()),
                                contentDescription = title.title(),
                                modifier = Modifier
                                    .size(28.dp)
                                    .scale(if (selected) 1.2f else 1f)
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }

}
