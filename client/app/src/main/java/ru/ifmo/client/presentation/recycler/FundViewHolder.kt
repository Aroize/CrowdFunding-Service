package ru.ifmo.client.presentation.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ifmo.client.R
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.domain.donation.DonateListener

class FundViewHolder(view: View, donateListener: DonateListener): RecyclerView.ViewHolder(view) {

    private var fund = Fund()

    private val fundName = view.findViewById<TextView>(R.id.item_fund_name)
    private val raisedMoney = view.findViewById<TextView>(R.id.raised_value)
    private val limit = view.findViewById<TextView>(R.id.limit_value)
    init {
        view.findViewById<Button>(R.id.donate).apply {
            setOnClickListener {
                donateListener.requestDonation(fund)
            }
        }
    }

    fun bind(fund: Fund) {
        this.fund = fund
        fundName.text = fund.name
        raisedMoney.text = "${fund.raised}"
        if (fund.raiseLimit < 0) {
            limit.text = itemView.context.getString(R.string.no)
        } else {
            limit.text = "${fund.raiseLimit}"
        }
    }
}