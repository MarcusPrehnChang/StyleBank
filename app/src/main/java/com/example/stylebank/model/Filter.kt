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
       /* val filterItem = filterItems.find { it.name == itemName}
        filterItem?.let {
            filterItems.remove(it)
        }*/
        filterItems.remove(filterItem)
    }
}