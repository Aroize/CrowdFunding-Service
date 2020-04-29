package ru.ifmo.client.presentation.presenter

import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.domain.mvp.MvpView
import java.util.ArrayList

interface FundsView : MvpView {
    fun showFunds(funds: ArrayList<Fund>)
    fun addCreatedFund(result: Fund)
    fun updateFund(fund: Fund)
}