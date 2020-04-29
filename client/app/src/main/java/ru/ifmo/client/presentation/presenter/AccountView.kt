package ru.ifmo.client.presentation.presenter

import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.data.models.User
import ru.ifmo.client.domain.mvp.MvpView

interface AccountView : MvpView {
    fun showUserInfo(user: User)

    fun showUserFunds(
        funds: List<Fund>
    )

    fun updateFund(fund: Fund)
}