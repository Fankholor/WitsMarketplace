package com.example.witsmarketplace.fave_cart;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import static java.util.regex.Pattern.matches;

import android.service.autofill.FieldClassification;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.PreferenceMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.witsmarketplace.Login.RegistrationActivity;
import com.example.witsmarketplace.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class cartTest {
    @Rule
    public ActivityTestRule<cart> rActivityTestRule = new ActivityTestRule<>(cart.class);
    private cart mActivity = null;
    public String wrongDetails = "null";
    public String correctDetails = "added to cart";

    @Test
    public void checkButton(){
        assertNotNull(mActivity.findViewById(R.id.AddToCart));
//        onView(withId(R.id.AddToCart)).check(matches());
    }

    @Before
    public void setUp() throws Exception {
        mActivity = rActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}