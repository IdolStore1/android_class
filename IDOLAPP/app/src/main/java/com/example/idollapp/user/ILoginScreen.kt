package com.example.idollapp.user

sealed interface ILoginScreen {

    data object Register : ILoginScreen

    data object Login : ILoginScreen
}