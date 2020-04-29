package ru.ifmo.client.presentation.fragment

import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.presentation.presenter.FundsView
import java.util.ArrayList

class FundsFragment : Fragment(), FundsView {
    override fun addCreatedFund(result: Fund) {
//        TODO("Not yet implemented")
    }

    override fun showFunds(funds: ArrayList<Fund>, addedSize: Int) {
//        TODO("Not yet implemented")
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}