package me.mustakimov.scetovod;

import org.junit.Test;

import me.mustakimov.scetovod.model.PurchaseItem;
import me.mustakimov.scetovod.util.ParseSpeechUtils;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parsingIsCorrect() throws Exception {
        PurchaseItem item = new PurchaseItem();
        item.setPrice(50);
        item.setTitle("булка хлеба");
        assertEquals(item, ParseSpeechUtils.parseStringToPurchase("купил булку хлеба за 50 рублей"));
    }
}