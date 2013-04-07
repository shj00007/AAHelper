package shj00007.aa.model;

import java.util.Date;

public class ModelUser {

	// 用户主键id
	private int mUserID;

	// 用户名称
	private String mUserName="";
	// 添加日期
	private Date mCreateDate = new Date();
	// 状态0=失效 1=启动
	private int mState = 1;

	public int getUserID() {
		return mUserID;
	}

	public void setUserID(int pUserID) {
		this.mUserID = pUserID;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String pUserName) {
		this.mUserName = pUserName;
	}

	public Date getCreateDate() {
		return mCreateDate;
	}

	public void setCreateDate(Date pCreateDate) {
		this.mCreateDate = pCreateDate;
	}

	public int getState() {
		return mState;
	}

	public void setState(int pState) {
		this.mState = pState;
	}
}
