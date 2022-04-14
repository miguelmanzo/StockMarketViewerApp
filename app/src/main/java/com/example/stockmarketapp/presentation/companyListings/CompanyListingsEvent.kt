package com.example.stockmarketapp.presentation.companyListings

sealed class CompanyListingsEvent{
    object  Refresh:  CompanyListingsEvent()
    data class  OnSearchQueryChange(val query: String): CompanyListingsEvent()
}
