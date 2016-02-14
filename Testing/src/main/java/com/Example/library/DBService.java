package com.Example.library;

import com.Example.constants.Global;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper
{
	private final static int DATABASE_VERSION = 3;
	private final static String DATABASE_NAME = Global.LOCAL_DB_NAME;
	
	public void onCreate(SQLiteDatabase db)
	{
		String sql_kuggle = "CREATE TABLE [" + Global.LOCAL_TABLE_USER_INFO + "] ("
				+ "[id] AUTOINC,"
				+ "[" + Global.LOCAL_FIELD_NAME + "] TEXT NOT NULL ON CONFLICT FAIL,"
				+ "[" + Global.LOCAL_FIELD_GENDER + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_BIRTHDAY + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_TOKEN + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_SETTINGS_UPDATE + "] TEXT )";
		
		db.execSQL(sql_kuggle);
		
		String sql_facebook = "CREATE TABLE [" + Global.LOCAL_TABLE_FACEBOOK_INFO + "] ("
				+ "[id] AUTOINC,"
				+ "[" + Global.LOCAL_FIELD_FACEBOOK_TOKEN + "] TEXT NOT NULL ON CONFLICT FAIL,"
				+ "[" + Global.LOCAL_FIELD_FACEBOOK_NAME + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_TOKEN + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_SETTINGS_UPDATE + "] TEXT )";
		
		db.execSQL(sql_facebook);		
		
		String sql_setting = "CREATE TABLE [" + Global.LOCAL_TABLE_SETTINGS_INFO + "] ("
				+ "[id] AUTOINC,"
				+ "[" + Global.LOCAL_FIELD_SETTINGS_LOGIN + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_SETTINGS_LOGOUT + "] TEXT ,"
				+ "[" + Global.LOCAL_FIELD_SETTINGS_UPDATE + "] TEXT )";
		
		db.execSQL(sql_setting);
	}

	public DBService(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql_kuggle = "drop table if exists [" + Global.LOCAL_TABLE_USER_INFO + "]";
		db.execSQL(sql_kuggle);
		
		String sql_facebook = "drop table if exists [" + Global.LOCAL_TABLE_FACEBOOK_INFO + "]";
		db.execSQL(sql_facebook);
		
		String sql_setting = "drop table if exists [" + Global.LOCAL_TABLE_SETTINGS_INFO + "]";
		db.execSQL(sql_setting);
		
		onCreate(db);
	}

	public void execSQL(String sql, Object[] args)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql, args);
	}

	public Cursor query(String sql, String[] args)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}
}
