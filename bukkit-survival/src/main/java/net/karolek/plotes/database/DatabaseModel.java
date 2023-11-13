package net.karolek.plotes.database;

import org.bson.Document;

public abstract class DatabaseModel {

    protected abstract Document buildDocument();

    protected abstract void insert();

    public abstract void update();

    public abstract void delete();
}


