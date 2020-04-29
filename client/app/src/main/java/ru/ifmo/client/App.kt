package ru.ifmo.client

import android.app.Application
import ru.ifmo.client.data.models.User
import ru.ifmo.client.domain.mvp.BasePresenter
import ru.ifmo.client.domain.mvp.MvpView
import ru.ifmo.client.presentation.presenter.AccountPresenter
import ru.ifmo.client.presentation.presenter.AccountView
import ru.ifmo.client.presentation.presenter.FundsPresenter
import ru.ifmo.client.presentation.presenter.FundsView

class App : Application() {
    companion object {

        var user = User()

        private val accountPresenter = AccountPresenter()

        private val fundsPresenter = FundsPresenter()

        fun <T : MvpView> providePresenter(view: T): BasePresenter<*> {
            return when (view) {
                is AccountView ->  accountPresenter
                is FundsView -> fundsPresenter
                else -> throw IllegalArgumentException("No presenter for this class ${view.javaClass}")
            }
        }
    }
}