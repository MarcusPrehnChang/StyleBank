package com.example.stylebank.model

interface ObservableListObserver<T> {
    fun onItemAdded(item: T)
}