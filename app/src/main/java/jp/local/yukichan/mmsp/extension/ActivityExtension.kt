package jp.local.yukichan.mmsp.extension

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass

const val EXTRA_ARGS: String = "args"

fun <T: AppCompatActivity> AppCompatActivity.startActivity(classRef: KClass<T>, bundle: Bundle? = null) {
    val intent = Intent(this, classRef.java);
    bundle?.let {
        intent.putExtra(EXTRA_ARGS, bundle)
    }
    startActivity(intent)
}
