package ru.ifmo.client.api

object ApiConfig {

    const val SCHEME = "http"

    const val HOST = "10.0.2.2"

    const val PORT = 8080

    val supportedMethods = HashSet<String>().apply {
        add("auth.signUp")
        add("auth.signIn")
        add("auth.logout")
        add("bill.userAddAmount")
        add("bill.donate")
        add("bill.getBalance")
        add("fund.create")
        add("fund.getRaised")
        add("fund.get")
        add("user.get")
        add("user.funds")
    }

    val requiresToken = supportedMethods.subtract(
        listOf(
            "auth.signUp",
            "auth.signIn",
            "fund.getRaised",
            "fund.get"
        )
    )
}