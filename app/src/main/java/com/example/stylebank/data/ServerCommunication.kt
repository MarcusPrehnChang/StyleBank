package com.example.stylebank.data

class ServerCommunication(firebaseRepository: FirebaseRepository) {
    private val firebaseRepository = firebaseRepository

    fun getClothing(callback: (List<String>?) -> Unit) {
        val resultOfQuery = mutableListOf<String>()
        firebaseRepository.getBatchOfIds { batchOfIds ->
            for (id in batchOfIds){
                resultOfQuery.add(id)
            }
            callback(resultOfQuery)
        }
        //TODO("This is what the algorithm should end up doing, this is just dummy implementation so that the app has a consistent flow")
    }

}