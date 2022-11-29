package com.cmput301f22t18.snackntrack.views.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.AppUser;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Label;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddIngredientActivity extends AppCompatActivity {
    ImageButton backButton, increaseAmountButton, decreaseAmountButton, calendarButton;
    ImageButton pickUnitButton, pickLocationButton, pickCategoryButton;
    ImageButton confirmButton;
    TextInputEditText descriptionEditText, amountEditText, bbfEditText;
    ActivityResultLauncher<Intent> mGetContent;
    TextView unitTextView, categoryTextView, locationTextView;
    TextView titleTextView;
    Ingredient ingredient;
    String id;

    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        setUI();

        Intent intent = this.getIntent();
        if (intent.hasExtra("ingredient")) {
            ingredient = intent.getParcelableExtra("ingredient");
            id = intent.getStringExtra("id");
            Log.d("INFO", ingredient.toString());
            setTitle();
            fillDetails();
        }

        // Get the PickLabelActivity Result
        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent i = result.getData();
                        if (i != null) {
                            if (i.hasExtra("unit")) {
                                unitTextView.setVisibility(View.VISIBLE);
                                unitTextView.setText(result.getData()
                                        .getExtras().getString("unit"));
                            }
                            else if (i.hasExtra("location")) {
                                Label location = (Label) i
                                        .getExtras().getSerializable("location");
                                setLabel(locationTextView, location);


                            }
                            else if (i.hasExtra("category")) {
                                Label category = (Label)i.getExtras().getSerializable("category");
                                setLabel(categoryTextView, category);
                            }
                        }
                    }
                });
    }

    private void setLabel(TextView textView, Label label){
        textView.setText(label.getName());
        Drawable drawable = colorLabel(label);
        int background = Color.parseColor(label.getColor());
        int foreground = ResourcesCompat.getColor(this.getResources(),
                R.color.black, null);
        double contrast = ColorUtils.calculateContrast(foreground, background);
        if (contrast < 6.0f)
            foreground = ResourcesCompat.getColor(this.getResources(),
                    R.color.white, null);
        textView.setBackground(drawable);
        textView.setTextColor(foreground);
        textView.setVisibility(View.VISIBLE);
    }

    private void changeLabelColor(TextView labelTextView, String text, AppUser appUser,
                                 String type) {
        ArrayList<Label> labelArrayList;
        if (type.equals("location")) {
            labelArrayList = appUser.getLocations();
        }
        else {
            labelArrayList = appUser.getCategories();
        }
        Label desiredLabel = labelArrayList.stream()
                .filter(location -> text.equals(location.getName()))
                .findAny()
                .orElse(null);
        if (desiredLabel != null) {
            Drawable unwrappedDrawable = ResourcesCompat.getDrawable(
                    this.getResources(),
                    R.drawable.custom_label, null);
            if (unwrappedDrawable != null) {
                Drawable wrappedDrawable = DrawableCompat
                        .wrap(unwrappedDrawable);
                int background = Color.parseColor(desiredLabel.getColor());
                int foreground = ResourcesCompat.getColor(this.getResources(),
                        R.color.black, null);
                double contrast = ColorUtils.calculateContrast(foreground, background);
                if (contrast < 6.0f) foreground = ResourcesCompat.getColor(this.getResources(),
                        R.color.white, null);
                labelTextView.setTextColor(foreground);
                wrappedDrawable.setTint(background);
                labelTextView.setBackground(wrappedDrawable);
                labelTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fillDetails() {
        descriptionEditText.setText(ingredient.getDescription());
        amountEditText.setText(String.format(Locale.CANADA, "%d", ingredient.getAmount()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, y", Locale.CANADA);
        if (ingredient.getBestBeforeDate() != null)
            bbfEditText.setText(simpleDateFormat.format(ingredient.getBestBeforeDateDate()));
        unitTextView.setText(ingredient.getUnit());
        unitTextView.setVisibility(View.VISIBLE);
        categoryTextView.setText(ingredient.getCategory());
        if (ingredient.getLocation() != null)
            locationTextView.setText(ingredient.getLocation());


        // Set label color
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).get().addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            AppUser appUser = task.getResult().toObject(AppUser.class);
                            changeLabelColor(categoryTextView,
                                    ingredient.getCategory(),appUser, "category");
                            if (ingredient.getLocation() != null) {
                                changeLabelColor(locationTextView, ingredient.getLocation(),
                                        appUser, "location");
                            }
                        }
                    }
            );
        }
    }


    private void getImageButton() {
        // Get the image buttons
        backButton = findViewById(R.id.add_ingredient_back_button);
        increaseAmountButton = findViewById(R.id.add_an_ingredient_increase_amount_button);
        decreaseAmountButton = findViewById(R.id.add_an_ingredient_decrease_amount_button);
        calendarButton = findViewById(R.id.add_an_ingredient_calendar_button);

        pickUnitButton = findViewById(R.id.add_an_ingredient_pick_unit_button);
        pickCategoryButton = findViewById(R.id.add_an_ingredient_pick_category_button);
        pickLocationButton = findViewById(R.id.add_an_ingredient_pick_location_button);
        confirmButton = findViewById(R.id.add_an_ingredient_confirm_button);
        setOnClickListener();
    }

    private void setInitialVisibility() {
        unitTextView.setVisibility(View.GONE);
        categoryTextView.setVisibility(View.GONE);
        locationTextView.setVisibility(View.GONE);
    }

    private void setOnClickListener() {
        // Set up image buttons
        backButton.setOnClickListener(v->goBack());
        increaseAmountButton.setOnClickListener(v->changeAmount(1));
        decreaseAmountButton.setOnClickListener(v->changeAmount(-1));
        calendarButton.setOnClickListener(v->openDatePicker());
        pickUnitButton.setOnClickListener(v->openPickerActivity("unit"));
        pickCategoryButton.setOnClickListener(v->openPickerActivity("category"));
        pickLocationButton.setOnClickListener(v->openPickerActivity("location"));
        confirmButton.setOnClickListener(v->addIngredient());
    }

    private void setFocusable() {
        // Setup Edit Texts
        descriptionEditText.setFocusableInTouchMode(true);
        amountEditText.setFocusableInTouchMode(true);
        bbfEditText.setFocusableInTouchMode(false);
        bbfEditText.setFocusable(false);
    }

    private void getEditText() {
        // Get the edit texts
        descriptionEditText = findViewById(R.id.add_an_ingredient_description_edit_text);
        amountEditText = findViewById(R.id.add_an_ingredient_amount_edit_text);
        bbfEditText = findViewById(R.id.add_an_ingredient_bbf_edit_text);
        unitTextView = findViewById(R.id.add_an_ingredient_unit_text_view);
        categoryTextView = findViewById(R.id.add_an_ingredient_category_text_view);
        locationTextView = findViewById(R.id.add_an_ingredient_location_text_view);
        setInitialVisibility();
        setFocusable();
    }

    private void setUI() {
        getImageButton();
        getEditText();
    }

    public void setTitle() {
        titleTextView = findViewById(R.id.add_an_ingredient_title);
        String title = "Edit Ingredient";
        titleTextView.setText(title);
    }

    /**
     * This function enable closing keyboards by touching outside of EditText
     * @param ev Motion event
     * @return true / false base on the AppCompatActivity dispatchTouchEvent function
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null && (ev.getAction() == MotionEvent.ACTION_UP ||
                ev.getAction() == MotionEvent.ACTION_MOVE) &&
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
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(
                        activity.getWindow().getDecorView()
                        .getWindowToken(), 0);

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

    /**
     * This function opens the Material Date Picker to get the date
     */
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

    /**
     * This function launches the PickLabelActivity that carries the corresponding label type
     * @param labelType the label type the user want to pick
     */
    public void openPickerActivity(String labelType) {
        Intent intent = new Intent();
        intent.putExtra("labelType", labelType);
        intent.setClass(this, PickLabelActivity.class);
        mGetContent.launch(intent);
    }

    /**
     * This function return a custom colored drawable
     * @param label the label object to be draw
     * @return the drawable object
     */
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
     * This function extracts all the inputs an create a new ingredient, then add to database
     */
    private void addIngredient() {

        String description = "";
        if (descriptionEditText.getText()!=null)
            description = descriptionEditText.getText().toString();
        int amount = 0;
        if (amountEditText.getText() != null && !amountEditText.getText().toString().isEmpty()) {
            amount = Integer.parseInt(amountEditText.getText().toString());
        }
        String unit = "";
        if (unitTextView.getText() !=null)
            unit = unitTextView.getText().toString();
        String location = "";
        if (locationTextView.getText() !=null)
            location = locationTextView.getText().toString();
        String category = "";
        if (categoryTextView.getText() !=null)
            category = categoryTextView.getText().toString();
        String bbfText = "";
        if (bbfEditText.getText() !=null)
            bbfText = bbfEditText.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, y", Locale.CANADA);
        Date bbf = null;
        try {
            bbf = simpleDateFormat.parse(bbfText);
        }
        catch (ParseException e) {
            Log.e("ERROR", "Cannot parse date");
        }
        if (description.isEmpty()
                || amount == 0 || unit.isEmpty() || category.isEmpty() || location.isEmpty()
                || bbf == null)
        {
            Toast.makeText(this, "Insufficient Input", Toast.LENGTH_SHORT).show();
        }
        else {
            Ingredient i = new Ingredient(description, location, unit, category, amount, bbf);
            if (user != null) {
                String uid = user.getUid();
                if (ingredient == null) {
                    db.collection("storages")
                            .document(uid)
                            .collection("ingredients")
                            .add(i);
                    finish();
                }
                else {
                    db.collection("storages").document(uid)
                            .collection("ingredients").document(id).set(i)
                            .addOnSuccessListener(task -> {
                                Log.i("INFO", "Edited successfully");
                            });
                    finish();
                }
            }
        }
    }

    /**
     * This function end the current activity without returning anything
     */
    public void goBack() {
        finish();
    }
}