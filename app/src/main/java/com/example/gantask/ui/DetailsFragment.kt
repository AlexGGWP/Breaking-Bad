package com.example.gantask.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.gantask.R
import com.example.gantask.databinding.FragmentDetailsBinding
import com.example.gantask.rest.data.BBCharactersData
import com.example.gantask.viewmodel.CharacterViewModel.Companion.loadImageFromURL
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(data: BBCharactersData) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("character_details_data", data)
            }
        }
    }

    private var characterDetails: BBCharactersData? = null
    private lateinit var mViewDataBinding: FragmentDetailsBinding
    private var backArrow: ImageView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        characterDetails = arguments?.getParcelable("character_details_data")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details, container, false
        )
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewDataBinding.character = characterDetails
        setTextFields()
        characterDetails?.img?.let { char_img.loadImageFromURL(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backArrow = activity?.findViewById(R.id.back_arrow_button)
        if (backArrow != null) {
            backArrow!!.visibility = View.VISIBLE
        }
    }

    private fun setTextFields() {
        if (characterDetails?.occupation?.isNotEmpty()!!) {
            val builder = StringBuilder()
            for (i in characterDetails!!.occupation.indices) {
                builder.append(characterDetails!!.occupation[i])
                if (i != characterDetails!!.occupation.size - 1) {
                    builder.append(", ")
                }
            }
            occup_tv.text = builder.toString()
        }

        if (characterDetails?.appearance != null && characterDetails?.appearance!!.isNotEmpty()) {
            val builder = StringBuilder()
            for (i in characterDetails!!.appearance.indices) {
                builder.append(characterDetails!!.appearance[i])
                if (i != characterDetails!!.appearance.size - 1) {
                    builder.append(", ")
                }
            }
            seasons_tv.text = builder.toString()
        }
    }

    override fun onDestroy() {
        backArrow?.visibility = View.GONE
        val searchIcon = activity?.findViewById<ImageView>(R.id.search_icon)
        if (searchIcon != null) {
            searchIcon.visibility = View.VISIBLE
        }
        super.onDestroy()
    }
}