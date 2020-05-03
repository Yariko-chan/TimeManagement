package com.ganeeva.d.f.timemanagement.filter.ui

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.filter.domain.SortType
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class FilterFragment: Fragment(R.layout.fragment_filter) {

    private val viewModel: FiltersViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViews()
        subscribeViewModel()
    }

    private fun setupToolbar() {
        title_textview.setText(R.string.filters_header)
        start_image_view.setImageResource(R.drawable.ic_back)
        start_image_view.setOnClickListener { viewModel.onBackClicked() }
        end_image_view.setImageResource(R.drawable.ic_ok)
        end_image_view.setOnClickListener { viewModel.onSaveClicked() }
    }

    private fun setupViews() {
        sort_radio_group.setOnCheckedChangeListener { group, checkedId ->
            viewModel.onNewSortSelected(
                getSortTypeByViewId(checkedId)
            )
        }
    }

    private fun subscribeViewModel() {
        viewModel.sortType.observe(viewLifecycleOwner, Observer { showSortType(it) })
        viewModel.finishLiveData.observe(viewLifecycleOwner, Observer { finish() })
    }

    private fun showSortType(sortType: SortType) {
        val button = getViewBySortType(sortType)
        if (!button.isChecked) {
            button.isChecked = true
        }
    }

    private fun getSortTypeByViewId(checkedId: Int): SortType {
        return when (checkedId) {
            R.id.sort_by_alphabet -> SortType.ALPHABET
            R.id.sort_short_first -> SortType.LENGTH_SHORT
            R.id.sort_long_first -> SortType.LENGTH_LONG
            R.id.sort_old_first -> SortType.CREATION_DATE_OLD
            else -> SortType.CREATION_DATE_NEW
        }
    }

    private fun getViewBySortType(sortType: SortType): RadioButton {
        return when (sortType) {
            SortType.ALPHABET -> sort_by_alphabet
            SortType.LENGTH_SHORT -> sort_short_first
            SortType.LENGTH_LONG -> sort_long_first
            SortType.CREATION_DATE_OLD -> sort_old_first
            SortType.CREATION_DATE_NEW -> sort_new_first
        }
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}