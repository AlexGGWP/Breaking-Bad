package com.example.gantask.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gantask.R
import com.example.gantask.databinding.FragmentMainBinding
import com.example.gantask.rest.data.BBCharactersData
import com.example.gantask.utils.addAndReplaceFragment
import com.example.gantask.utils.filterList
import com.example.gantask.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import net.cachapa.expandablelayout.ExpandableLayout
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), CharacterAdapter.OnItemClickListener {
    private val characterViewModel by viewModel<CharacterViewModel>()
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var mViewDataBinding: FragmentMainBinding
    private var unSortedList: List<BBCharactersData>? = null;
    private var listLoaded: Boolean = false;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main, container, false
        )
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setData()
        mViewDataBinding.viewModel = characterViewModel

        //Avoid loading executing the rest call multiple times
        if (!listLoaded) {
            characterViewModel.getCharacters()
        }

        characterViewModel.characterList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty() && it != null) {
                characterAdapter.setCharacters(it)
                unSortedList = it
                listLoaded = true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val search_view = activity?.findViewById<EditText>(R.id.search_tV)
        search_view?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    //if the input is only digits - user searching for seasons between 1 and 5
                    if (s.isDigitsOnly()) {
                        if (s.toString().toInt() in 1..5) {
                            var season = s.toString().toInt()
                            unSortedList?.let { filterList("", season, it) }?.let {
                                characterAdapter.setCharacters(it)
                            }
                        } else {
                            search_view.error = getString(R.string.main_frag_search_error)
                            search_view.setText("")
                        }
                        //if the input begins with letters - user searching for name
                    } else {
                        unSortedList?.let { filterList(s.toString(), 0, it) }?.let {
                            characterAdapter.setCharacters(it)
                        }
                    }
                }
            }
        })
    }

    private fun setData() {
        characterAdapter = CharacterAdapter(this)
        char_recycler_view.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        char_recycler_view.adapter = characterAdapter
        char_recycler_view.isNestedScrollingEnabled = true
        char_recycler_view.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onItemClick(character: BBCharactersData) {

        //Hide search icon from toolbar. Nothing to filter there
        val searchIcon = activity?.findViewById<ImageView>(R.id.search_icon)
        if (searchIcon != null) {
            searchIcon.visibility = View.GONE
        }

        //make sure that the expanded layout is hidden while transitioning to the details screen
        val expandableView = activity?.findViewById<ExpandableLayout>(R.id.expandable_view)
        if (expandableView?.isExpanded!!) {
            searchIcon?.setImageResource(R.drawable.ic_search_white)
            expandableView.toggle()
        }

        (activity as MainActivity).addAndReplaceFragment(
            DetailsFragment.newInstance(character),
            R.id.fragment_container, DetailsFragment.javaClass.simpleName
        )
    }
}