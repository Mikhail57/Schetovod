package me.mustakimov.scetovod.AddScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.mustakimov.scetovod.R;
import me.mustakimov.scetovod.model.CategoryItem;

/**
 * Created by mikhail on 23/05/16.
 */
public class CategoryAdapter extends BaseAdapter {

    List<CategoryItem> categories;
    Context context;
    LayoutInflater inflater;

    public CategoryAdapter(Context context, List<CategoryItem> categories) {
        this.context = context;
        this.categories = categories;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null) {
            view = inflater.inflate(R.layout.category_item, parent, false);
        }

        TextView categoryTV = (TextView)view.findViewById(R.id.categoryTextView);
        categoryTV.setText(categories.get(position).getTitle());

        return view;
    }
}
