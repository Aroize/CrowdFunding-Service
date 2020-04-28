package ru.ifmo.client.presentation.presenter

import ru.ifmo.client.domain.mvp.BasePresenter

class AccountPresenter : BasePresenter<AccountView>() {
    override var viewState: AccountView? = null

    override fun onAttach() {

    }

    override fun onDetach() {

    }
}