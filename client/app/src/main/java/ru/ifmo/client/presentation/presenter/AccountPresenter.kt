package ru.ifmo.client.presentation.presenter

import android.util.Log
import ru.ifmo.client.App
import ru.ifmo.client.api.ApiCallback
import ru.ifmo.client.api.ApiManager
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.data.models.User
import ru.ifmo.client.data.requests.AddAmountRequest
import ru.ifmo.client.data.requests.DonateRequest
import ru.ifmo.client.data.requests.UserFundsRequest
import ru.ifmo.client.data.requests.UserGetRequest
import ru.ifmo.client.domain.mvp.BasePresenter
import ru.ifmo.client.presentation.activity.NavigationActivity.Companion.TAG

class AccountPresenter : BasePresenter<AccountView>() {

    private var cachedUser = App.user

    private var cachedFunds = listOf<Fund>()

    override var viewState: AccountView? = null

    override fun onAttach() {
        if (ApiManager.accessToken.userId != cachedUser.uid) {
            //Should request User info
            requestUserInfo()
        } else {
            viewState?.showUserInfo(cachedUser)
            //User is cached so need to check his funds
            if (cachedFunds.isNotEmpty()) {
                //Maybe user has funds
                viewState?.showUserFunds(cachedFunds)
            }
            requestUserFunds()
        }
    }

    private fun requestUserFunds() {
        val callback = object : ApiCallback<List<Fund>> {
            override fun onError(e: Throwable) {
                handleRequestError("requestUserFunds", e)
            }

            override fun onSuccess(result: List<Fund>) {
                Log.d(TAG, "successfully got user's funds. count = ${result.size}")
                cachedFunds = result as ArrayList<Fund>
                viewState?.showUserFunds(cachedFunds)
            }
        }
        val request = UserFundsRequest(cachedUser.uid)
        ApiManager.execute(request, callback)
    }

    private fun requestUserInfo() {
        val callback = object : ApiCallback<User> {
            override fun onError(e: Throwable) {
                handleRequestError("requestUserInfo", e)
            }

            override fun onSuccess(result: User) {
                Log.d(TAG, "successfully got user = $result")
                cachedUser.apply {
                    uid = result.uid
                    balance = result.balance
                    login = result.login
                }
                viewState?.showUserInfo(cachedUser)
                requestUserFunds()
            }
        }
        val request = UserGetRequest(ApiManager.accessToken.userId)
        ApiManager.execute(request, callback)
    }

    override fun onDetach() {}

    fun addAmount(amountValue: Int) {
        val request = AddAmountRequest(cachedUser.uid, amountValue)
        val callback = object : ApiCallback<Int> {
            override fun onError(e: Throwable) {
                handleRequestError("addAmount", e)
            }

            override fun onSuccess(result: Int) {
                cachedUser.balance += amountValue
                Log.d(TAG, "current user state = $cachedUser; response = $result")
                viewState?.showUserInfo(cachedUser)
            }
        }
        ApiManager.execute(request, callback)
    }

    private fun handleRequestError(methodName: String, e: Throwable) {
        Log.d(TAG, "Exception while running method \"$methodName\" request; message=${e.message}", e)
        val message = e.message
        if (message != null)
            viewState?.showErrorMessage(message)
    }

    fun donate(amount: Int, fund: Fund) {
        val request = DonateRequest(ApiManager.accessToken.userId, amount, fund.id)
        val callback = object : ApiCallback<Int> {
            override fun onError(e: Throwable) {
                handleRequestError("donate", e)
            }

            override fun onSuccess(result: Int) {
                cachedUser.balance -= amount
                fund.raised += amount
                viewState?.updateFund(fund)
                viewState?.showUserInfo(cachedUser)
            }
        }
        ApiManager.execute(request, callback)
    }
}