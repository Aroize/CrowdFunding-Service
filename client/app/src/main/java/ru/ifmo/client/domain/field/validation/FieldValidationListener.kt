package ru.ifmo.client.domain.field.validation

@FunctionalInterface
interface FieldValidationListener {
    fun textIsValid(valid: Boolean)
}