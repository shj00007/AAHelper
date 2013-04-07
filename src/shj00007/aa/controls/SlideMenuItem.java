package shj00007.aa.controls;

public class SlideMenuItem {
	private int mItemID;
	private String mTitle;

	public SlideMenuItem(int pItemID,String pTitle){
		this.mItemID=pItemID;
		this.mTitle=pTitle;
	}
	
	public int getItemID() {
		return mItemID;
	}

	public void setmItemID(int mItemID) {
		this.mItemID = mItemID;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
}
