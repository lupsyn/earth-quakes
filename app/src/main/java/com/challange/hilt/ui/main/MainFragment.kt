package com.challange.hilt.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.challange.hilt.R
import com.challange.hilt.ui.models.EarthQuakesUiModel
import com.challange.hilt.ui.utils.ErrorState
import com.challange.hilt.ui.utils.TransientUIState
import com.challange.hilt.util.ext.init
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*


@AndroidEntryPoint
class MainFragment : Fragment(), EarthQuakesUiModelListener {
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.earthQuakes.observe(
            viewLifecycleOwner,
            Observer<List<EarthQuakesUiModel>> { challenges: List<EarthQuakesUiModel> ->
                mainAdapter.submitList(
                    challenges
                )
            }
        )

        viewModel.uiState.observe(
            viewLifecycleOwner,
            Observer<TransientUIState> { uiState: TransientUIState ->
                showSwipeToRefresh(false)

                when (uiState) {
                    TransientUIState.DisplayDataUIState -> {
                        showList(true)
                        showLoading(false)
                        showErrorState(false)
                    }
                    is TransientUIState.LoadingUIState -> {
                        showErrorState(false)
                        showList(false)
                        showLoading(true)
                    }
                    is TransientUIState.ErrorUIState -> {
                        showList(false)
                        showLoading(false)

                        (uiState.errorState as ErrorState.FullScreenNetworkErrorAndRetryWith).let { retryError ->
                            showErrorState(true, retryError.stringId)
                            retryButton.setOnClickListener {
                                retryError.retry()
                            }
                        }
                    }
                }
            }
        )
        viewModel.fetchData()
    }

    private fun showSwipeToRefresh(show: Boolean) {
        swipe_refresh_layout.isRefreshing = show
    }

    private fun showList(show: Boolean) {
        recyclerView.visibility = if (show) VISIBLE else GONE
        swipe_refresh_layout.visibility = if (show) VISIBLE else GONE
    }

    private fun showLoading(show: Boolean) {
        contentLoadingProgressBar.visibility = if (show) VISIBLE else GONE
    }

    private fun showErrorState(show: Boolean, resIntMessage: Int? = null) {
        errorGroup.visibility = if (show) VISIBLE else GONE
        resIntMessage?.run {
            errorMessage.text = requireContext().resources.getString(resIntMessage)
        }
    }

    private fun initRecyclerView() {
        recyclerView.init(
            { LinearLayoutManager(requireContext()) },
            { mainAdapter },
            {
                val manager = LinearLayoutManager(requireContext())
                val dividerItemDecoration =
                    DividerItemDecoration(requireContext(), manager.orientation)
                recyclerView.addItemDecoration(dividerItemDecoration)
            }
        )
        swipe_refresh_layout.setOnRefreshListener {
            showSwipeToRefresh(true)
            viewModel.fetchData(true)
        }
    }

    override fun onClick(earthQuake: EarthQuakesUiModel) {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.detail_fragment_dest, bundleOf(Pair("quake", earthQuake)))
    }

    override fun onDestroyView() {
        recyclerView.adapter = null
        super.onDestroyView()
    }
}
