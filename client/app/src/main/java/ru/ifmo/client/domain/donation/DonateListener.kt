package ru.ifmo.client.domain.donation

import ru.ifmo.client.data.models.Fund

interface DonateListener {
    fun requestDonation(fund: Fund)
}