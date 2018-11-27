package net.gottsolutions.translations.models;

import java.io.Serializable;

/**
 * This class represents a single translation text on a given language.
 */
public class AppResourcesTranslation implements Serializable {

    //private int idAppResourceTranslation;
    private int idAppLanguage;
    //private String LanguageCode;
    private String Text;
    private String Tag;

    /*public int getIdAppResourceTranslation() {
        return idAppResourceTranslation;
    }

    public void setIdAppResourceTranslation(int idAppResourceTranslation) {
        this.idAppResourceTranslation = idAppResourceTranslation;
    }*/

    public int getIdAppLanguage() {
        return idAppLanguage;
    }

    public void setIdAppLanguage(int idAppLanguage) {
        this.idAppLanguage = idAppLanguage;
    }

    /*public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }*/

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder().create().toJson(this, AppResourcesTranslation.class);
    }
}
