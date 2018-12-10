package com.example.ashleydsouza.takeyourmeds;

import com.example.ashleydsouza.takeyourmeds.database.AppDatabase;
import com.example.ashleydsouza.takeyourmeds.dao.UsersDao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest {

    private UsersDao mUserDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
//        Context context = ApplicationProvider.
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}