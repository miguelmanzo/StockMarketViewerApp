package com.example.stockmarketapp.presentation.companyInfo

import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.IntradayInfo
import com.example.stockmarketapp.domain.repository.StockRepository

data class CompanyInfoState(
    val stockInfos : List<IntradayInfo> = emptyList(),
    val company : CompanyInfo? = null,
    val isLoading : Boolean = false,
    val error: String? = null
    )
