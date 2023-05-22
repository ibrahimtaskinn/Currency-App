package com.example.currencyapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currencyapp.R
import com.example.currencyapp.adapter.CurrencyArrayAdapter
import com.example.currencyapp.data.CurrencyExchangeRate
import com.example.currencyapp.data.CurrencyItem
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exchangeRates.observe(viewLifecycleOwner) { exchangeRates ->
            if (exchangeRates.isNotEmpty()) {
                binding.Date.text = exchangeRates[0].tarihDate
                setupSpinner(exchangeRates)
            }
        }

        binding.ExchangeRate.setOnClickListener {
            binding.currencySpinner.setSelection(0)
            binding.currencySpinner.performClick()
        }
    }

    private fun setupSpinner(exchangeRates: List<CurrencyExchangeRate>) {
        val defaultItem = CurrencyItem("Seçiniz", 0)
        val items = exchangeRates.map {
            getFlagResourceByName(it.currencyCode)?.let { it1 ->
                CurrencyItem("${it.currencyTitle}/TRY", it1)
            }
        }.toMutableList()

        items.add(0, defaultItem)

        val adapter = CurrencyArrayAdapter(requireContext(), R.layout.spinner_item_with_icon, items)
        binding.currencySpinner.adapter = adapter

        binding.currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position != 0) {
                    val selectedCurrency = exchangeRates[position - 1]

                    binding.apply {
                        ForexBuying.text = selectedCurrency.forexBuying.toString()
                        ForexSelling.text = selectedCurrency.forexSelling.toString()
                        BanknoteBuying.text = selectedCurrency.banknoteBuying.toString()
                        BanknoteSelling.text = selectedCurrency.banknoteSelling.toString()
                        chooseName.text = selectedCurrency.isim
                        unit.text = selectedCurrency.unit.toString()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                binding.currencySpinner.setSelection(0)
            }
        }
    }

    private fun getFlagResourceByName(currencyCode: String): Int? {
        val drawableName = if (currencyCode.equals("IRR", ignoreCase = true)) {
            "irr_flag"
        } else {
            "${currencyCode.toLowerCase()}_flag"
        }
        val resId = resources.getIdentifier("drawable/$drawableName", null, requireContext().packageName)
        return if (resId != 0) resId else null
    }
}