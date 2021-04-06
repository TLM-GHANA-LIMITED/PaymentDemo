package com.tlmghana.paymentdemo.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.expresspaygh.api.ExpressPayApi
import com.expresspaygh.api.ExpressPayApi.ExpressPayPaymentCompletionListener
import com.tlmghana.paymentdemo.R
import com.tlmghana.paymentdemo.utils.constant
import com.tlmghana.paymentdemo.utils.constant.ENDPOINT
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity(), ExpressPayPaymentCompletionListener {

    private var expressPayApi: ExpressPayApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        expressPayApi = ExpressPayApi(this, ENDPOINT)
        expressPayApi!!.setDebugMode(true)
        val payBtn = findViewById<Button>(R.id.payBtn)
        payBtn.setOnClickListener { v: View? -> order() }
    }

    private fun order() {

        val params = HashMap<String, String>()

        params["request"] = constant.SUBMIT
        params["order_id"] = constant.ORDER_ID
        params["currency"] = constant.CURRENCY
        params["amount"] = constant.AMOUNT
        params["order_desc"] = constant.ORDER_DESC
        params["user_name"] = constant.USER_NAME
        params["first_name"] = constant.FIRST_NAME
        params["last_name"] = constant.LAST_NAME
        params["email"] = constant.EMAIL
        params["phone_number"] = constant.PHONE
        params["account_number"] = constant.ACCOUNT_NUMBER
        expressPayApi!!.submit(
            params,
            this@MainActivity
        ) { jsonObject: JSONObject?, message: String? ->
            if (jsonObject != null) {

                //You can access the returned token
                try {
                    val status = jsonObject.getString("status")
                    if (status.equals("1", ignoreCase = true)) {
                        val token = expressPayApi!!.token
                        Log.d(constant.TAGS, token)
                        expressPayApi!!.checkout(this@MainActivity)
                    } else {
                        Log.d(constant.TAGS, message!!)
                        constant.initDialog(message, this@MainActivity)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d(constant.TAGS, message!!)
                    constant.initDialog(message, this@MainActivity)
                }
            } else {
                Log.d(constant.TAGS, message!!)
                constant.initDialog(message, this@MainActivity)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) expressPayApi!!.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onExpressPayPaymentFinished(paymentCompleted: Boolean, message: String) {
        if (paymentCompleted) {
            //Payment was completed
            val token = expressPayApi!!.token
            queryPayment(token)
        } else {
            //There was an error
            Log.d(constant.TAGS, message)
            constant.initDialog(message, this@MainActivity)
        }
    }

    private fun queryPayment(token: String?) {
        expressPayApi!!.query(token) { paymentSuccessful: Boolean, _: JSONObject?, message: String? ->
            if (paymentSuccessful) {
                constant.initDialog(message, this@MainActivity)
            } else {
                //There was an error
                Log.d(constant.TAGS, message!!)
                constant.initDialog(message, this@MainActivity)
            }
        }
    }

}