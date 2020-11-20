package com.example.gantask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gantask.R
import com.example.gantask.databinding.CharacterListElementBinding
import com.example.gantask.rest.data.BBCharactersData
import com.example.gantask.viewmodel.CharacterViewModel.Companion.loadImageFromURL


class CharacterAdapter(val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(character: BBCharactersData)
    }

    var characterList: List<BBCharactersData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: CharacterListElementBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.character_list_element,
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setCharacters(characterList: List<BBCharactersData>) {
        this.characterList = characterList
        notifyDataSetChanged()
    }



    inner class CharacterViewHolder(private val item: CharacterListElementBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun onBind(position: Int) {
            val character = characterList[position]
            item.character = character
            item.characterImg.loadImageFromURL(character.img)
            item.itemClickInterface = clickListener
        }
    }
}
