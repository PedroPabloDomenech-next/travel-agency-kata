package com.breadhardit.travelagencykata.application.command;

public interface ICommand<T> {

    T handle();
}
