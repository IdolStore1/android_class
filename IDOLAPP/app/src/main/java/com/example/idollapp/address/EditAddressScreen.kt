package com.example.idollapp.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.ui.view.rememberStringState

@Preview
@Composable
private fun PreviewAddAddress() {
    EditAddressScreen("", "", "", onAddAddress = { name, phone, address ->

    })
}

@Preview
@Composable
private fun PreviewEditAddress() {
    EditAddressScreen(
        "hahaha",
        "18818818818",
        "asfhwpofhnasfnzxv;erhwgipuahrf",
        onAddAddress = { name, phone, address ->

        })
}

@Composable
fun EditAddressScreen(
    nameSrc: String, phoneSrc: String, addressSrc: String,
    onAddAddress: (name: String, phone: String, address: String) -> Unit,
) {
    val name = rememberStringState(nameSrc)
    val phone = rememberStringState(phoneSrc)
    val address = rememberStringState(addressSrc)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        AddAddressInputField(
            text = "收货人",
            input = name.get(),
            onChange = { name.update(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        AddAddressInputField(
            text = "手机号码",
            input = phone.get(),
            onChange = { phone.update(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        AddAddressInputField(
            text = "详细地址",
            input = address.get(),
            onChange = { address.update(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAddAddress(name.get(), phone.get(), address.get()) }) {
                Text("保存")
            }
        }
    }
}

@Composable
private fun AddAddressInputField(
    modifier: Modifier = Modifier,
    text: String,
    input: String,
    onChange: (String) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = input,
            onValueChange = { onChange(it) },
            modifier = Modifier.weight(3f)
        )
    }
}