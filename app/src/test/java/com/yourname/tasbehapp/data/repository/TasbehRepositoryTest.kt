package com.yourname.tasbehapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.yourname.tasbehapp.data.local.dao.TasbehDao
import com.yourname.tasbehapp.data.local.entity.TasbehEntity
import com.yourname.tasbehapp.data.local.entity.toDomain
import com.yourname.tasbehapp.domain.model.Tasbeh
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TasbehRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var tasbehDao: TasbehDao

    private lateinit var repository: TasbehRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        repository = TasbehRepository(tasbehDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insertTasbeh calls dao insert`() = runTest {
        val tasbeh = Tasbeh(name = "Test", targetCount = 33)
        coEvery { tasbehDao.insertTasbeh(any()) } returns 1L

        repository.insertTasbeh(tasbeh)

        coVerify { tasbehDao.insertTasbeh(match { it.name == "Test" }) }
    }

    @Test
    fun `incrementCount calls dao updateCount with incremented value`() = runTest {
        val tasbeh = Tasbeh(id = 1, name = "Test", currentCount = 5)
        coEvery { tasbehDao.updateCount(any(), any(), any()) } just Runs

        repository.incrementCount(tasbeh)

        coVerify { tasbehDao.updateCount(eq(1L), eq(6), any()) }
    }

    @Test
    fun `resetCount calls dao resetCount`() = runTest {
        coEvery { tasbehDao.resetCount(any()) } just Runs

        repository.resetCount(1L)

        coVerify { tasbehDao.resetCount(1L) }
    }

    @Test
    fun `setActiveTasbeh calls deactivateAll and then setActive`() = runTest {
        coEvery { tasbehDao.deactivateAll() } just Runs
        coEvery { tasbehDao.setActive(any()) } just Runs

        repository.setActiveTasbeh(123L)

        coVerifyOrder {
            tasbehDao.deactivateAll()
            tasbehDao.setActive(123L)
        }
    }
}
