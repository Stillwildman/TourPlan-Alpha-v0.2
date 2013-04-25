package com.map1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Database1.db";
	private static final String DATABASE_TABLE = "testTable";
	private static final String Field_ID = "_ID";
	private static final String Field_PlanName = "PlanName";
	private static final String Field_StartDate = "PlanStartName";
	private static final String Field_EndDate = "PlanEndDate";
	
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "+DATABASE_TABLE+" ("+
													Field_ID+" INTERGER PRIMARY KEY AutoIncrement,"+
													Field_PlanName+" TEXT,"+
													Field_StartDate+" DATETIME NULL,"+
													Field_EndDate+" DATETIME NULL)";
	private SQLiteDatabase dataBase;
	
	public SQLite(Context context) {		//Constructor
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			dataBase = this.getWritableDatabase();
	}
	
/**
	public SQLite(Context context, String name, CursorFactory factory, int version) {
		super(context,name,factory,version);
		//TODO Auto-generated constructor stub
		//context=Content；name=傳入資料庫名稱；factory=For the Complicated query；version=Version of Database
	}
**/
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);		//Delete the old table!
		onCreate(db);
	}
}
