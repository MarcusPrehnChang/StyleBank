package com.example.stylebank.model

class ObservableList<T> : ArrayList<T>() {
    private val observers = mutableListOf<ObservableListObserver<T>>()

    fun registerObserver(observer : ObservableListObserver<T>){
        observers.add(observer)
    }

    fun removeObserver(observer : ObservableListObserver<T>){
        observers.remove(observer)
    }

    override fun add(element: T): Boolean{
        println("Thing was added")
        val result = super.add(element)
        if(result){
            notifyItemAdded(element)
        }
        return result
    }

    private fun notifyItemAdded(item : T){
        observers.forEach{
            println("Checkus cockus")
            it.onItemAdded(item)
        }
    }

}