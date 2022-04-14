package com.example.stockmarketapp.presentation.companyListings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository,
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())

    private var searchJob : Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingsEvent){
        when (event){
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings( fetchFromRemote = true )

            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    companies = it
                                )
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
        }
    }
}