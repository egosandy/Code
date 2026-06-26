package com.rcdriver.cs.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BuildProperties {

    private final Properties properties;

    private BuildProperties() throws IOException {
        InputStream in = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
        properties = new Properties();
        properties.load(in);
        in.close();
    }

    public static BuildProperties newInstance() throws IOException {
        return new BuildProperties();
    }

    public String getProperty(final String name) {
        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

}