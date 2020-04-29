package ru.ifmo.client.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.ifmo.client.App
import ru.ifmo.client.R
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.domain.donation.DonateListener
import ru.ifmo.client.presentation.presenter.FundsPresenter
import ru.ifmo.client.presentation.presenter.FundsView
import ru.ifmo.client.presentation.recycler.FundAdapter

class FundsFragment : Fragment(), FundsView, DonateListener {

    private val presenter: FundsPresenter by lazy { App.providePresenter(this) as FundsPresenter }

    private lateinit var mainView: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var createFundBtn: ImageView
    private lateinit var fundRecyclerView: RecyclerView
    private val adapter = FundAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_funds, container, false)
        initViews()
        return mainView
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    private fun initViews() {
        refreshLayout = mainView.findViewById(R.id.swipe_refresh)
        createFundBtn = mainView.findViewById(R.id.create_fund)
        createFundBtn.setOnClickListener {
            createFund()
        }
        refreshLayout.setOnRefreshListener {
            presenter.requestFunds()
        }
        fundRecyclerView = mainView.findViewById(R.id.fund_list)
        fundRecyclerView.adapter = adapter
    }

    @SuppressLint("InflateParams")
    private fun createFund() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_fund, null, false)
        val limitEditText = dialogView.findViewById<EditText>(R.id.fund_limit)
        val fundNameEditText = dialogView.findViewById<EditText>(R.id.fund_name)
        val checkBox = dialogView.findViewById<CheckBox>(R.id.check_box)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            limitEditText.isEnabled = isChecked
        }
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.create_fund)
            .setView(dialogView)
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                val fundName = fundNameEditText.text.toString()
                val limit = limitEditText.text.toString()
                if (fundName.isBlank()) {
                    Toast.makeText(activity, R.string.must_specify_fund_name, Toast.LENGTH_SHORT).show()
                } else {
                    val fundLimit = if (limit.isBlank()) -1 else limit.toInt()
                    presenter.createFund(fundName, fundLimit)
                    dialog.dismiss()
                }
            }
            .create()
            .show()
    }

    override fun addCreatedFund(result: Fund) {
        adapter.funds.add(0, result)
        adapter.notifyItemInserted(0)
    }

    override fun showFunds(funds: java.util.ArrayList<Fund>) {
        adapter.funds = funds
        adapter.notifyDataSetChanged()
        refreshLayout.isRefreshing = false
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