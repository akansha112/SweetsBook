package com.saxenaakansha.sweetsbooks;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDescriptionActivity extends AppCompatActivity {
    private boolean allChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);

        TextView foodName = findViewById(R.id.foodname);
        TextView foodIngredients = findViewById(R.id.txtIngredients);
        TextView foodDescription = findViewById(R.id.txtdescription);
        ImageView foodImage = findViewById(R.id.ivImage);
        LinearLayout layoutIngredients = findViewById(R.id.layoutIngredients);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            foodDescription.setText(mBundle.getString("RecipeDescription"));
            foodImage.setImageResource(mBundle.getInt("Image"));
            foodName.setText(mBundle.getString("RecipeName"));


            String ingredients = mBundle.getString("RecipeIngredients");
            String[] ingredientArray = ingredients.split("\n");

            for (String ingredient : ingredientArray) {
                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                CheckBox checkBox = new CheckBox(this);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                checkBox.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    // Check if any checkbox is not checked
                    allChecked = checkAllCheckboxes(layoutIngredients);

                    // Update the toast message if all checkboxes are checked
                    if (allChecked) {
                       showCustomToast("Succesfully added ingredients!");
                    }
                });

                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                textView.setText(ingredient);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                itemLayout.addView(checkBox);
                itemLayout.addView(textView);

                layoutIngredients.addView(itemLayout);
            }

            // Delay the initial toast message to allow checkboxes to initialize
            new Handler().postDelayed(() -> {
                if (!allChecked) {
                    showCustomToast( "Please select all ingredients!");
                }
            }, 100);
        }
    }
    private void showCustomToast(String message) {

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast));
            TextView textView = layout.findViewById(R.id.toast_message);
            textView.setText(message);

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }



    private boolean checkAllCheckboxes(LinearLayout layoutIngredients) {
        for (int i = 0; i < layoutIngredients.getChildCount(); i++) {
            View view = layoutIngredients.getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout itemLayout = (LinearLayout) view;
                CheckBox checkBox = (CheckBox) itemLayout.getChildAt(0);
                if (!checkBox.isChecked()) {
                    return false;
                }
            }
        }
        return true;
    }
}
