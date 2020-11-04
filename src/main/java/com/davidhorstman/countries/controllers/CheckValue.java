package com.davidhorstman.countries.controllers;

@FunctionalInterface
public interface CheckValue<T> {
    boolean test(T t);
}
