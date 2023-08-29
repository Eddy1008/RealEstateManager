package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertTrue;
import android.content.Context;
import androidx.test.InstrumentationRegistry;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void testIsInternetConnectionAvailable() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        boolean isInternetAvailable = Utils.isInternetConnectionAvailable(context);
        assertTrue(isInternetAvailable);
    }
}
