package shj00007.aa.business;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import shj00007.aa.business.base.BusinessBase;
import shj00007.aa.database.sqlitedal.SQLiteDALUser;
import shj00007.aa.model.ModelUser;

public class BusinessUser extends BusinessBase {

	private SQLiteDALUser mSQLiteDALUser;

	public BusinessUser(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
		mSQLiteDALUser = new SQLiteDALUser(pContext);
	}

	public boolean insertUser(ModelUser pInfo) {
		boolean _Result = mSQLiteDALUser.InsertUser(pInfo);
		return _Result;
	}

	public boolean DeleteUserByUserID(int pUserID) {
		String _Condition = " And UserID = " + pUserID;
		boolean _Result = mSQLiteDALUser.DeleteUser(_Condition);
		return _Result;
	}

	public boolean UpdateUserByUserID(ModelUser pModelUser) {
		String _Condition = " UserID = " + pModelUser.getUserID();
		boolean _Result = mSQLiteDALUser.UpdateUser(_Condition, pModelUser);
		return _Result;
	}

	private List<ModelUser> getUser(String pCondition) {
		return mSQLiteDALUser.GetUser(pCondition);
	}

	public ModelUser getModelUserByUserID(int pUserID) {
		List<ModelUser> _List = mSQLiteDALUser.GetUser(" And UserID = "
				+ pUserID);
		if (_List.size() == 1) {
			return _List.get(0);
		} else {
			return null;
		}
	}

	
	
	public List<ModelUser> getNotHideUser() {
		return mSQLiteDALUser.GetUser(" And State = 1");
	}

	public List<ModelUser> getUserListByUserID(String[] pUserID) {
		List<ModelUser> _List = new ArrayList<ModelUser>();
		for (int i = 0; i < pUserID.length; i++) {
			_List.add(getModelUserByUserID(Integer.valueOf(pUserID[i])));
		}
		return _List;
	}

	public boolean IsExistUserByUserName(String pUserName, Integer pUserID) {
		String _Condition = " And UserName = '" + pUserName + "'";
		if (pUserID != null) {
			_Condition += " And UserID <> " + pUserID;
		}
		List _List = mSQLiteDALUser.GetUser(_Condition);
		if (_List.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean HideUserByUserID(int p_UserID)
	{
		String _Condition = " UserID = " + p_UserID;
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("State",0);
		Boolean _Result = mSQLiteDALUser.UpdateUser(_Condition, _ContentValues);

		if(_Result)
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public String GetUserNameByUserID(String p_UserID)
	{
		List<ModelUser> _List = getUserListByUserID(p_UserID.split(","));
		String _Name = "";
		
		for(int i=0;i<_List.size();i++)
		{
			_Name += _List.get(i).getUserName() + ",";
		}
		return _Name;
	}
}
