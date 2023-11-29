package com.example.stylebank
import com.example.stylebank.model.ObservableList
import com.example.stylebank.model.ObservableListObserver
import org.junit.Test

import org.junit.Assert.*
class ObservableListTest {


    @Test
    fun getNotification(){
        val list = ObservableList<String>()
        var notificationReceived = false;

        val observer = object : ObservableListObserver<String>{
            override fun onItemAdded(item: String) {
                notificationReceived = true
            }

            override fun setOnItemAddedListener(listener: ((item: String) -> Unit)?) {
                //
            }
        }
        list.registerObserver(observer)
        list.add("testingString")
        assertTrue("was false", notificationReceived)
    }

    @Test
    fun itemAdded(){
        val list = ObservableList<String>()
        list.add("test")
        assertEquals(1, list.size)
        assertEquals("test", list[0])
    }

    @Test
    fun amountOfNotification(){
        val list = ObservableList<String>()
        var amountReceived = 0;

        val observer = object : ObservableListObserver<String>{
            override fun onItemAdded(item: String) {
                amountReceived++
            }

            override fun setOnItemAddedListener(listener: ((item: String) -> Unit)?) {
                //
            }
        }
        list.registerObserver(observer)
        list.add("testingString")
        list.add("testingString")
        list.add("testingString")
        list.add("testingString")
        list.add("testingString")

        assertTrue(amountReceived == 5)
    }

    @Test
    fun oneUpdateItem(){
        val list = ObservableList<String>()
        var textReceived = ""

        val observer = object : ObservableListObserver<String>{
            override fun onItemAdded(item: String) {
                textReceived = item
            }

            override fun setOnItemAddedListener(listener: ((item: String) -> Unit)?) {
                //
            }
        }
        list.registerObserver(observer)
        list.add("testingString")
        list.add("test")
        assertEquals("test", textReceived)
    }




}