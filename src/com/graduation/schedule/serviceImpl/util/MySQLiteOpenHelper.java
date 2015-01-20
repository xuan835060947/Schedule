package com.graduation.schedule.serviceImpl.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(createPhoneTableSql);
		db.execSQL(createShortMessageTable);
		db.execSQL(createListingTable);
		db.execSQL(createImportantDateTable);
		db.execSQL(createNoteDateTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	final String createPhoneTableSql = "create table phone (_id integer primary key "
			+ "autoincrement , reason VARCHAR(45) NOT NULL DEFAULT '0',"
			+ "startTime varchar(30) NOT NULL DEFAULT '0',"
			+ "endTime varchar(30) NOT NULL DEFAULT '0',"
			+ "phoneNumber VARCHAR(30) NOT NULL DEFAULT '0'" + ")";
	final String createShortMessageTable = "create table shortmessage (_id integer primary key "
			+ "autoincrement , reason VARCHAR(45) NOT NULL DEFAULT '0',"
			+ "startTime varchar(30) NOT NULL DEFAULT '0',"
			+ "endTime varchar(30) NOT NULL DEFAULT '0',"
			+ "message VARCHAR(200) NOT NULL DEFAULT '0' ,"
			+ "phoneNumber VARCHAR(30) NOT NULL DEFAULT '0'" + ")";
	final String createListingTable = "create table listing (_id integer primary key "
			+ "autoincrement , reason VARCHAR(45) NOT NULL DEFAULT '0',"
			+ "startTime varchar(30) NOT NULL DEFAULT '0',"
			+ "endTime varchar(30) NOT NULL DEFAULT '0',"
			+ "willDoList TEXT NULL" + ")";
	final String createImportantDateTable = "create table importantdate (_id integer primary key "
			+ "autoincrement , reason VARCHAR(45) NOT NULL DEFAULT '0',"
			+ "startTime varchar(30) NOT NULL DEFAULT '0',"
			+ "endTime varchar(30) NOT NULL DEFAULT '0',"
			+ "whatDate VARCHAR(200) NOT NULL DEFAULT '0'" + ")";

	final String createNoteDateTable = "create table note (_id integer primary key "
			+ "autoincrement , reason VARCHAR(45) NOT NULL DEFAULT '0',"
			+ "startTime varchar(30) NOT NULL DEFAULT '0',"
			+ "endTime varchar(30) NOT NULL DEFAULT '0',"
			+ "title varchar(30) NOT NULL DEFAULT '0',"
			+ "content varchar(127) NOT NULL DEFAULT '0'"+ ")";

}
