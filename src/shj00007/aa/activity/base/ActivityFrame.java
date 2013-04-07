package shj00007.aa.activity.base;

import shj00007.aa.R;
import shj00007.aa.controls.SlideMenuItem;
import shj00007.aa.controls.SlideMenuView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityFrame extends ActivityBase {

	private SlideMenuView mSlideMenuView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
	}

	
	
	protected void AppendMainBody(int pResID) {
		LinearLayout _MainBody = (LinearLayout) findViewById(R.id.layMainBody);

		View _View = LayoutInflater.from(this).inflate(pResID, null);
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		_MainBody.addView(_View, _LayoutParams);
	}

	protected void CreateSlideMenu(int pResID) {
		mSlideMenuView = new SlideMenuView(this);
		String[] _MenuItemArray = getResources().getStringArray(pResID);
		for (int i = 0; i < _MenuItemArray.length; i++) {
			SlideMenuItem _Item = new SlideMenuItem(i, _MenuItemArray[i]);
			mSlideMenuView.Add(_Item);
		}
		mSlideMenuView.BindList();
	}

	protected void slideMenuToggle() {
		mSlideMenuView.Toggle();
	}

	protected void CreateMenu(Menu p_Menu) {
		int _GroupID = 0;
		int _Order = 0;
		int[] _ItemID = { 1, 2 };

		for (int i = 0; i < _ItemID.length; i++) {
			switch (_ItemID[i]) {
			case 1:
				p_Menu.add(_GroupID, _ItemID[i], _Order, R.string.MenuTextEdit);
				break;
			case 2:
				p_Menu.add(_GroupID, _ItemID[i], _Order,
						R.string.MenuTextDelete);
				break;
			default:
				break;
			}
		}
	}

	protected void SetTopBarTitle(String pText) {
		TextView _tvTitle = (TextView) findViewById(R.id.tvTopTitle);
		_tvTitle.setText(pText);

	}
	
	protected void RemoveBottomBox()
	{
		mSlideMenuView = new SlideMenuView(this);
		mSlideMenuView.RemoveBottomBox();
	}
	
	protected void AppendMainBody(View pView)
	{
		LinearLayout _MainBody = (LinearLayout)findViewById(getMainBodyLayoutID());		
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(pView,_LayoutParams);
	//_View.setPadding(15,15,15,15);
	}
	
	private int getMainBodyLayoutID()
	{
		return R.id.layMainBody;
	}
}
