package com.cis.kotlinsystemreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Toast

class TestReceiver : BroadcastReceiver() {
    val BOOT_COMPLETE = "android.intent.action.BOOT_COMPLETED"
    val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            // BOOT_COMPLETE -> { // 직접 intent-filter action 이름을 지정하거나, 아래처럼 메소드를 이용해도 된다.
            Intent.ACTION_BOOT_COMPLETED -> {
                val toast = Toast.makeText(context, "부팅 완료", Toast.LENGTH_SHORT)
                toast.show()
            }
//            SMS_RECEIVED -> { // 직접 intent-filter action 이름을 지정하거나, 아래처럼 메소드를 이용해도 된다.
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                var str = ""
                val bundle = intent.extras
                if (bundle != null) {
                    val obj = bundle.get("pdus") as Array<Any>
                    val msg = arrayOfNulls<SmsMessage>(obj.size)

                    for (i in obj.indices) {
                        msg[i] = SmsMessage.createFromPdu(obj[i] as ByteArray)
                    }

                    for (i in msg.indices) {
                        str = msg[i]?.originatingAddress + " : " + msg[i]?.messageBody
                        val t2 = Toast.makeText(context, str, Toast.LENGTH_SHORT)
                        t2.show()
                    }
                }
            }

        }
    }
}
