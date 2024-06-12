package com.example.idollapp.address

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.user.usermanager.AppUserManager

@Composable
fun rememberAddressRepository(): AddressRepositoryLocal {
    val context = LocalContext.current
    val repository = remember {
        val userId = AppUserManager.instance().getUserInfo()?.id ?: ""
        AddressRepositoryLocal(
            AppDatabase.getInstance(context).userAddressDao(),
            userId
        )
    }
    return repository
}