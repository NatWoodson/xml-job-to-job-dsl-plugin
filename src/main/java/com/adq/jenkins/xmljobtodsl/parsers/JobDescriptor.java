package com.adq.jenkins.xmljobtodsl.parsers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobDescriptor implements IDescriptor {

    private String name;
    private List<PropertyDescriptor> properties;
    private List<PropertyDescriptor> addedProperties;

    public JobDescriptor(String name, List<PropertyDescriptor> properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public List<PropertyDescriptor> getProperties() {
        return properties;
    }

    public List<PropertyDescriptor> getAddedProperties(){ return addedProperties; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JobDescriptor)) {
            return false;
        }
        JobDescriptor other = (JobDescriptor) obj;
        if (!other.getName().equals(this.getName())) {
            return false;
        }
        return other.getProperties().equals(this.getProperties());
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getName().equals("parent");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
        builder.setPrettyPrinting();
        return builder.create().toJson(this).replaceAll(Pattern.quote("\\r"), Matcher.quoteReplacement(""));
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}