package com.lacourt.githubusers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.lacourt.githubusers.paging.UserListPageAdapter
import com.lacourt.githubusers.view.SearchActivity
import org.junit.Rule
import org.junit.Test

class SearchActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(SearchActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {
        // Simulate writing a text in the search bar
        onView(withId(R.id.et_search))
            .perform(typeText("mojombo"))

        // Simulate click on the search button
        onView(withId(R.id.iv_search_icon)).perform(click())
        Thread.sleep(2000)

        // Simulate click on a specific item in the RecyclerView
        val position = 0
        onView(withId(R.id.rv_searched_list))
            .perform(actionOnItemAtPosition<UserListPageAdapter.UserListViewHolder>(position, click()))

        // Verify that the UserDetailsActivity is launched
        onView(withId(R.id.root_user_details))
            .check(matches(isDisplayed()))
    }

}