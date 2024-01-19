package com.example.stylebank.model

class Filter {

    //Liste af FilterItems
    var filterItems: MutableList<FilterItem> = mutableListOf()


    //Add FilterItem til liste
    fun addFilterItem(filterItem: FilterItem){
        filterItems.add(filterItem)
    }

    //Remove FilterItem fra liste
    fun removeFilterItem(filterItem: FilterItem){
        val itemToRemove = filterItem.name
        //filterItems.remove(filterItem)
        val item = filterItems.firstOrNull { it.name == itemToRemove }
        if (item != null) {
            filterItems.remove(item)
        }
    }

    fun clearFilter(){
        filterItems.clear()

    }
}