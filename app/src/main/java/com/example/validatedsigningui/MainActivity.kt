package com.example.validatedsigningui

import android.os.Bundle
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var privacyCheckBox: CheckBox
    private lateinit var privacyCheckBoxText: TextView
    private lateinit var signUpButton: Button
    private var phoneNumberString = ""
    private var email = ""
    private var city = ""
    private var password = ""
    private var isPrivacyCheckBoxChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        privacyCheckBoxText.makeLinks(
            Pair("Terms of service and Privacy Policy", View.OnClickListener {
                Toast.makeText(applicationContext, "// ToDo", Toast.LENGTH_SHORT).show()
            })
        )


        signUpButtonClickHandler()


    }

    private fun initViews() {
        phoneEditText = findViewById(R.id.editTextPhoneNumber)
        emailEditText = findViewById(R.id.editTextEmail)
        cityEditText = findViewById(R.id.editTextCity)
        passwordEditText = findViewById(R.id.editTextPassword)
        privacyCheckBox = findViewById(R.id.checkBoxPrivacy)
        privacyCheckBoxText = findViewById(R.id.checkBoxPrivacyText)
        signUpButton = findViewById(R.id.sign_up_button)
    }

    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun updateDrawState(textPaint: TextPaint) {
                    // use this to change the link color
                    textPaint.color = textPaint.linkColor
                    // toggle below value to enable/disable
                    // the underline shown below the clickable text
                    textPaint.isUnderlineText = true
                }

                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
//      if(startIndexOfLink == -1) continue // todo if you want to verify your texts contains links text
            spannableString.setSpan(
                clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }


    private fun getValues() {
        phoneNumberString = phoneEditText.text.toString()
        email = emailEditText.text.toString()
        city = cityEditText.text.toString()
        password = passwordEditText.text.toString()
        isPrivacyCheckBoxChecked = privacyCheckBox.isChecked
    }

    private fun signUpButtonClickHandler() {
        signUpButton.setOnClickListener {
            getValues()
            validateForm()
        }
    }

    private fun validateForm() {
        val b2: Boolean
        val b3: Boolean
        val b4: Boolean
        val b5: Boolean

        val b1: Boolean = validatePhoneNumber(phoneNumberString)
        if (b1) {
            b2 = validateEmail(email)
            if (b2) {
                b3 = validateCity(city)
                if (b3) {
                    b4 = validatePassword(password)
                    if (b4) {
                        b5 = privacyCheckBox.isChecked
                        if (b5) {
                            // ToDo
                            Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "You have to agree to the Terms of service and Privacy Policy", Toast.LENGTH_LONG)
                                .show()

                        }
                    }
                }
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {

        val allIsNumbers: Boolean = phoneNumber.toIntOrNull() != null // Also check if it is empty

        return if (!allIsNumbers) {
            Toast.makeText(this, "Phone number is not valid", Toast.LENGTH_LONG).show()
            false
        } else if (phoneNumber.length != 11) {
            Toast.makeText(this, "Phone number must be 11 number", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

    private fun validateEmail(email: String): Boolean {

        val validEmail: Boolean = email.contains('@').and(email.contains('.')) // Also check if it is empty

        return if (!validEmail) {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

    private fun validateCity(city: String): Boolean {

        return if (city.isEmpty()) {
            Toast.makeText(this, "Please enter a city", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

    private fun validatePassword(password: String): Boolean {


        return if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
            false
        } else if (password.length < 8) {
            Toast.makeText(this, "Password must be more than 8 characters", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

}