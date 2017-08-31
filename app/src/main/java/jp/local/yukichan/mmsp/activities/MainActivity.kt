package jp.local.yukichan.mmsp.activities

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.BaseAdapter

import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList

import jp.local.yukichan.mmsp.R
import jp.local.yukichan.mmsp.application.MMSPApplication
import jp.local.yukichan.mmsp.extension.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_spinner_scales.view.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        private val FIND_SCALE = "Scaleを探す →"
    }

    private val mOnModeChangedListeners = CopyOnWriteArrayList<OnModeChangedListener>()

    private var mFragmentManager: FragmentManager? = null

    var mode: Mode = Mode.UNKNOWN

    private val mOnItemSelectedListener = OnItemSelectedListenerImpl()

    interface OnModeChangedListener {
        fun onModeChanged(mode: Mode)
    }

    enum class Mode {
        UNKNOWN,
        DETECTION,
        DETAIL
    }

    /* public methods --------------------------------------------------------------------------- */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        spScales!!.adapter = ScalesAdapter()
        spScales!!.onItemSelectedListener = mOnItemSelectedListener
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addOnModeChangedListener(listener: OnModeChangedListener) {
        mOnModeChangedListeners.add(listener)
    }

    fun removeOnModeChangedListener(listener: OnModeChangedListener) {
        mOnModeChangedListeners.remove(listener)
    }

    fun changeDetectionMode() {
        Timber.i("changeDetectionMode called")
        fragmentHistories.view!!.visibility = VISIBLE
        fragmentDetectedCode.view!!.visibility = VISIBLE
        fragmentCodeDetail.view!!.visibility = GONE
        mode = Mode.DETECTION
        for (listener in mOnModeChangedListeners) {
            listener.onModeChanged(mode)
        }
    }

    fun changeDetailMode() {
        Timber.i("changeDetailMode called")
        fragmentHistories.view!!.visibility = GONE
        fragmentDetectedCode.view!!.visibility = GONE
        fragmentCodeDetail.view!!.visibility = VISIBLE
        mode = Mode.DETAIL
        for (listener in mOnModeChangedListeners) {
            listener.onModeChanged(mode)
        }
    }

    private inner class ScalesAdapter internal constructor() : BaseAdapter() {

        private val scales = ArrayList<String>()

        init {
            scales.add("未設定")
            scales.add(FIND_SCALE)
            scales.add("C♭ major scale")
            scales.add("C major scale")
            scales.add("C♯ major scale") // == D♭
            scales.add("D♭ major scale")
            scales.add("D major scale")
            scales.add("E♭ major scale")
            scales.add("E major scale")
            scales.add("F major scale")
            scales.add("F♯ major scale") // == G♭
            scales.add("G♭ major scale")
            scales.add("G major scale")
            scales.add("A♭ major scale")
            scales.add("A major scale")
            scales.add("B♭ major scale")
            scales.add("B major scale")
        }

        override fun getCount(): Int = scales.size

        override fun getItem(position: Int): Any = scales[position]

        override fun getItemId(position: Int): Long = 0

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view: View = convertView.let { it }
                    ?: layoutInflater.inflate(R.layout.item_spinner_scales, parent, false)
            view.tvTitle.text = scales[position]
            return view
        }
    }

    private inner class OnItemSelectedListenerImpl : OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val selected = parent.adapter.getItem(position) as String
            if (selected == FIND_SCALE) {
                this@MainActivity.startActivity(ScaleActivity::class)
            }
        }

        override fun onNothingSelected(adapterView: AdapterView<*>) {
            // NOP
        }
    }
}
