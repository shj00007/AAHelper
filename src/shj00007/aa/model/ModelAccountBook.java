package shj00007.aa.model;

import java.util.Date;

public class ModelAccountBook {
	private int mAccountBookID;
	private String mAccountBookName;
	private Date mCreateDate = new Date();
	private int mState = 1;
	private int mIsDefault;

	public int getAccountBookID() {
		return mAccountBookID;
	}

	public void setAccountBookID(int pAccountBookID) {
		this.mAccountBookID = pAccountBookID;
	}

	public String getAccountBookName() {
		return mAccountBookName;
	}

	public void setAccountBookName(String pAccountBookName) {
		this.mAccountBookName = pAccountBookName;
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

	public int getIsDefault() {
		return mIsDefault;
	}

	public void setIsDefault(int pIsDefault) {
		this.mIsDefault = pIsDefault;
	}
}
