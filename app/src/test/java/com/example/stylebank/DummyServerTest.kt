import com.example.stylebank.data.FirebaseRepository
import com.example.stylebank.data.ServerCommunication
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DummyServerTest {
    private val firebaseRepository = mockk<FirebaseRepository>()
    private lateinit var serverCommunication: ServerCommunication

    @Before
    fun setUp() {
        serverCommunication = ServerCommunication(firebaseRepository)
    }

    @After
    fun tearDown() {
        // Clear any recorded calls on mock objects after each test
        clearAllMocks()
    }

    @Test
    fun testGetClothing() {
        // Mocking only the getBatchOfIds method of FirebaseRepository
        val batchOfIds = listOf("id1", "id2", "id3")
        every { firebaseRepository.getBatchOfIds(any()) } answers {
            val callback = arg<(List<String>) -> Unit>(0)
            callback.invoke(batchOfIds)
        }

        // Trigger the asynchronous operation
        serverCommunication.getClothing { resultOfQuery ->
            // Verify that the callback is invoked with the expected data
            assert(resultOfQuery != null)
            assert(resultOfQuery == batchOfIds)
        }

        // Verify that the repository interaction occurred as expected
        verify { firebaseRepository.getBatchOfIds(any()) }
    }
}