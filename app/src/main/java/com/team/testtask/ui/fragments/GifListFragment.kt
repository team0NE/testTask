package com.team.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team.testtask.R
import com.team.testtask.ui.adapter.GifListAdapter
import com.team.testtask.ui.adapter.OnItemClickListener
import com.team.testtask.utils.hideKeyboard
import com.team.testtask.utils.isConnected
import com.team.testtask.viewmodel.GifListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifListFragment : Fragment(), OnItemClickListener {
    private lateinit var adapter: GifListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var sendBtn: ImageView
    private lateinit var errorText: TextView
    private val vm: GifListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.loadData()

        searchEditText = view.findViewById(R.id.search_et)
        searchEditText.setText(vm.query.value)

        sendBtn = view.findViewById(R.id.send_img)
        sendBtn.setOnClickListener {
            vm.setNewQuery(searchEditText.text.toString())
            // close soft keyboard
            context?.let { ctx -> hideKeyboard(ctx) }
            searchEditText.clearFocus()
        }

        errorText = view.findViewById(R.id.error_text)

        val layoutManager = GridLayoutManager(context, 3)
        recyclerView = view.findViewById(R.id.rv_list)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        vm.isConnected.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                recyclerView.visibility = View.VISIBLE
                errorText.visibility = View.GONE
//                vm.loadData()
            } else {
                recyclerView.visibility = View.GONE
                errorText.visibility = View.VISIBLE
            }
        }

        vm.gifList.observe(viewLifecycleOwner) { list ->
            if(isConnected(requireContext())) {
                adapter = GifListAdapter(list, this)
                recyclerView.adapter = adapter
            }
        }
    }

    override fun onItemClicked(position: Int) {
        vm.position.value = position
        findNavController().navigate(R.id.gifFragment)
    }
}