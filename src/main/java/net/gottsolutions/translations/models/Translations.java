package net.gottsolutions.translations.models;

import java.io.Serializable;
import java.util.List;

/**
 * This class will only contain AppResourcesTranslations so it's easy to pass an array of translations
 * to the json serialization process.
 */
public class Translations implements Serializable {

    private List<AppResourcesTranslation> AppResourcesTranslations;

    public List<AppResourcesTranslation> getAppResourcesTranslations() {
        return AppResourcesTranslations;
    }

    public void setAppResourcesTranslations(List<AppResourcesTranslation> appResourcesTranslations) {
        AppResourcesTranslations = appResourcesTranslations;
    }
}
