package com.example.idollapp.address

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.test.generateAddress

@Preview
@Composable
private fun PreviewManagerAddress() {
    ManagerAddressScreen(addresses = generateAddress()) {
        // start edit address activity
    }
}


@Composable
fun ManagerAddressScreen(
    modifier: Modifier = Modifier,
    addresses: List<UserAddress>,
    onClick: (UserAddress) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(addresses) { index, item ->
            AddressItem(
                modifier = Modifier.clickable { onClick(item) },
                address = item, isDefault = index == 0
            )
        }
    }
}

@Composable
private fun AddressItem(modifier: Modifier = Modifier, address: UserAddress, isDefault: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                    if (isDefault) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "默认",
                            color = Color(0xFFFF8000),
                            fontSize = 11.sp,
                            modifier = Modifier
                                .border(1.dp, Color(0xFFFF8000), RoundedCornerShape(10.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(text = address.address, fontSize = 14.sp, maxLines = 2, minLines = 2)

            }

            Icon(imageVector = Icons.Default.Edit, contentDescription = "")
        }
    }

}
