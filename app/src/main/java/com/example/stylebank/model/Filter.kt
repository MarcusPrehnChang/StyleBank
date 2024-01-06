package com.example.stylebank.model

class Filter {

    //Liste af FilterItems
    val filterItems: MutableList<FilterItem> = mutableListOf()


    //Add FilterItem til liste
    fun addFilterItem(filterItem: FilterItem){
        filterItems.add(filterItem)
    }

    //Remove FilterItem fra liste
    fun removeFilterItem(filterItem: FilterItem){
        filterItems.remove(filterItem)
    }

    fun clearFilter(){
        filterItems.clear()
    }
}