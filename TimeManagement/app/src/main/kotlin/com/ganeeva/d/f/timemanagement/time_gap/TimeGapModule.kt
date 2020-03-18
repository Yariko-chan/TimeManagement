package com.ganeeva.d.f.timemanagement.time_gap

import com.ganeeva.d.f.timemanagement.time_gap.data.DbTimeGapMapper
import com.ganeeva.d.f.timemanagement.time_gap.data.DbTimeGapRepository
import com.ganeeva.d.f.timemanagement.time_gap.data.NewTimeGapMapper
import com.ganeeva.d.f.timemanagement.time_gap.data.TimeGapMapper
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGapRepository
import org.koin.dsl.module

val timeGapModule = module {
    single { DbTimeGapRepository(get(), get(), get(), get()) as TimeGapRepository }
    factory { TimeGapMapper() }
    single { DbTimeGapMapper() }
    factory { NewTimeGapMapper() }
}