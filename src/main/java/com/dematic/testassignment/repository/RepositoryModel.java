package com.dematic.testassignment.repository;

import com.dematic.testassignment.helpers.AntiqueBookParser;
import com.dematic.testassignment.helpers.FileIO;

import java.util.ArrayList;

public abstract class RepositoryModel {
    ArrayList<?> bookStorage;
    String path;
    FileIO fileIO;
    AntiqueBookParser antiqueBookParser;

    abstract void updateStateFromFile(String path);

}
