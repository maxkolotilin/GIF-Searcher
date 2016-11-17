package com.maximka.gifsearcher;

import proxypref.annotation.DefaultString;

/**
 * Created by maximka on 17.11.16.
 */

public interface Preferences {
    @DefaultString("r")
    String getRating();
    void setRating(String rating);
}
