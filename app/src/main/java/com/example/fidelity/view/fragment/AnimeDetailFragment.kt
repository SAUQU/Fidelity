package com.example.fidelity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fidelity.databinding.FragmentItemDetailBinding
import com.example.fidelity.util.viewModel
import com.example.fidelity.viewmodel.animedetails.AnimeDetailsViewModel
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class AnimeDetailFragment : Fragment() {
    private var fragmentItemDetailBinding: FragmentItemDetailBinding? = null
    private val animeViewModel: AnimeDetailsViewModel by viewModel {
        AnimeDetailsViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentItemDetailBinding = FragmentItemDetailBinding.inflate(inflater, container, false)

        activity?.let {
            fragmentItemDetailBinding?.let { binding ->
                binding.viewModel = animeViewModel
                binding.lifecycleOwner = this
            }

            arguments?.let { args ->
                if (args.containsKey(ARG_ITEM_ID))
                    animeViewModel.selectedData = args.getParcelable(ARG_ITEM_ID)
            }

            toolbar_layout?.apply {
                title = animeViewModel.selectedData?.title
            }
        }

        return fragmentItemDetailBinding!!.root
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}