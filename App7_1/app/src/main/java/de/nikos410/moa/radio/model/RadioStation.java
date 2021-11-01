package de.nikos410.moa.radio.model;

import androidx.annotation.NonNull;

public class RadioStation {

    private final String name;
    private final String websiteUrl;
    private final String streamUrl;

    public RadioStation(String name, String websiteUrl, String streamUrl) {
        this.name = name;
        this.websiteUrl = websiteUrl;
        this.streamUrl = streamUrl;
    }

    public String getName() {
        return name;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
