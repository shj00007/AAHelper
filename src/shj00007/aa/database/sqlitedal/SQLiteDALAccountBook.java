package shj00007.aa.database.sqlitedal;

import java.util.Date;
import java.util.List;

import shj00007.aa.R;
import shj00007.aa.database.base.SQLiteDALBase;
import shj00007.aa.model.ModelAccountBook;
import shj00007.aa.utility.DateTools;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class SQLiteDALAccountBook extends SQLiteDALBase {

	public SQLiteDALAccountBook(Context p_Context) {
		super(p_Context);
	}

	public Boolean InsertAccountBook(ModelAccountBook p_Info) {
		ContentValues _ContentValues = CreatParms(p_Info);
		Long p_NewID = GetDataBase().insert("AccountBook", null, _ContentValues);
		p_Info.setAccountBookID(p_NewID.intValue());
		return p_NewID > 0;
	}
	
	public Boolean DeleteAccountBook(String p_Condition)
	{
		return Delete(GetTableNameAndPK()[0], p_Condition);
	}
	
	public Boolean UpdateAccountBook(String p_Condition, ModelAccountBook p_Info)
	{
		ContentValues _ContentValues = CreatParms(p_Info);
		return GetDataBase().update("AccountBook", _ContentValues, p_Condition, null) > 0;
	}
	
	public Boolean UpdateAccountBook(String p_Condition,ContentValues p_ContentValues)
	{
		return GetDataBase().update("AccountBook", p_ContentValues, p_Condition, null) > 0;
	}
	
	public List<ModelAccountBook> GetAccountBook(String p_Condition)
	{
		String _SqlText = "Select * From AccountBook Where  1=1 " + p_Condition;
		return GetList(_SqlText);
	}
	
	protected ModelAccountBook FindModel(Cursor p_Cursor)
	{
		ModelAccountBook _ModelAccountBook = new ModelAccountBook();
		_ModelAccountBook.setAccountBookID(p_Cursor.getInt(p_Cursor.getColumnIndex("AccountBookID")));
		_ModelAccountBook.setAccountBookName(p_Cursor.getString(p_Cursor.getColumnIndex("AccountBookName")));
		Date _CreateDate = DateTools.getDate(p_Cursor.getString(p_Cursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");	
		_ModelAccountBook.setCreateDate(_CreateDate);
		_ModelAccountBook.setIsDefault(p_Cursor.getInt(p_Cursor.getColumnIndex("IsDefault")));
		_ModelAccountBook.setState((p_Cursor.getInt(p_Cursor.getColumnIndex("State"))));
		
		return _ModelAccountBook;
	}

	private void InitDefaultData(SQLiteDatabase p_DataBase)
	{
		String _AccountBook[] = GetContext().getResources().getStringArray(R.array.InitDefaultDataAccountBookName);
		ModelAccountBook _ModelAccountBook = new ModelAccountBook();
		_ModelAccountBook.setAccountBookName(_AccountBook[0]);
		_ModelAccountBook.setIsDefault(1);
	
		ContentValues _ContentValues = CreatParms(_ModelAccountBook);
		p_DataBase.insert("AccountBook", null, _ContentValues);
	}
	
	@Override
	public void OnCreate(SQLiteDatabase p_DataBase) {
		StringBuilder s_CreateTableScript = new StringBuilder();
		
		s_CreateTableScript.append("		Create  TABLE AccountBook(");
		s_CreateTableScript.append("				[AccountBookID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("				,[AccountBookName] varchar(20) NOT NULL");
		s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("				,[State] integer NOT NULL");
		s_CreateTableScript.append("				,[IsDefault] integer NOT NULL");
		s_CreateTableScript.append("				)");
		
		p_DataBase.execSQL(s_CreateTableScript.toString());
		
		InitDefaultData(p_DataBase);
	}

	@Override
	public void OnUpgrade(SQLiteDatabase p_DataBase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String[] GetTableNameAndPK() {
		return new String[]{"AccountBook","AccountBookID"};
	}

	public ContentValues CreatParms(ModelAccountBook p_Info) {
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("AccountBookName", p_Info.getAccountBookName());
		_ContentValues.put("CreateDate",DateTools.getFormatDateTime(p_Info.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		_ContentValues.put("State",p_Info.getState());
		_ContentValues.put("IsDefault",p_Info.getIsDefault());
		return _ContentValues;
	}
}
