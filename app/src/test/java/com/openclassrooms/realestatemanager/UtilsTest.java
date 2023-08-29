package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsTest {

    @Test
    public void testConvertDollarToEuro() {
        int dollars = 100;
        int expectedEuro = 81;  // 81.2 -> 81

        int actualEuro = Utils.convertDollarToEuro(dollars);

        assertEquals(expectedEuro, actualEuro);
    }

    @Test
    public void testConvertEuroToDollar() {
        int euros = 100;
        int expectedDollars = 119;  // 118.8 -> 119

        int actualDollars = Utils.convertEuroToDollar(euros);

        assertEquals(expectedDollars, actualDollars);
    }

    @Test
    public void testGetTodayDate() throws ParseException {
        String expectedPattern = "dd/MM/yyyy";

        String actualDate = Utils.getTodayDate();

        DateFormat dateFormat = new SimpleDateFormat(expectedPattern);
        Date parsedDate = dateFormat.parse(actualDate);
        assert parsedDate != null;
        String formattedParsedDate = dateFormat.format(parsedDate);

        assertEquals(actualDate, formattedParsedDate);
    }
}
