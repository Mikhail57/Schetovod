package me.mustakimov.scetovod.util;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.mustakimov.scetovod.model.PurchaseItem;

import static me.mustakimov.scetovod.util.LogUtils.LOGE;
import static me.mustakimov.scetovod.util.LogUtils.makeLogTag;

/**
 * Created by mikhail on 26/05/16.
 */
public class ParseSpeechUtils {

    private static final String LOG_TAG = makeLogTag(ParseSpeechUtils.class);

    public static PurchaseItem parseStringToPurchase(String encoded) {
        encoded = encoded.replaceAll("[.]", "");
        encoded = encoded.replaceAll("[,]", "");
        LOGE(LOG_TAG, "String1: " + encoded);
        PurchaseItem purchase = new PurchaseItem();

        LuceneMorphology luceneMorphology = null;
        try {
            luceneMorphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> wordsBaseForm = new ArrayList<>();
        String[] words = getWordsFromSentences(encoded);
        for (String word : words) {
            if (luceneMorphology != null && isRussianSmall(word)) {
                String wordForm = luceneMorphology.getNormalForms(word).get(0);
                wordsBaseForm.add(wordForm);
                LOGE(LOG_TAG, "Word: " + wordForm);
            } else {
                wordsBaseForm.add(word);
            }
        }

        for (String word : wordsBaseForm) {
            LOGE(LOG_TAG, "!!!Word!!!: " + word);
        }

        int buy = -1, forPrice = -1, currency1 = -1, currency2 = -1;
        for (int i = 0; i < wordsBaseForm.size(); i++) {
            String word = wordsBaseForm.get(i);

            if (word.equals("купить") || word.equals("приобрести")) {
                buy = i;
                continue;
            }
            if (word.equals("за")) {
                forPrice = i;
                continue;
            }
            if (word.equals("рубль")) {
                currency1 = i;
                continue;
            }
            if (word.equals("копейка")) {
                currency2 = i;
                continue;
            }
        }

        if (buy >= 0) {
            if (forPrice > buy + 1) {
                String title = "";
                if (forPrice - buy > 2) {
                    for (int i = buy + 1; i < forPrice; i++) {
                        title += words[i] + " ";
                    }
                } else {
                    title = wordsBaseForm.get(forPrice - 1);
                }
                purchase.setTitle(title);
            } else {
                if (forPrice == -1 && buy < wordsBaseForm.size() - 1) {
                    purchase.setTitle(wordsBaseForm.get(buy + 1));
                }
            }
            double price = 0;
            if ((currency1 > forPrice) && (currency1 - forPrice == 2)) {
                String digit = wordsBaseForm.get(currency1 - 1);
                if (hasDigit(digit)) {
                    price += Double.parseDouble(digit);
                }
            }
            if ((currency2 > currency1) && (currency2 - currency1 == 2)) {
                String digit = wordsBaseForm.get(currency2 - 1);
                if (hasDigit(digit)) {
                    price += Double.parseDouble(digit) * 0.01;
                }
            }
            purchase.setPrice(price);
        }

        return purchase;
    }

    private static String[] getWordsFromSentences(String sentences) {
        String[] words = sentences.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return words;
    }

    private static boolean hasDigit(String string) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    private static boolean isRussianSmall(String string) {
        Pattern pattern = Pattern.compile("[а-я]");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
}
