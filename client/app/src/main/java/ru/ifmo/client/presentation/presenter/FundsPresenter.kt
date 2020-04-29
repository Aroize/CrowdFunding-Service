package ru.ifmo.client.presentation.presenter

import android.util.Log
import ru.ifmo.client.api.ApiCallback
import ru.ifmo.client.api.ApiManager
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.data.requests.CreateFundRequest
import ru.ifmo.client.data.requests.FundsGetRequest
import ru.ifmo.client.domain.mvp.BasePresenter
import ru.ifmo.client.presentation.activity.NavigationActivity.Companion.TAG

class FundsPresenter : BasePresenter<FundsView>() {

    override var viewState: FundsView? = null

    private var cachedFunds = arrayListOf<Fund>()

    override fun onAttach() {
        if (cachedFunds.isEmpty()) {
            requestFunds()
        }
    }

    private fun requestFunds(count: Int = 20, offset: Int = 0) {
        val request = FundsGetRequest(count, offset)
        val callback = object : ApiCallback<List<Fund>> {
            override fun onError(e: Throwable) {
                handleRequestError("requestFunds", e)
            }

            override fun onSuccess(result: List<Fund>) {
                Log.d(TAG, "successfully got funds. count = ${result.size}")
                val savedSize = cachedFunds.size
                cachedFunds = ArrayList(cachedFunds.union(result))
                viewState?.showFunds(cachedFunds, cachedFunds.size - savedSize)
            }
        }
        ApiManager.execute(request, callback)
    }

    fun createFund(name: String, limit: Int = -1) {
        val request = CreateFundRequest(ApiManager.accessToken.userId, name, limit)
        val callback = object : ApiCallback<Fund> {
            override fun onError(e: Throwable) {
                handleRequestError("createFund", e)
            }

            override fun onSuccess(result: Fund) {
                cachedFunds.add(0, result)
                viewState?.addCreatedFund(result)
            }
        }

    }

    override fun onDetach() {
//        TODO("Not yet implemented")
    }

    private fun handleRequestError(methodName: String, e: Throwable) {
        Log.d(TAG, "Exception while running method\"$methodName\" request; message=${e.message}", e)
        val message = e.message
        if (message != null)
            viewState?.showErrorMessage(message)
    }
}