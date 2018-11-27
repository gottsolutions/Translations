package net.gottsolutions.translations.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import net.gottsolutions.translations.R;
import net.gottsolutions.translations.services.Translation;

/**
 * Example usage:
 * <p>
 * <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
 * xmlns:translation="http://schemas.android.com/apk/res-auto"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content">
 * <p>
 * <net.gottsolutions.translations.widgets.TranslatedButton
 * android:id="@+id/move_button"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:orientation="horizontal"
 * translation:translationTag="@string/next"
 * />
 *
 * </RelativeLayout>
 */

public class TranslatedButton extends android.support.v7.widget.AppCompatButton {

    public TranslatedButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StyleableTranslations);
        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; ++i) {

            int attribute = typedArray.getIndex(i);

            if (attribute == R.styleable.StyleableTranslations_translationTag) {
                String tag = typedArray.getString(attribute);
                if (!TextUtils.isEmpty(tag)) {
                    setText(Translation.get(tag));
                } else {
                    setText("undefined");
                }
            }
        }

        typedArray.recycle();
    }


}