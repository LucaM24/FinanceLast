package com.example.finanzenroom

import android.app.Activity
import android.view.View
import android.widget.AdapterView

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            parent.getItemAtPosition(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>){

    }

}