package ru.ifmo.client.presentation.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.ifmo.client.App
import ru.ifmo.client.R
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.data.models.User
import ru.ifmo.client.domain.donation.DonateListener
import ru.ifmo.client.presentation.activity.NavigationActivity
import ru.ifmo.client.presentation.presenter.AccountPresenter
import ru.ifmo.client.presentation.presenter.AccountView
import ru.ifmo.client.presentation.recycler.FundAdapter

class AccountFragment : Fragment(), AccountView, DonateListener {

    private val presenter: AccountPresenter by lazy { App.providePresenter(this) as AccountPresenter }

    private lateinit var mainView: View
    private lateinit var usernameTextView: TextView
    private lateinit var balanceTextView: TextView
    private lateinit var addChipsBtn: Button
    private lateinit var fundsRecyclerView: RecyclerView
    private lateinit var logoutBtn: View
    private val adapter = FundAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_account, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        usernameTextView = mainView.findViewById(R.id.username)
        balanceTextView = mainView.findViewById(R.id.balance_view)
        addChipsBtn = mainView.findViewById(R.id.add_chips_btn)
        addChipsBtn.setOnClickListener {
            showDialog()
        }
        logoutBtn = mainView.findViewById(R.id.logout_btn)
        logoutBtn.setOnClickListener {
            (activity as NavigationActivity).logout()
        }
        fundsRecyclerView = mainView.findViewById(R.id.user_funds)
        fundsRecyclerView.adapter = adapter

    }

    private fun showDialog() {
        val editText = EditText(activity).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.add_chips)
            .setView(editText)
            .setPositiveButton(android.R.string.ok
            ) { dialog, _ ->
                val amountValue = editText.text.toString().toInt()
                if (amountValue < 0) {
                    Toast.makeText(activity, R.string.negative_amount, Toast.LENGTH_SHORT).show()
                } else {
                    presenter.addAmount(amountValue)
                    dialog.dismiss()
                }
            }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showUserInfo(user: User) {
        usernameTextView.text = user.login
        balanceTextView.text = user.balance.toString()
    }

    override fun showUserFunds(funds: List<Fund>) {
        adapter.funds = funds as ArrayList<Fund>
        adapter.notifyDataSetChanged()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun requestDonation(fund: Fund) {
        val editText = EditText(activity).apply { inputType = InputType.TYPE_CLASS_NUMBER }
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.donate)
            .setView(editText)
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                val amount = editText.text.toString().toInt()
                if (App.user.balance - amount < 0) {
                    Toast.makeText(activity, R.string.not_enough_money, Toast.LENGTH_SHORT).show()
                } else {
                    presenter.donate(amount, fund)
                    dialog.dismiss()
                }
            }
            .create().show()
    }

    override fun updateFund(fund: Fund) {
        val index = adapter.funds.indexOf(fund)
        adapter.notifyItemChanged(index)
    }
}