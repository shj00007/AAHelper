package shj00007.aa.activity;

import shj00007.aa.R;
import shj00007.aa.activity.base.ActivityFrame;
import shj00007.aa.adapter.AdapterAccountBookSelect;
import shj00007.aa.business.BusinessAccountBook;
import shj00007.aa.business.BusinessStatistics;
import shj00007.aa.controls.SlideMenuItem;
import shj00007.aa.controls.SlideMenuView.onSlideMenuListener;
import shj00007.aa.model.ModelAccountBook;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class ActivityStatistics extends ActivityFrame implements onSlideMenuListener {

	private TextView tvStatisticsResult;
	private BusinessStatistics mBusinessStatistics;
	private ModelAccountBook mAccountBook;
	private BusinessAccountBook mBusinessAccountBook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AppendMainBody(R.layout.statistics);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		SetTitle();
		CreateSlideMenu(R.array.SlideMenuStatistics);
	}
	
	private void SetTitle() {
//		int _Count = lvStatisticsList.getCount();
//		int _Count = 7;
		String _Titel = getString(R.string.ActivityTitleStatistics, new Object[]{mAccountBook.getAccountBookName()});
		SetTopBarTitle(_Titel);
	}

	protected void InitView() {
		tvStatisticsResult = (TextView) findViewById(R.id.tvStatisticsResult);
	}

	protected void InitListeners() {
	}
	
	protected void InitVariable() {
		mBusinessStatistics = new BusinessStatistics(ActivityStatistics.this);
		mBusinessAccountBook = new BusinessAccountBook(ActivityStatistics.this);
		mAccountBook = mBusinessAccountBook.GetDefaultModelAccountBook();
	}

	protected void BindData()
	{
		ShowProgressDialog(R.string.StatisticsProgressDialogTitle, R.string.StatisticsProgressDialogWaiting);
		new BindDataThread().start();
	}
	
	private class BindDataThread extends Thread
	{
		@Override
		public void run() {
			String _Result = mBusinessStatistics.GetPayoutUserIDByAccountBookID(mAccountBook.getAccountBookID());
			Message _Message = new Message();
			_Message.obj = _Result;
			_Message.what = 1;
			mHandler.sendMessage(_Message);
		}
	}
	
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String _Result = (String) msg.obj;
				tvStatisticsResult.setText(_Result);
				DismissProgressDialog();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onSlideMenuItemClick(View p_View, SlideMenuItem p_SlideMenuItem) {
		slideMenuToggle();
		if (p_SlideMenuItem.getItemID() == 0) {
			ShowAccountBookSelectDialog();
		}
		if(p_SlideMenuItem.getItemID() == 1) {
			ExportData();
		}
	}
	
	private void ShowAccountBookSelectDialog()
	{
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
		ListView _ListView = (ListView)_View.findViewById(R.id.ListViewSelect);
		AdapterAccountBookSelect _AdapterAccountBookSelect = new AdapterAccountBookSelect(this);
		_ListView.setAdapter(_AdapterAccountBookSelect);

		_Builder.setTitle(R.string.ButtonTextSelectAccountBook);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnAccountBookItemClickListener(_AlertDialog));
	}
	
	private class OnAccountBookItemClickListener implements AdapterView.OnItemClickListener
	{
		private AlertDialog m_AlertDialog;
		public OnAccountBookItemClickListener(AlertDialog p_AlertDialog)
		{
			m_AlertDialog = p_AlertDialog;
		}
		@Override
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,
				long arg3) {
			mAccountBook = (ModelAccountBook)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
			BindData();
			m_AlertDialog.dismiss();
		}
	}
	
	
	private void ExportData() {
		String _Result = "";
		try {
			_Result = mBusinessStatistics.ExportStatistics(mAccountBook.getAccountBookID());
		} catch (Exception e) {
			_Result = "错误";
		}
		Toast.makeText(this, _Result, Toast.LENGTH_LONG).show();
	}
}
