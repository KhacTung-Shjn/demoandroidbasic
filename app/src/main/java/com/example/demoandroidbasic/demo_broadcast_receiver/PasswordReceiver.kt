package com.example.demoandroidbasic.demo_broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PasswordReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action == null) return

        when (intent.action) {
            Const.ACTION_VALIDATE_PASSWORD -> {
                val password = intent.getStringExtra(Const.KEY_PASSWORD)
                val isStrong = isValidatePassword(password!!)

                if (isStrong) {
                    Toast.makeText(context, "Power Password", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Weak Password", Toast.LENGTH_SHORT).show()
                }

                val resultIntent = Intent()
                resultIntent.action = Const.ACTION_RESULT_PASSWORD
                resultIntent.putExtra(Const.KEY_RESULT, isStrong)
                context?.sendBroadcast(resultIntent)
            }
        }
    }


    private fun isValidatePassword(pass: String): Boolean {
        var hasNumber = false
        var hasLowCase = false
        var hasUpperCase = false
        var hasSpecialChars = false

        val specialString = "!@#\$%^&*"

        if (pass.length < 8) {
            return false
        }

        for (i in pass.indices) {
            if (pass[i] in '0'..'9') {
                hasNumber = true
            }

            if (pass[i] in 'a'..'z') {
                hasLowCase = true
            }

            if (pass[i] in 'A'..'Z') {
                hasUpperCase = true
            }

            if (specialString.contains(pass[i].toString())) {
                hasSpecialChars = true
            }
        }

        return hasNumber && hasLowCase && hasUpperCase && hasSpecialChars
    }
}