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
 * <net.gottsolutions.translations.widgets.TranslatedTextView
 * android:id="@+id/choose_product_type"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:layout_marginBottom="@dimen/margin_small"
 * android:layout_marginTop="@dimen/margin_normal"
 * android:gravity="start|center_vertical"
 * android:orientation="vertical"
 * android:paddingLeft="@dimen/margin_xsmall"
 * android:paddingRight="@dimen/margin_xsmall"
 * translation:translationTag="choose_product"
 * android:textAlignment="textStart"
 * android:textSize="@dimen/survey_text_size"
 * android:textAllCaps="true"
 * android:textStyle="bold"
 * />
 *
 * </RelativeLayout>
 */

public class TranslatedTextView extends android.support.v7.widget.AppCompatTextView {

    public TranslatedTextView(Context context, AttributeSet attrs) {
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