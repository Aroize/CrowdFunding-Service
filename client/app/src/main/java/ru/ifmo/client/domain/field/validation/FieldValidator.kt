package ru.ifmo.client.domain.field.validation

import android.text.Editable
import android.text.TextWatcher

class FieldValidator(
    private val regex: Regex,
    private val fieldValidationListener: FieldValidationListener
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        s ?: return
        val text = s.toString()
        fieldValidationListener.textIsValid(text.matches(regex))
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
}