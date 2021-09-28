package com.example.witsmarketplace.ViewMore;

import static org.junit.Assert.*;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.witsmarketplace.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ViewMoreTest {
@Rule
public ActivityTestRule<ViewMore> mViewMoreTestRule = new ActivityTestRule<ViewMore>(ViewMore.class);
private ViewMore mViewMore = null;

    @Before
    public void setUp() throws Exception{
        mViewMore = mViewMoreTestRule.getActivity();
    }
    @Test
    public void testLaunch(){
        View view = mViewMore.findViewById(R.id.rv_viewMore);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws  Exception{

    }
}