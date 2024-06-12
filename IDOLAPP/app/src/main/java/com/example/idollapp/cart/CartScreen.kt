package com.example.idollapp.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.goods.CartItem
import com.example.idollapp.test.generateCartItems
import com.example.idollapp.ui.base.CommonLoadingLayout
import com.example.idollapp.ui.view.LoadingImage
import com.example.idollapp.ui.view.SwipeBox
import com.example.idollapp.ui.view.ViewSpacerWeight
import com.example.idollapp.ui.view.rememberBooleanState
import com.example.idollapp.ui.view.showShotToast
import timber.log.Timber
import java.util.Locale

@Preview
@Composable
private fun PreviewShoppingCart() {
    val cartItems = generateCartItems()
    val checkedList = remember { mutableStateListOf<Int>() }
    Column(Modifier.fillMaxSize()) {
        CartSummary(modifier = Modifier.weight(1f),
            cartItems = cartItems,
            checkedIdList = checkedList,
            onCheckChange = { item, checked ->
                if (checked) {
                    checkedList.add(item.id)
                } else {
                    checkedList.remove(item.id)
                }
            },
            onAddToCart = { },
            onRemoveFromCart = { },
            onRemoveAllFromCart = { })
        SettlementLayout(234, "￥", 293487.9, {

        }) {

        }
    }
}


@Composable
fun CartScreen(modifier: Modifier = Modifier, onSettlement: (List<CartItem>) -> Unit) {
    val viewModel = createShoppingCartViewModel()
    val data = viewModel.getCartItems().collectAsState().value
    val context = LocalContext.current
    CommonLoadingLayout(modifier = Modifier.fillMaxSize(), data = data) { cartItems ->
        val checkedIdList = remember { mutableStateListOf<Int>() }
        val checkedItems = cartItems.filter { it.id in checkedIdList }
        val totalPrice = checkedItems.sumOf { it.item.price.toDouble() * it.quantity }
        val totalQuantity = checkedItems.sumOf { it.quantity }
        Timber.d(" cartItems        : ${cartItems.toList()}")
        Timber.d(" checkedIdList    : ${checkedIdList.toList()}")
        Timber.d(" checkedItems     : $checkedItems")
        Timber.d(" totalPrice       : $totalPrice")
        Timber.d(" totalQuantity    : $totalQuantity")
        Column(Modifier.fillMaxSize()) {
            if (cartItems.isEmpty()) {
                Text(
                    text = "购物车空空如也~",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
            } else {
                CartSummary(modifier = modifier.weight(1f),
                    cartItems = cartItems,
                    checkedIdList = checkedIdList,
                    onCheckChange = { item, checked ->
                        if (checked) {
                            checkedIdList.add(item.id)
                        } else {
                            checkedIdList.remove(item.id)
                        }
                    },
                    onAddToCart = { viewModel.addToCart(it) },
                    onRemoveFromCart = { viewModel.removeFromCart(it) },
                    onRemoveAllFromCart = { viewModel.removeGoodsFromCart(it) })
            }
            SettlementLayout(totalQuantity, "￥", totalPrice, onSettlement = {
                if (checkedIdList.isEmpty()) {
                    context.showShotToast("请选择结算商品")
                } else {
                    onSettlement(checkedItems)
                }
            }, onAllChecked = {
                checkedIdList.clear()
                if (it) {
                    cartItems.map { it.id }.let {
                        checkedIdList.addAll(it)
                    }
                }
            })
        }
    }
}


@Composable
private fun CartSummary(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    checkedIdList: List<Int>,
    onCheckChange: (CartItem, Boolean) -> Unit,
    onAddToCart: (CartItem) -> Unit,
    onRemoveFromCart: (CartItem) -> Unit,
    onRemoveAllFromCart: (CartItem) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(cartItems) { cartItem ->
            val checked = rememberBooleanState()
            SwipeBox(checked = checked.isTrue(),
                onCheckedChange = { checked.update(it) },
                bottomContent = {
                    Box(
                        modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { onRemoveAllFromCart(cartItem);checked.beFalse() },
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier
                            )
                        }
                    }
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = checkedIdList.contains(cartItem.id),
                        onCheckedChange = {
                            onCheckChange(cartItem, it)
                        })
                    CartItemRow(
                        cartItem = cartItem,
                        onAddToCart = { onAddToCart(cartItem) },
                        onRemoveFromCart = { onRemoveFromCart(cartItem) }
                    )
                }
            }
        }
    }

}

@Composable
private fun SettlementLayout(
    totalQuantity: Int,
    currencySymbol: String,
    totalPrice: Double,
    onSettlement: () -> Unit,
    onAllChecked: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFf1f1f1))
            .padding(end = 16.dp)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val allChecked = rememberBooleanState()
        Checkbox(
            checked = allChecked.get(),
            onCheckedChange = { allChecked.toggle();onAllChecked(allChecked.get()) })
        Text(text = "全选")
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "共 $totalQuantity 件 ", color = Color(0xff999999), fontSize = 11.sp)
        Text(text = "合计", fontWeight = FontWeight.SemiBold)
        Text(
            text = "${currencySymbol} ${String.format(Locale.getDefault(), "%.2f", totalPrice)}",
            fontSize = 16.sp,
            color = Color(0xFFAB0000)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = { onSettlement() }) {
            Text(text = "结算")
        }
    }
}


@Composable
private fun CartItemRow(
    cartItem: CartItem, onAddToCart: () -> Unit, onRemoveFromCart: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            LoadingImage(
                model = cartItem.item.pic,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(top = 16.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = cartItem.item.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    minLines = 2
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "￥ ${cartItem.item.price}  ",
                    fontSize = 14.sp,
                )

                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    ViewSpacerWeight()
                    IconButton(
                        onClick = onRemoveFromCart,
                        modifier = Modifier,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xfff9f9f9)
                        )
                    ) {
                        Text(text = "一", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Text(
                        text = cartItem.quantity.toString(), modifier = Modifier.padding(8.dp)
                    )

                    IconButton(
                        onClick = onAddToCart,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(
                                0xfff9f9f9
                            )
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "Add to cart"
                        )
                    }
                }
            }
        }
    }
}
