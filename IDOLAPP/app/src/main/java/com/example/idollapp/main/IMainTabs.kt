package com.example.idollapp.main

import com.example.idollapp.R

sealed interface IMainTabs {

    fun title(): String
    fun icon(): Int

    data object Store : IMainTabs {
        override fun title(): String {
            return "商店"
        }

        override fun icon(): Int {
            return R.drawable.ic_store
        }
    }

    data object Idol : IMainTabs {
        override fun title(): String {
            return "爱豆"
        }

        override fun icon(): Int {
            return R.drawable.ic_idol
        }
    }

    data object Cart : IMainTabs {
        override fun title(): String {
            return "购物车"
        }

        override fun icon(): Int {
            return R.drawable.ic_shoppingcart
        }
    }

    data object About : IMainTabs {
        override fun title(): String {
            return "我的"
        }

        override fun icon(): Int {
            return R.drawable.ic_personal
        }
    }

}