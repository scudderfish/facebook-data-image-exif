package com.github.addshore.facebook.data.image.exif;

public abstract class Task {
    protected abstract Object call();

    protected boolean isCancelled(){return false;}
}
