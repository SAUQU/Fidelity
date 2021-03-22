package com.example.fidelity.view.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.fidelity.R
import com.example.fidelity.databinding.ActivityItemListBinding
import com.example.fidelity.model.AnimeModel
import com.example.fidelity.util.openActivity
import com.example.fidelity.util.replaceFragment
import com.example.fidelity.util.showLoadingDialog
import com.example.fidelity.util.viewModel
import com.example.fidelity.view.fragment.AnimeDetailFragment
import com.example.fidelity.view.fragment.AnimeDetailFragment.Companion.ARG_ITEM_ID
import com.example.fidelity.viewmodel.animelist.AnimeViewModel
import com.example.fidelity.viewmodel.animelist.adapters.AnimeAdapter

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [AnimeDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class AnimeListActivity : AppCompatActivity(), AnimeAdapter.IAnimeAdapter {

    private lateinit var itemListBinding: ActivityItemListBinding
    private val animeModel: AnimeViewModel by viewModel {
        AnimeViewModel(this.application)
    }
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false
    private val dialog: AlertDialog by showLoadingDialog {
        cancelable = false
        setMessage("Loading data...")
        setRetryVisibility()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemListBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_list)
        animeModel.adapter.listener = this
        itemListBinding.viewModel = animeModel
        itemListBinding.lifecycleOwner = this
        val toolbar = itemListBinding.toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title
        animeModel.dataLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                dialog.show()
            } else
                dialog.dismiss()
        })

        if (itemListBinding.itemListContainer.itemDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        itemListBinding.itemListContainer.btnSearch.setOnClickListener {
            val searchData = itemListBinding.itemListContainer.etSearch.text.toString()
            if(!searchData.isNullOrBlank()){
                hideKeyboard()
                animeModel.loadData(searchData)
            }
            else{
                Toast.makeText(this, "Please enter anime name", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun hideKeyboard(){
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    override fun itemSelected(animeModel: AnimeModel) {
        when {
            mTwoPane ->
                replaceFragment(R.id.item_detail_container, AnimeDetailFragment()) {
                    putParcelable(ARG_ITEM_ID, animeModel)
                }
            !mTwoPane ->
                openActivity(AnimeDetailActivity::class.java) {
                    putParcelable(ARG_ITEM_ID, animeModel)
                }
        }
    }
}