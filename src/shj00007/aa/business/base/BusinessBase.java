package shj00007.aa.business.base;

import android.content.Context;

public class BusinessBase {
	private Context mContext;

	public BusinessBase(Context pContext) {
		mContext = pContext;
	}

	protected Context getContext() {
		return mContext;
	}

	protected String getString(int pResID) {
		return mContext.getString(pResID);
	}

	protected String getString(int pResID, Object pObject[]) {
		return mContext.getString(pResID, pObject);
	}
}
