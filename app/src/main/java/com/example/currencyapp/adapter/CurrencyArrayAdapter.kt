package com.example.currencyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.currencyapp.data.CurrencyItem
import com.example.currencyapp.R

class CurrencyArrayAdapter(
    context: Context,
    private val resource: Int,
    private val items: MutableList<CurrencyItem?>
) : ArrayAdapter<CurrencyItem>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val item = getItem(position)

        val imageView = view.findViewById<ImageView>(R.id.spinner_item_icon)
        val textView = view.findViewById<TextView>(R.id.spinner_item_text)

        item?.let {
            imageView.setImageResource(it.icon ?: 0)
            textView.text = it.title
        }

        return view
    }
}