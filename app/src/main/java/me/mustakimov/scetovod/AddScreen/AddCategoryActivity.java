package me.mustakimov.scetovod.AddScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import me.mustakimov.scetovod.CommonVariables;
import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.adapters.CategoryAdapter;
import me.mustakimov.scetovod.model.CategoryItem;

public class AddCategoryActivity extends AppCompatActivity {

    ListView categoriesLV;
    EditText categoryTitleET;
    Button addCategory;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoriesLV = (ListView) findViewById(R.id.categoryListView);
        categoryAdapter = new CategoryAdapter(this, CommonVariables.getCategories());
        categoriesLV.setAdapter(categoryAdapter);

        categoriesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("categoryPosition", position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        categoryTitleET = (EditText) findViewById(R.id.categoryTitleEditText);
        addCategory = (Button) findViewById(R.id.addCategoryButton);
        addCategory.setOnClickListener(onAddCategoryListener);
    }

    View.OnClickListener onAddCategoryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = categoryTitleET.getText().toString();
            CategoryItem category = new CategoryItem();
            category.setTitle(title);

            CommonVariables.addCategory(AddCategoryActivity.this, category);
            categoryAdapter.notifyDataSetChanged();
        }
    };

}
