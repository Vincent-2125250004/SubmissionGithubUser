package com.dicoding.submissiongithubuser.ui.activity

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyUser = "nurrachmat"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    // Bingung kenapa selalu error sewaktu pengujian
    @Test
    fun searchUser() {
        // Mencari view dengan ID com.google.android.material.search.SearchView
        onView(isAssignableFrom(SearchBar::class.java)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).check(matches(isDisplayed()))
        onView(isAssignableFrom(SearchView::class.java)).perform(
            typeText(dummyUser),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )


    }

}