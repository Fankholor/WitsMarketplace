package com.example.witsmarketplace.fave_cart;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.app.Instrumentation;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.witsmarketplace.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class favoriteTest {
    public ActivityTestRule<favorite> mFavoriteTest = new ActivityTestRule<>(favorite.class);
    private favorite mActivity = null;
    Instrumentation.ActivityMonitor faveMonitor = getInstrumentation().addMonitor(favorite.class.getName(),null,false);

    @Before
    public void  setUp(){
        mActivity = mFavoriteTest.getActivity();
    }

    @Test
    public void removefromFaves(){
        AppCompatButton fave_remove = mActivity.findViewById(R.id.fv_rm);
        Espresso.onView(withId(R.id.fv_rm)).perform(click());
    }

    @After
    public void tearDown() throws Exception{
        mActivity = null;
    }
}