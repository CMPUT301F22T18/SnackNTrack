package com.cmput301f22t18.snackntrack.views.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.cmput301f22t18.snackntrack.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class AddIngredientActivity extends AppCompatActivity {
    ImageButton backButton, increaseAmountButton, decreaseAmountButton, calendarButton;
    TextInputEditText descriptionEditText, amountEditText, bbfEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        backButton = findViewById(R.id.add_ingredient_back_button);
        increaseAmountButton = findViewById(R.id.add_an_ingredient_increase_amount_button);
        decreaseAmountButton = findViewById(R.id.add_an_ingredient_decrease_amount_button);
        calendarButton = findViewById(R.id.add_an_ingredient_calendar_button);

        descriptionEditText = findViewById(R.id.add_an_ingredient_description_edit_text);
        amountEditText = findViewById(R.id.add_an_ingredient_amount_edit_text);
        bbfEditText = findViewById(R.id.add_an_ingredient_bbf_edit_text);

        descriptionEditText.setFocusableInTouchMode(true);
        amountEditText.setFocusableInTouchMode(true);
        bbfEditText.setFocusableInTouchMode(false);
        bbfEditText.setFocusable(false);

        backButton.setOnClickListener(v->goBack());
        increaseAmountButton.setOnClickListener(v->changeAmount(1));
        decreaseAmountButton.setOnClickListener(v->changeAmount(-1));
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
        String new_amount = String.format(Locale.CANADA, "%d", amount);
        amountEditText.setText(new_amount);
    }

    /**
     * This function end the current activity without returning anything
     */
    public void goBack() {
        finish();
    }
}