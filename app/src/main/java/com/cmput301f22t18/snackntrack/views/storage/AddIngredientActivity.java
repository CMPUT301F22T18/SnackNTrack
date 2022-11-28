package com.cmput301f22t18.snackntrack.views.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.Label;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddIngredientActivity extends AppCompatActivity {
    ImageButton backButton, increaseAmountButton, decreaseAmountButton, calendarButton;
    ImageButton pickUnitButton, pickLocationButton, pickCategoryButton;
    TextInputEditText descriptionEditText, amountEditText, bbfEditText;
    ActivityResultLauncher<Intent> mGetContent;
    TextView unitTextView, categoryTextView, locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // Get the image buttons
        backButton = findViewById(R.id.add_ingredient_back_button);
        increaseAmountButton = findViewById(R.id.add_an_ingredient_increase_amount_button);
        decreaseAmountButton = findViewById(R.id.add_an_ingredient_decrease_amount_button);
        calendarButton = findViewById(R.id.add_an_ingredient_calendar_button);

        pickUnitButton = findViewById(R.id.add_an_ingredient_pick_unit_button);
        pickCategoryButton = findViewById(R.id.add_an_ingredient_pick_category_button);
        pickLocationButton = findViewById(R.id.add_an_ingredient_pick_location_button);

        // Get the edit texts
        descriptionEditText = findViewById(R.id.add_an_ingredient_description_edit_text);
        amountEditText = findViewById(R.id.add_an_ingredient_amount_edit_text);
        bbfEditText = findViewById(R.id.add_an_ingredient_bbf_edit_text);

        // Setup Edit Texts
        descriptionEditText.setFocusableInTouchMode(true);
        amountEditText.setFocusableInTouchMode(true);
        bbfEditText.setFocusableInTouchMode(false);
        bbfEditText.setFocusable(false);

        // Set up image buttons
        backButton.setOnClickListener(v->goBack());
        increaseAmountButton.setOnClickListener(v->changeAmount(1));
        decreaseAmountButton.setOnClickListener(v->changeAmount(-1));
        calendarButton.setOnClickListener(v->openDatePicker());

        unitTextView = findViewById(R.id.add_an_ingredient_unit_text_view);
        unitTextView.setVisibility(View.GONE);
        categoryTextView = findViewById(R.id.add_an_ingredient_category_text_view);
        categoryTextView.setVisibility(View.GONE);
        locationTextView = findViewById(R.id.add_an_ingredient_location_text_view);
        locationTextView.setVisibility(View.GONE);

        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent i = result.getData();
                        if (i != null) {
                            if (i.hasExtra("unit")) {
                                unitTextView.setVisibility(View.VISIBLE);
                                unitTextView.setText(result.getData().getExtras().getString("unit"));
                            }
                            else if (i.hasExtra("location")) {
                                Label location = (Label)i.getExtras().getSerializable("location");
                                locationTextView.setText(location.getName());
                                Drawable drawable = colorLabel(location);
                                locationTextView.setBackground(drawable);
                                locationTextView.setVisibility(View.VISIBLE);
                            }
                            else if (i.hasExtra("category")) {
                                Label category = (Label)i.getExtras().getSerializable("category");
                                categoryTextView.setText(category.getName());
                                Drawable drawable = colorLabel(category);
                                categoryTextView.setBackground(drawable);
                                int black = ResourcesCompat.getColor(
                                        this.getResources(), R.color.black, null);
                                categoryTextView.setTextColor(black);
                                categoryTextView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        pickUnitButton.setOnClickListener(v->openPickerActivity("unit"));
        pickCategoryButton.setOnClickListener(v->openPickerActivity("category"));
        pickLocationButton.setOnClickListener(v->openPickerActivity("location"));
    }

    /**
     * This function enable closing keyboards by touching outside of EditText
     * @param ev Motion event
     * @return true / false base on the AppCompatActivity dispatchTouchEvent function
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof TextInputEditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int[] sourceCoordinates = new int[2];
            v.getLocationOnScreen(sourceCoordinates);
            float x = ev.getRawX() + v.getLeft() - sourceCoordinates[0];
            float y = ev.getRawY() + v.getTop() - sourceCoordinates[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyboard(this);
                v.clearFocus();
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * This function hide the virtual keyboard
     * @param activity the activity invoking this method
     */
    private void hideKeyboard(AddIngredientActivity activity) {
        if (activity != null && activity.getWindow() != null) {
            activity.getWindow().getDecorView();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);

            }
        }
    }

    /**
     * This function change the amount edit text input text by an amount
     * @param changeAmount -1 for decrease, 1 for increase
     */
    public void changeAmount(int changeAmount){
        int amount = 0;
        if (amountEditText.getText() != null && !amountEditText.getText().toString().isEmpty()) {
            amount = Integer.parseInt(amountEditText.getText().toString());
        }
        amount += changeAmount;
        if (amount < 0) amount = 0;
        String new_amount = String.format(Locale.CANADA, "%d", amount);
        amountEditText.setText(new_amount);
    }

    public void openDatePicker() {

        final MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker
                .Builder
                .datePicker().build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            TimeZone timeZoneUTC = TimeZone.getDefault();
            // It will be negative, so that's the -1
            int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
            Date date = new Date(selection + offsetFromUTC);
            SimpleDateFormat format = new SimpleDateFormat("MMM d, y", Locale.CANADA);
            bbfEditText.setText(format.format(date));
        });
        materialDatePicker.show(getSupportFragmentManager(), "MaterialDatePicker");
    }

    public void openPickerActivity(String labelType) {
        Intent intent = new Intent();
        intent.putExtra("labelType", labelType);
        intent.setClass(this, PickLabelActivity.class);
        mGetContent.launch(intent);
    }

    public Drawable colorLabel(Label label) {
        Drawable unwrappedDrawable = ResourcesCompat.getDrawable(this.getResources(),
                R.drawable.custom_label, null);
        if (unwrappedDrawable != null) {
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);

            wrappedDrawable.setTint(Color.parseColor(label.getColor()));
            return wrappedDrawable;
        }
        return null;
    }

    /**
     * This function end the current activity without returning anything
     */
    public void goBack() {
        finish();
    }
}