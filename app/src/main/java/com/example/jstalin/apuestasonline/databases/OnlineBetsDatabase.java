package com.example.jstalin.apuestasonline.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by JStalin on 05/02/2018.
 */

/**
 * Clase que gestiona la base de datos
 */
public class OnlineBetsDatabase extends SQLiteOpenHelper{


    // Nombre de la base de datos
   public static final String NAME_BD = "onlinebets.db";

   // Version de la base de datos
    public static final int VERSION = 5;

    // Tablas
    public interface Tables {

        String USER = "user";
        String BET = "bet";

    }

    // sentencia para crear tabla Ususarios
    String sqlCreateUser = "CREATE TABLE user( " +
            "id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "email TEXT NOT NULL UNIQUE, " +
            "password TEXT NOT NULL " +
            ");";


    // Sentencia para crear tabla Apuestas
    String sqlCreateBet = "CREATE TABLE bet( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user INTEGER NOT NULL, " +
            "codeSport INTEGER, " +
            "money REAL, " +
            "team1 TEXT, " +
            "team2 TEXT, " +
            "result1 INTEGER, " +
            "result2 INTEGER, " +
            "FOREIGN KEY (user) REFERENCES user(id) " +
            ");";

    // Sentencia para añadir usuario administrador
    String sqlInsertAdmin = "INSERT INTO user(name, email, password) VALUES ('admin','admin@admin', 'admin')";


    public OnlineBetsDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateUser);
        db.execSQL(sqlCreateBet);

        db.execSQL(sqlInsertAdmin);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS bet");

        onCreate(db);


    }


}
