package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.util.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initializeSettingTextInputLayoutErrorMessageAndHint()

        action_next.setOnClickListener {
            if(verifyProperFormatNickname()) { moveToRegionListActivityWithAccountInfo() }
        }
    }

    private fun initializeSettingTextInputLayoutErrorMessageAndHint() {
        setListenerTextInputLayoutErrorMessage()
        setListenerTextInputLayoutHint()
    }

    private fun setListenerTextInputLayoutErrorMessage() {
        input_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layout_nickname.error = getErrorMessageAccordingToLength(s.toString().length)
            }
        })
    }

    private fun setListenerTextInputLayoutHint() {
        input_nickname.setOnFocusChangeListener { _, hasFocus ->
            layout_nickname.hint = getTextInputLayoutHintByFocus(hasFocus)
        }
    }

    private fun getErrorMessageAccordingToLength(length: Int): String? {
        return when(length) {
            in NICKNAME_BLANK until NICKNAME_MINIMUM_LENGTH -> resources.getString(R.string.nickname_min)
            in NICKNAME_MINIMUM_LENGTH .. NICKNAME_MAXIMUM_LENGTH -> null
            else -> resources.getString(R.string.nickname_max)
        }
    }

    private fun getTextInputLayoutHintByFocus(hasFocus: Boolean): String {
        return when(hasFocus) {
            true -> resources.getString(R.string.hint_write_nickname_label)
            else -> resources.getString(R.string.hint_write_nickname)
        }
    }

    private fun verifyProperFormatNickname(): Boolean {
        var correctFormat = false
        when {
            input_nickname.text.toString().length < NICKNAME_MINIMUM_LENGTH -> Util.toastShort(resources.getString(R.string.input_nickname_too_short))
            input_nickname.text.toString().length > NICKNAME_MAXIMUM_LENGTH -> Util.toastShort(resources.getString(R.string.input_nickname_too_long))
            else -> correctFormat = true
        }
        return correctFormat
    }

    private fun moveToRegionListActivityWithAccountInfo() {
        val intent = Intent(this, RegionListActivity::class.java)
        intent.putExtra(ACCOUNT_ID, this.intent.getStringExtra(ACCOUNT_ID))
        intent.putExtra(ACCOUNT_PASSWORD, this.intent.getStringExtra(ACCOUNT_PASSWORD))
        intent.putExtra(ACCOUNT_NICKNAME, input_nickname.text.toString())
        intent.putExtra(ACCOUNT_PROFILE_IMAGE_URL, "https://아직없다")
        startActivity(intent)
    }

    companion object {
        const val NICKNAME_BLANK = 0
        const val NICKNAME_MINIMUM_LENGTH = 2
        const val NICKNAME_MAXIMUM_LENGTH = 8
    }
}