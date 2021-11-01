package de.nikos410.moa.radio.model;

import android.os.Bundle;

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

    public Bundle toBundle() {

        final Bundle bundle = new Bundle();
        bundle.putString("name", getName());
        bundle.putString("websiteUrl", getWebsiteUrl());
        bundle.putString("streamUrl", getStreamUrl());

        return bundle;
    }

    public static RadioStation fromBundle(Bundle bundle) {

        final String name = bundle.getString("name");
        final String websiteUrl = bundle.getString("websiteUrl");
        final String streamUrl = bundle.getString("streamUrl");

        return new RadioStation(name, websiteUrl, streamUrl);
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
