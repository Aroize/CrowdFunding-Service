package ru.ifmo.client.presentation.recycler

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.ifmo.client.R
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.domain.donation.DonateListener

class FundAdapter(
    private val donateListener: DonateListener
): RecyclerView.Adapter<FundViewHolder>() {

    var funds: ArrayList<Fund> = arrayListOf()

    override fun getItemCount(): Int = funds.size

    override fun onBindViewHolder(holder: FundViewHolder, position: Int) {
        holder.bind(funds[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundViewHolder {
        return FundViewHolder(
            (donateListener as Fragment).layoutInflater.inflate(R.layout.item_fund, parent, false),
            donateListener
        )
    }
}