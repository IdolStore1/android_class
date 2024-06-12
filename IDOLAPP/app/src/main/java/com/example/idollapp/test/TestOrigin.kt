package com.example.idollapp.test

import kotlin.random.Random

val testGoodsNames = listOf(
    "名信片", "闪光棒", "小纸条", "定制灯牌", "彩灯", "台历", "抱枕",
    "杯子", "记事本", "雨伞", "背包", "书", "手链", "钥匙链",
    "杯子杯子杯子杯子杯子杯子杯子", "记事本记事本记事本记事本记事本",
    "雨伞雨伞雨伞雨伞雨伞雨伞雨伞雨伞", "背包背包背包背包背包背包",
    "书书书书书书书书", "手链手链手链手链手链手链手链手链手链手链手链手链手链手链",
    "钥匙链钥匙链钥匙链钥匙链钥匙链", "项链", "扭蛋", "挂卡", "模型", "手办"
)

val testGoodsNamesSku = listOf(
    "颜色红", "颜色绿", "颜色蓝", "颜色紫", "颜色黄", "颜色黑", "颜色白",
    "尺寸大", "尺寸中", "尺寸小", "尺寸大", "尺寸中", "尺寸小",
)

val testGoodsPrices = listOf(
    11.3f, 23.4f, 12.3f, 34.5f, 23.4f, 12.3f, 34.5f,
    23.4f, 12.3f, 34.5f, 23.4f, 12.3f, 34.5f, 23.4f,
    12.3f, 34.5f, 23.4f, 12.8f, 34.5f, 23.4f, 12.3f,
    34.5f, 23.4f, 12.3f
)

val testGoodsPics = listOf(
    "https://source.unsplash.com/random/500x300",
    "https://source.unsplash.com/random/500x300",
//    "https://cdn.seovx.com/?mom=302",
//    "https://cdn.seovx.com/ha/?mom=302",
//    "https://cdn.seovx.com/d/?mom=302",
//    "https://tuapi.eees.cc/api.php?category=fengjing",
//    "https://tuapi.eees.cc/api.php?category=dongman",
)

fun randomGoodsPic(): String {
    return testGoodsPics.random() + "?r=${Random.nextInt(100)}"
}

val testCategorys = listOf(
    "全部", "名品", "生活", "手办", "玩偶", "海报", "卡片", "服饰", "专辑",
)

val testUserName = listOf(
    "小明",    "小红",    "小刚",    "小李",    "小王",    "小赵",    "小钱",    "小孙",    "小李",)

val testUserPhone = listOf(
    "12345678901",
    "12345678902",
    "12345678903",
    "12345678904",
    "12345678905",
    "12345678906",
    "12345678907",
    "12345678908",
    "12345678909",
    "12345678910",
)

val testAddress = listOf(
    "北京市朝阳区望京街道15号1号楼",
    "北京市朝阳区望京街道15号2号楼",
    "北京市朝阳区望京街道15号3号楼",
    "北京市朝阳区望京街道15号4号楼",
    "北京市朝阳区望京街道15号5号楼",
    "北京市朝阳区望京街道15号6号楼",
    "北京市朝阳区望京街道15号7号楼",
    "北京市朝阳区望京街道15号8号楼",
    "广州市天河区街道15号9号楼",
    "广州市天河区街道15号10号楼",
    "广州市天河区街道15号11号楼",
    "广州市天河区街道15号12号楼",
)

val testRemark = listOf(
    "不要打折",
    "不要打折",
    // 一堆随机文字，16字左右，每段不一样
    "这是一段随机文字1",
    "这是一段随机文字2",
    "这是一段随机文字3，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字，这是一段随机文字",
    "这是一段随机文字5",
    "这是一段随机文字6",
    "这是一段随机文字7",
    "这是一段随机文字8",
    "这是一段随机文字9",
    "这是一段随机文字10",
    "这是一段随机文字11",
)

val testDate = listOf(
    "2021-01-01",
    "2021-01-02",
    "2021-01-03",
    "2021-01-04",
    "2021-01-05",
    "2021-01-06",
    "2021-01-07",
)