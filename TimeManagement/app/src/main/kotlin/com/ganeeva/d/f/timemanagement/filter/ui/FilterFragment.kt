package com.ganeeva.d.f.timemanagement.filter.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.util.*

class FilterFragment: Fragment(R.layout.fragment_filter) {

    private val viewModel: FiltersViewModel by viewModel()

    private val startDateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth -> viewModel.onStartDate(year, month, dayOfMonth) }
    private val startTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> viewModel.onStartTime(hourOfDay, minute) }
    private val endDateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth -> viewModel.onEndDate(year, month, dayOfMonth) }
    private val endTimeListener = TimePickerDialog.OnTimeSetListener { _,  hourOfDay, minute -> viewModel.onEndTime(hourOfDay, minute) }

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
        from_date_text_view.setOnClickListener {
            showDateDialog(viewModel.periodStart.value, startDateListener)
        }
        from_time_text_view.setOnClickListener {
            showTimeDialog(viewModel.periodStart.value, startTimeListener)
        }
        to_date_text_view.setOnClickListener {
            showDateDialog(viewModel.periodEnd.value, endDateListener)
        }
        to_time_text_view.setOnClickListener {
            showTimeDialog(viewModel.periodEnd.value, endTimeListener)
        }
    }

    private fun subscribeViewModel() {
        viewModel.sortType.observe(viewLifecycleOwner, Observer { showSortType(it) })
        viewModel.finishLiveData.observe(viewLifecycleOwner, Observer { finish() })
        viewModel.periodStartDateText.observe(viewLifecycleOwner, Observer { from_date_text_view.text = it })
        viewModel.periodStartTimeText.observe(viewLifecycleOwner, Observer { from_time_text_view.text = it })
        viewModel.periodEndDateText.observe(viewLifecycleOwner, Observer { to_date_text_view.text = it })
        viewModel.periodEndTimeText.observe(viewLifecycleOwner, Observer { to_time_text_view.text = it })
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

    private fun showDateDialog(c: Calendar?, listener: DatePickerDialog.OnDateSetListener) {
        val calendar = c ?: Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    private fun showTimeDialog(c: Calendar?, listener: TimePickerDialog.OnTimeSetListener) {
        val calendar = c ?: Calendar.getInstance()
        val dialog = TimePickerDialog(
            requireContext(),
            listener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        dialog.show()
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}