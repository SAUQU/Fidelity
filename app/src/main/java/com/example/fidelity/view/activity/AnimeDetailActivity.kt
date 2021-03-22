package com.example.fidelity.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fidelity.R
import com.example.fidelity.databinding.ActivityItemDetailBinding
import com.example.fidelity.model.AnimeModel
import com.example.fidelity.util.addFragment
import com.example.fidelity.util.viewModel
import com.example.fidelity.view.fragment.AnimeDetailFragment
import com.example.fidelity.view.fragment.AnimeDetailFragment.Companion.ARG_ITEM_ID
import com.example.fidelity.viewmodel.animedetails.AnimeDetailsViewModel

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [AnimeListActivity].
 */
class AnimeDetailActivity : AppCompatActivity() {

    private lateinit var activityItemDetailBinding: ActivityItemDetailBinding
    private val animeViewModel: AnimeDetailsViewModel by viewModel {
        AnimeDetailsViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityItemDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_item_detail)
        activityItemDetailBinding.apply {
            viewModel = animeViewModel
            lifecycleOwner = this@AnimeDetailActivity
            setSupportActionBar(detailToolbar)
        }

        val animeModel = intent.getParcelableExtra<AnimeModel>(ARG_ITEM_ID)
        animeViewModel.selectedData = animeModel

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            addFragment(R.id.item_detail_container, AnimeDetailFragment()) {
                putParcelable(ARG_ITEM_ID, animeModel)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {

            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
