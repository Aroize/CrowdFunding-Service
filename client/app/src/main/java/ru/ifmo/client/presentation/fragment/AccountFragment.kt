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
import ru.ifmo.client.presentation.presenter.AccountPresenter
import ru.ifmo.client.presentation.presenter.AccountView

class AccountFragment : Fragment(), AccountView {

    private val presenter: AccountPresenter by lazy { App.providePresenter(this) as AccountPresenter }

    private lateinit var mainView: View
    private lateinit var usernameTextView: TextView
    private lateinit var balanceTextView: TextView
    private lateinit var addChipsBtn: Button
    private lateinit var fundsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = layoutInflater.inflate(R.layout.fragment_account, container, false)
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
        fundsRecyclerView = mainView.findViewById(R.id.user_funds)
        //TODO(create adapter and fill it)
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

    override fun showUserFunds(funds: List<Fund>, addCount: Int) {
//        TODO("Not yet implemented")
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}