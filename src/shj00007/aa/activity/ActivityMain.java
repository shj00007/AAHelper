package shj00007.aa.activity;

import shj00007.aa.R;
import shj00007.aa.activity.base.ActivityFrame;
import shj00007.aa.adapter.AdapterAppGrid;
import shj00007.aa.controls.SlideMenuItem;
import shj00007.aa.controls.SlideMenuView.onSlideMenuListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ActivityMain extends ActivityFrame implements onSlideMenuListener {
	private GridView gvAppGrid = null;
	private AdapterAppGrid mAdapterAppGrid = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppendMainBody(R.layout.main_body);

		InitVariable();
		InitView();
		InitListeners();
		BindData();
		CreateSlideMenu(R.array.SlideMenuActivityMain);
	}

	public void InitVariable() {
		mAdapterAppGrid = new AdapterAppGrid(this);
	}

	public void InitView() {
		gvAppGrid = (GridView) findViewById(R.id.gvAppGrid);
	}

	public void InitListeners() {
		gvAppGrid.setOnItemClickListener(new onAppGridItemClickListener());
	}

	public void BindData() {
		gvAppGrid.setAdapter(mAdapterAppGrid);
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		// TODO Auto-generated method stub
		Toast.makeText(this, pSlideMenuItem.getmTitle(), 1).show();
	}

	private class onAppGridItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> pAdapter, View pView,
				int pPosition, long arg3) {
			// TODO Auto-generated method stub
			String _MenuName = (String) pAdapter.getAdapter()
					.getItem(pPosition);

			if (_MenuName.equals(getString(R.string.appGridTextUserManage))) {
				OpenActivity(ActivityUser.class);
				return;
			}
			if (_MenuName
					.equals(getString(R.string.appGridTextAccountBookManage))) {
				OpenActivity(ActivityAccountBook.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextCategoryManage))) {
				OpenActivity(ActivityCategory.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextPayoutAdd))) {
				OpenActivity(ActivityPayoutAddOrEdit.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextPayoutManage))) {
				OpenActivity(ActivityPayout.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextStatisticsManage)))
			{
				OpenActivity(ActivityStatistics.class);
				return;
			}

		}

	}
}
