package com.example.idollapp.address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.test.generateAddress
import com.example.idollapp.ui.view.ViewSpacer
import com.example.idollapp.ui.view.ViewSpacerWeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewAddressDialog() {
    AddressSettingDialog(addresses = generateAddress()) {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSettingDialog(
    sheetState: SheetState = rememberModalBottomSheetState(),
    addresses: List<UserAddress>,
    onSelected: (UserAddress) -> Unit
) {
    if (sheetState.isVisible) {
        val coroutineScope = rememberCoroutineScope()
        ModalBottomSheet(
            sheetState = sheetState,
            dragHandle = {},
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            }) {
            AddressContent(addresses, onClose = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            }, onSelected = onSelected)
        }
    }
}

@Composable
private fun AddressContent(
    addresses: List<UserAddress>,
    onClose: () -> Unit,
    onSelected: (UserAddress) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
            .padding(20.dp, 25.dp)
    ) {

        AddressSettingTitle { onClose() }

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = Color(0xffD6D6D6))
        )

        LazyColumn {
            if (addresses.isNotEmpty()) {

                itemsIndexed(addresses) { index, item ->
                    AddressItem(modifier = Modifier.clickable {
                        onSelected(item)
                        onClose()
                    }, address = item, isCheck = false)
                }
            } else {
                item {
                    Text(
                        text = "暂无收货地址", modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), textAlign = TextAlign.Center
                    )
                }
            }
            item {
                Column {
                    ViewSpacer(size = 10)
                    OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        context.startActivity(AddAddressActivity.start(context))
                    }) {
                        Text(text = "添加收货地址")
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressItem(modifier: Modifier = Modifier, address: UserAddress, isCheck: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "")

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {

                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = address.name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = address.phone, fontSize = 14.sp)
                }

                Text(text = address.address, fontSize = 14.sp, maxLines = 2, minLines = 2)

            }

            if (isCheck) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "")
            }
        }
    }

}

@Composable
private fun AddressSettingTitle(onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "收货地址",
            style = MaterialTheme.typography.titleMedium
        )

        ViewSpacerWeight()

        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            modifier = Modifier.clickable {
                onClose()
            }
        )
    }
}
