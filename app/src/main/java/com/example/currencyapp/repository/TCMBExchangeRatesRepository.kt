package com.example.currencyapp.repository

import com.example.currencyapp.data.CurrencyExchangeRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class TcmbExchangeRatesRepository {
    suspend fun tcmbExchangeRates(): List<CurrencyExchangeRate> = withContext(Dispatchers.IO) {
        val resultList = mutableListOf<CurrencyExchangeRate>()
        val url = "https://www.tcmb.gov.tr/kurlar/today.xml"
        val doc: org.jsoup.nodes.Document =
            Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()

        val tarihDateElement: Element? = doc.getElementsByTag("Tarih_Date").first()
        val tarihDate = tarihDateElement?.attr("Tarih") ?: "Bilgi alınamadı"

        val elements: Elements = doc.getElementsByTag("Currency")

        for (item in elements) {
            val currencyCode = item.attr("CurrencyCode")
            val unit = item.getElementsByTag("Unit").text().toIntOrNull() ?: 0
            val isim = item.getElementsByTag("Isim").text()
            val currencyName = item.getElementsByTag("CurrencyName").text()
            val banknoteBuying =
                item.getElementsByTag("BanknoteBuying").text().toDoubleOrNull() ?: 0.0
            val banknoteSelling =
                item.getElementsByTag("BanknoteSelling").text().toDoubleOrNull() ?: 0.0
            val forexBuying = item.getElementsByTag("ForexBuying").text().toDoubleOrNull() ?: 0.0
            val forexSelling = item.getElementsByTag("ForexSelling").text().toDoubleOrNull() ?: 0.0

            val result = CurrencyExchangeRate(
                tarihDate,
                currencyCode,
                unit,
                isim,
                currencyName,
                banknoteBuying,
                banknoteSelling,
                forexBuying,
                forexSelling,
                currencyCode,
            )
            resultList.add(result)
        }

        return@withContext resultList
    }
}