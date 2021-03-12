package com.alanchen.cloudinteractive_alanchen;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.alanchen.cloudinteractive_alanchen.model.Photo;
import com.alanchen.cloudinteractive_alanchen.viewmodel.PhotoViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.alanchen.cloudinteractive_alanchen", appContext.getPackageName());
    }

    @Test
    public void viewModelInfo() {
        Photo photo = new Photo();
        photo.title = "SuperMan";
        PhotoViewModel photoViewModel = new PhotoViewModel(photo);
        assertEquals(photoViewModel.title, new PhotoViewModel(new Photo()).title);
    }
}
