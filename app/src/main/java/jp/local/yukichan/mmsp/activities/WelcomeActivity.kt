package jp.local.yukichan.mmsp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import jp.local.yukichan.mmsp.R
import jp.local.yukichan.mmsp.extension.startActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        touchMe.setOnClickListener({
            startActivity(MainActivity::class)
        })
    }
}
