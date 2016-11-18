package com.maximka.gifsearcher.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by maximka on 15.11.16.
 */

public class GiphyResponse {
    public List<Gif> data;

    public class Gif {
        String slug;
        String id;
        RenditionsList images;
        Date trending_datetime;

        class RenditionsList {
            GifRendition downsized_medium;
            GifRendition original_still;

            class GifRendition {
                String url;
                int height;
                int width;
            }
        }
    }
}
