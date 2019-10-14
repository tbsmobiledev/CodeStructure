package com.codestructure.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Tech";

    private static final String TABLE_NAME = "CodeStructure";

    private static final String TX_TIME_STAMP = "TxTimeStamp";
    private static final String TX_HASH = "TxHash";
    private static final String TX_FROM = "TxFrom";
    private static final String TX_TO = "TxTo";
    private static final String TX_VALUE = "TxValue";
    private static final String TX_GAS = "TxGas";
    private static final String TX_GAS_PRICE = "TxGasPrice";
    private static final String TX_CONFIRMATION = "TxConfirmation";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}