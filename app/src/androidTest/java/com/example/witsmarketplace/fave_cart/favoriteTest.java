package com.example.witsmarketplace.fave_cart;

import static androidx.test.espresso.Espresso.onView;
import static org.junit.Assert.*;

import android.widget.ImageButton;

import androidx.test.rule.ActivityTestRule;

import com.example.witsmarketplace.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class favoriteTest {
    @Rule
    public ActivityTestRule<favorite> mActivityTestRule = new ActivityTestRule<>(favorite.class);
    private favorite mActivity = null;
    public String wrongDetails = "null";
    public String correctDetails = "added to favourite";

    @Test
    public void Checkbutton(){
        assertNotNull(mActivity.findViewById(R.id.addFaveBtn));

    }

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}