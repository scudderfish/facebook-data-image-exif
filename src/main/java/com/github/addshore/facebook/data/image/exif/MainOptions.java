package com.github.addshore.facebook.data.image.exif;

public class MainOptions {

    private final boolean debug;
    private final boolean dry;
    private final boolean overwriteOriginals;

    public MainOptions(
            boolean debug,
            boolean dry,
            boolean overwriteOriginals
    ) {
        this.debug = debug;
        this.dry = dry;
        this.overwriteOriginals = overwriteOriginals;
    }

    public boolean isDryMode() {
        return dry;
    }

    public boolean isDebugMode() {
        return debug;
    }

    public boolean shouldOverwriteOriginals() {
        return overwriteOriginals;
    }

}
