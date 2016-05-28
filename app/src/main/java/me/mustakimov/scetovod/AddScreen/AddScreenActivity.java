package me.mustakimov.scetovod.AddScreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import me.mustakimov.scetovod.CommonVariables;
import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.model.PurchaseItem;
import me.mustakimov.scetovod.util.ParseSpeechUtils;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.gui.RecognizerActivity;

public class AddScreenActivity extends AppCompatActivity {

    static final int PICK_CATEGORY_REQUEST = 1;
    static final int YANDEX_SPEECHKIT_REQUEST = 31;

    LinearLayout selectCategoryLayout;
    TextView categoryTextView;
    Button addPurchaseButton;
    EditText titlePurchase, pricePurchase;
    int category = 0;

    Animation wiggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);

        selectCategoryLayout = (LinearLayout)findViewById(R.id.selectCategory);
        categoryTextView = (TextView)findViewById(R.id.categoryTextView);
        addPurchaseButton = (Button)findViewById(R.id.addPurchaseButton);
        pricePurchase = (EditText)findViewById(R.id.pricePurchaseEditText);
        titlePurchase = (EditText)findViewById(R.id.titlePurchaseEditText);

        if (selectCategoryLayout != null) {
            selectCategoryLayout.setOnClickListener(onClickChooseCategory);
        }
        categoryTextView.setText(CommonVariables.getCategories().get(category).getTitle());
        wiggle = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wiggle);

        addPurchaseButton.setOnClickListener(onClickAddButton);

        /**
         * Yandex.Speechkit initialization
         */
        SpeechKit.getInstance().configure(getApplicationContext(), "e9122350-9172-4912-83ef-03132177fcd7");

        // To start recognition create an Intent with required extras.
        Intent intent = new Intent(this, RecognizerActivity.class);
        // Specify the model for better results.
        intent.putExtra(RecognizerActivity.EXTRA_MODEL, Recognizer.Model.NOTES);
        // Specify the language.
        intent.putExtra(RecognizerActivity.EXTRA_LANGUAGE, Recognizer.Language.RUSSIAN);
        startActivityForResult(intent, YANDEX_SPEECHKIT_REQUEST);
    }

    View.OnClickListener onClickAddButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean filled = true;
            if (pricePurchase.getText().length() == 0) {
                pricePurchase.startAnimation(wiggle);
                filled = false;
            }
            if (titlePurchase.getText().length() == 0) {
                titlePurchase.startAnimation(wiggle);
                filled = false;
            }
            if (filled) {
                PurchaseItem purchase = new PurchaseItem();
                purchase.setPrice(Double.parseDouble(pricePurchase.getText().toString()));
                purchase.setTitle(titlePurchase.getText().toString());
                purchase.setCategoryId(category);
                Calendar rightNow = Calendar.getInstance();
                purchase.setDate(rightNow.getTimeInMillis());
                CommonVariables.addPurchase(AddScreenActivity.this, purchase);

                finish();
            }
        }
    };

    View.OnClickListener onClickChooseCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent selectCategory = new Intent(AddScreenActivity.this, AddCategoryActivity.class);
            startActivityForResult(selectCategory, PICK_CATEGORY_REQUEST);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CATEGORY_REQUEST) {
            if (resultCode == RESULT_OK) {
                category = data.getIntExtra("categoryPosition", category);
                categoryTextView.setText(CommonVariables.getCategories().get(category).getTitle());
            }
        }
        if (requestCode == YANDEX_SPEECHKIT_REQUEST) {
            if (resultCode == RecognizerActivity.RESULT_OK && data != null) {
                String result = data.getStringExtra(RecognizerActivity.EXTRA_RESULT);
                new AsyncFillPurchase().execute(result);
            }
        }
    }

    class AsyncFillPurchase extends AsyncTask<String, Void, Void> {

        PurchaseItem purchase;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0)
                return null;

            purchase = ParseSpeechUtils.parseStringToPurchase(params[0]);
            System.gc();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            titlePurchase.setText(purchase.getTitle());
            pricePurchase.setText(Double.toString(purchase.getPrice()));
        }
    }
}
