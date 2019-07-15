package `in`.co.ankitarora.mvvmsampletip.view

import `in`.co.ankitarora.mvvmsampletip.R
import `in`.co.ankitarora.mvvmsampletip.databinding.ActivityMainBinding
import `in`.co.ankitarora.mvvmsampletip.viewmodel.CalculatorViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SaveDialogFragment.Callback, LoadFragment.Callback {
    override fun onTipSelected(name: String) {
        binding.vm?.loadTipCalculation(name)
        Snackbar.make(binding.root, "Loaded $name", Snackbar.LENGTH_SHORT).show()

    }
    lateinit var binding: ActivityMainBinding

    override fun onSaveTip(name: String) {
        binding.vm?.saveCurrentTip(name)
        Snackbar.make(binding.root, "Saved $name", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        binding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                return true
            }
            R.id.action_load -> {
                showLoadDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoadDialog() {
        val loadFragment = LoadFragment()
        loadFragment.show(supportFragmentManager, "LoadDialog")
    }

    private fun showSaveDialog() {
        val saveDialogFragment = SaveDialogFragment()
        saveDialogFragment.show(supportFragmentManager,"SaveDialog")

    }
}
