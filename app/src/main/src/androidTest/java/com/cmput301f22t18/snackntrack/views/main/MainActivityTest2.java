
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.cmput301f22t18.snackntrack.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_getting_started), withText("Get Started"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(com.firebase.ui.auth.R.id.email_button), withText("Sign in with email"),
                        childAtPosition(
                                allOf(withId(com.firebase.ui.auth.R.id.btn_holder),
                                        childAtPosition(
                                                withId(com.google.android.material.R.id.container),
                                                0)),
                                0)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputLayout = onView(
                allOf(withId(com.firebase.ui.auth.R.id.email_layout),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        textInputLayout.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(com.firebase.ui.auth.R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.firebase.ui.auth.R.id.email_layout),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("caspertest@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(com.firebase.ui.auth.R.id.button_next), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(com.firebase.ui.auth.R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.firebase.ui.auth.R.id.password_layout),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("caspertest"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(com.firebase.ui.auth.R.id.button_done), withText("Sign in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_ingredient_fab), withContentDescription("Add an Ingredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_main),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.add_an_ingredient_description_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("Sugar"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.add_an_ingredient_increase_amount_button), withContentDescription("Plus Amount"),
                        childAtPosition(
                                allOf(withId(R.id.add_an_ingredient_amount_row),
                                        childAtPosition(
                                                withId(R.id.add_an_ingredient_form),
                                                1)),
                                3),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.add_an_ingredient_amount_edit_text), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("200"));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.add_an_ingredient_amount_edit_text), withText("200"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.add_an_ingredient_pick_unit_button), withContentDescription("Add a Unit"),
                        childAtPosition(
                                allOf(withId(R.id.add_an_ingredient_unit_row),
                                        childAtPosition(
                                                withId(R.id.add_an_ingredient_form),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.unit_name_radio_button),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialRadioButton.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.pick_a_label_confirm_button), withContentDescription("Confirm Label"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.add_an_ingredient_pick_category_button), withContentDescription("Add a Category"),
                        childAtPosition(
                                allOf(withId(R.id.add_an_ingredient_category_row),
                                        childAtPosition(
                                                withId(R.id.add_an_ingredient_form),
                                                3)),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction materialRadioButton2 = onView(
                allOf(withId(R.id.unit_name_radio_button),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialRadioButton2.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.pick_a_label_confirm_button), withContentDescription("Confirm Label"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.add_an_ingredient_description_edit_text), withText("Sugar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("Mozarella"));

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.add_an_ingredient_description_edit_text), withText("Mozarella"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.add_an_ingredient_pick_unit_button), withContentDescription("Add a Unit"),
                        childAtPosition(
                                allOf(withId(R.id.add_an_ingredient_unit_row),
                                        childAtPosition(
                                                withId(R.id.add_an_ingredient_form),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction materialRadioButton3 = onView(
                allOf(withId(R.id.unit_name_radio_button),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialRadioButton3.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.pick_a_label_confirm_button), withContentDescription("Confirm Label"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.add_an_ingredient_pick_location_button), withContentDescription("Add a Location"),
                        childAtPosition(
                                allOf(withId(R.id.add_an_ingredient_location_row),
                                        childAtPosition(
                                                withId(R.id.add_an_ingredient_form),
                                                4)),
                                2),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction materialRadioButton4 = onView(
                allOf(withId(R.id.unit_name_radio_button),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialRadioButton4.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.pick_a_label_confirm_button), withContentDescription("Confirm Label"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withId(R.id.add_an_ingredient_calendar_button), withContentDescription("Open Date Picker"),
                        childAtPosition(
                                allOf(withId(R.id.add_an_ingredient_bbf_row),
                                        childAtPosition(
                                                withId(R.id.add_an_ingredient_form),
                                                5)),
                                2),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(com.google.android.material.R.id.month_grid),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(31);
        materialTextView.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(com.google.android.material.R.id.confirm_button), withText("OK"),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.date_picker_actions),
                                        childAtPosition(
                                                withId(com.google.android.material.R.id.mtrl_calendar_main_pane),
                                                1)),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withId(R.id.add_an_ingredient_confirm_button), withContentDescription("Confirm Label"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton11.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.ingredient_description_text_view), withText("Mozarella"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Mozarella")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
