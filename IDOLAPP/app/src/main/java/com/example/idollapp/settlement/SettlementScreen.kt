package com.example.idollapp.settlement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.address.AddressSettingDialog
import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.test.generateAddress
import com.example.idollapp.test.generateCartItems
import com.example.idollapp.ui.view.LoadingImage
import com.example.idollapp.utils.toFormatString
import kotlinx.coroutines.launch


@Preview
@Composable
private fun PreviewSettlement() {
    SettlementScreen(
        totalQuantity = 234,
        totalPrice = 53.9,
        addresses = generateAddress(),
        selectedAddress = generateAddress().first(),
        onSelectedAddress = {},
        cartItems = generateCartItems()
    ) {}
}


@Composable
fun SettlementScreen(
    modifier: Modifier = Modifier,
    addresses: List<UserAddress>,
    selectedAddress: UserAddress?,
    onSelectedAddress: (UserAddress) -> Unit,
    totalQuantity: Int,
    totalPrice: Double,
    cartItems: List<CartItem>,
    onSubmit: () -> Unit
) {

    Column(modifier = modifier) {

        // 地址
        AddressSelector(selectedAddress, addresses) {
            onSelectedAddress(it)
        }

        // 商品列表
        LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(8.dp)) {
            items(cartItems) { cartItem ->
                SettlementItem(cartItem = cartItem)
            }
        }

        // 结算信息
        SettlementInfo(
            totalQuantity,
            totalPrice,
            onSubmit = { onSubmit() }
        )

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSelector(
    address: UserAddress?,
    addresses: List<UserAddress>,
    onSelected: (UserAddress) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    AddressSettingDialog(sheetState, addresses, onSelected)

    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clickable { coroutineScope.launch { sheetState.show() } },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "")
            Spacer(modifier = Modifier.width(16.dp))
            address?.let {
                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(
                            text = address.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = address.phone, fontSize = 14.sp)
                    }
                    Text(text = address.address, fontSize = 14.sp, maxLines = 2, minLines = 2)
                }
            } ?: let {
                Text(text = "请添加收货地址", fontSize = 14.sp)
            }
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
        }
    }

}


@Composable
private fun SettlementItem(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
) {
    Card(
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            LoadingImage(
                model = cartItem.item.pic,
                modifier = Modifier
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

            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "￥ ${cartItem.item.price.toFormatString()}  ",
                    fontSize = 14.sp,
                )
                Text(
                    text = "x ${cartItem.quantity}  ",
                    fontSize = 14.sp,
                )
                Text(
                    text = "小计 : ￥ ${(cartItem.item.price * cartItem.quantity).toFormatString()}  ",
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
private fun SettlementInfo(
    totalQuantity: Int,
    totalPrice: Double,
    onSubmit: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFf9f9f9))
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "合计", fontWeight = FontWeight.SemiBold)
        Text(
            text = " ￥ ${totalPrice.toFormatString()}",
            fontSize = 16.sp,
            color = Color(0xFFAB0000)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = { onSubmit() }) {
            Text(text = "提交订单")
        }

    }
}













