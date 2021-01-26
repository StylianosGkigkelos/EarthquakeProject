package com.icsd17029.earthquakeproject.features.list

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.icsd17029.earthquakeproject.R
import com.icsd17029.earthquakeproject.features.add.AddActivity
import com.icsd17029.earthquakeproject.features.map.MapActivity
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import kotlinx.android.synthetic.main.activity_add.bottom_nav
import kotlinx.android.synthetic.main.activity_list.*


class ListsActivity: AppCompatActivity(), MyListViewHolder.OnItemClickListener {
    private val viewModel: ListsViewModel by lazy {
        ViewModelProvider(this).get(ListsViewModel::class.java)
    }

    private val myListAdapter = MyListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        bottom_nav.selectedItemId = R.id.navigation_list
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }

                R.id.navigation_add -> {
                    startActivity(Intent(this, AddActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
            }
            true
        }


        list_recycle_view.adapter = myListAdapter

        viewModel.responseGetFG.observe(this, Observer {
            myListAdapter.submitList(it)
        })

    }


    override fun onStart() {
        super.onStart()


    }

    override fun onItemClicked(item: FirstGradeCheckModel) {
        Log.d(TAG, "Clicked ${item.id}")
    }
}