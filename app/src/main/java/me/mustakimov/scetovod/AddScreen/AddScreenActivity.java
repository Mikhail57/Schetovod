package me.mustakimov.scetovod.AddScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.mustakimov.scetovod.CommonVariables;
import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.model.PurchaseItem;

public class AddScreenActivity extends AppCompatActivity {

    static final int PICK_CATEGORY_REQUEST = 1;

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

        addPurchaseButton.setOnClickListener(new View.OnClickListener() {
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
                    CommonVariables.addPurchase(AddScreenActivity.this, purchase);

                    finish();
                }
            }
        });
    }

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
    }
}
