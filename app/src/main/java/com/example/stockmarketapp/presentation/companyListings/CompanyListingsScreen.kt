package com.example.stockmarketapp.presentation.companyListings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmarketapp.presentation.destinations.CompanyInfoScreenDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)

    val state = viewModel.state
    Column(
        Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it)) },
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            maxLines = 1,
            placeholder = { Text(text = "Search") }
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) })
        {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyItem(company = company,
                        modifier = Modifier.padding(10.dp)
                            .fillMaxSize()
                            .clickable {
                                   navigator.navigate(
                                       CompanyInfoScreenDestination(company.symbol)
                                   )
                            })
                    if (i < state.companies.size) {
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }
            }
        }
    }
}