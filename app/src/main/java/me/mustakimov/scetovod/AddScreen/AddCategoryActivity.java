package me.mustakimov.scetovod.AddScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import me.mustakimov.scetovod.CommonVariables;
import me.mustakimov.scetovod.R;

public class AddCategoryActivity extends AppCompatActivity {

    ListView categoriesLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoriesLV = (ListView) findViewById(R.id.categoryListView);
        categoriesLV.setAdapter(new CategoryAdapter(this, CommonVariables.getCategories()));

        categoriesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("categoryPosition", position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}
