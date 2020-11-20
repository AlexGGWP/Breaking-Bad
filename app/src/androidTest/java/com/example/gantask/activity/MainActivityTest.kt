package com.example.gantask.activity

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.gantask.R
import com.example.gantask.base.BaseUITest
import com.example.gantask.di.apiModule
import com.example.gantask.di.generateTestAppComponent
import com.example.gantask.di.networkModule
import com.example.gantask.helpers.recyclerItemAtPosition
import com.example.gantask.rest.NetworkResponseHandler
import com.example.gantask.rest.data.BBCharactersData
import com.example.gantask.ui.CharacterAdapter
import com.example.gantask.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUITest() {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var response: NetworkResponseHandler<List<BBCharactersData>>

    val recyclerView = Espresso.onView(withId(R.id.char_recycler_view))
    val progressBar = Espresso.onView(withId(R.id.progressbar))
    val testName1 = "Walter White"
    val testLastName = "Jack Welker"


    @Before
    fun start() {
        super.setUp()
        //Unload preloaded modules
        unloadKoinModules(
            listOf(
                networkModule,
                apiModule
            )
        )

        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun testMainActivityAndFragments() {
        mActivityTestRule.launchActivity(null)

        mockNetworkResponse("mock_response.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        //Is main fragment visible?
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Check for progress
        progressBar.check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

        //Check if item at 0th position is having 0th element in json
        recyclerView
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName1))
                    )
                )
            )

        //Scroll to last index in json
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<CharacterAdapter.CharacterViewHolder>(56)
        )

        recyclerView
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        56,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testLastName))
                    )
                )
            )

        //Scroll back to top
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<CharacterAdapter.CharacterViewHolder>(0)
        )
        //and click first element
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<CharacterAdapter.CharacterViewHolder>(
                0,
                ViewActions.click()
            )
        )

        //Check if search icon is visible - should be NOT
        Espresso.onView(withId(R.id.search_icon))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        // Confirm DetailFragment is in view
        Espresso.onView(withId(R.id.fragment_details_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.char_name_tv))
            .check(ViewAssertions.matches(ViewMatchers.withText("Walter White")))
        Espresso.onView(withId(R.id.nickname_tv))
            .check(ViewAssertions.matches(ViewMatchers.withText("Heisenberg")))
        Espresso.onView(withId(R.id.status_tv))
            .check(ViewAssertions.matches(ViewMatchers.withText("Presumed dead")))
        Espresso.onView(withId(R.id.occup_tv))
            .check(ViewAssertions.matches(ViewMatchers.withText("High School Chemistry Teacher, Meth King Pin")))
        Espresso.onView(withId(R.id.seasons_tv))
            .check(ViewAssertions.matches(ViewMatchers.withText("1, 2, 3, 4, 5")))

        //Click on back arrow on tool bar
        Espresso.onView(withId(R.id.back_arrow_button)).perform(click())

        //check if click was successful
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Check if search icon is visible - should be VISIBLE
        Espresso.onView(withId(R.id.search_icon))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        //Check if back icon is visible - should be NOT
        Espresso.onView(withId(R.id.back_arrow_button))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
}