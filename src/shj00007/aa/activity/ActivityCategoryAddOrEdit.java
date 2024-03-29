package shj00007.aa.activity;

import shj00007.aa.R;
import shj00007.aa.activity.base.ActivityFrame;
import shj00007.aa.business.BusinessCategory;
import shj00007.aa.model.ModelCategory;
import shj00007.aa.utility.RegexTools;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityCategoryAddOrEdit extends ActivityFrame
	implements android.view.View.OnClickListener
{
	
	private Button btnSave;
	private Button btnCancel;
	
	private BusinessCategory mBusinessCategory;
	private ModelCategory mModelCategory;
	private Spinner spParentID;
	private EditText etCategoryName;

	@Override
	public void onClick(View v) {
		int _ID = v.getId();
		
		switch (_ID) {
		case R.id.btnSave:
			AddOrEditCategory();
			break;
		case R.id.btnCancel:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AppendMainBody(R.layout.category_add_or_edit);
		RemoveBottomBox();
		InitView();
		InitVariable();
		BindData();
		SetTitle();
		InitListeners();
	}
	
	private void SetTitle() {
		String _Titel;
		if(mModelCategory == null)
		{
			_Titel = getString(R.string.ActivityTitleCategoryAddOrEdit, new Object[]{getString(R.string.TitleAdd)});
		}
		else {
			_Titel = getString(R.string.ActivityTitleCategoryAddOrEdit, new Object[]{getString(R.string.TitleEdit)});
			InitData(mModelCategory);
		}
		
		SetTopBarTitle(_Titel);
	}

	protected void InitView() {
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		etCategoryName = (EditText)findViewById(R.id.etCategoryName);
		spParentID = (Spinner)findViewById(R.id.SpinnerParentID);
	}

	protected void InitListeners() {
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}
	

	protected void InitVariable() {
		mBusinessCategory = new BusinessCategory(this);
		mModelCategory = (ModelCategory) getIntent().getSerializableExtra("ModelCategory");
	}

	protected void BindData()
	{
		ArrayAdapter<ModelCategory> _ArrayAdapter = mBusinessCategory.GetRootCategoryArrayAdapter();
		spParentID.setAdapter(_ArrayAdapter);
	}
	
	private void InitData(ModelCategory p_ModelCategory)
	{
		etCategoryName.setText(p_ModelCategory.GetCategoryName());
		ArrayAdapter _ArrayAdapter = (ArrayAdapter) spParentID.getAdapter();
		
		if(p_ModelCategory.GetParentID() != 0)
		{
			int _Position = 0;
			for(int i=1;i<_ArrayAdapter.getCount();i++)
			{
				ModelCategory _ModelCategory = (ModelCategory)_ArrayAdapter.getItem(i);
				if(_ModelCategory.GetCategoryID() == p_ModelCategory.GetParentID())
				{
					_Position = _ArrayAdapter.getPosition(_ModelCategory);
				}
			}	
			spParentID.setSelection(_Position);
		}
		else {
			int _Count = mBusinessCategory.GetNotHideCountByParentID(p_ModelCategory.GetCategoryID());
			if(_Count != 0)
			{
				spParentID.setEnabled(false);
			}
		}
	}

	private void AddOrEditCategory() {
		String _CategoryName = etCategoryName.getText().toString().trim();
		Boolean _CheckResult = RegexTools.IsChineseEnglishNum(_CategoryName);
	    if(!_CheckResult)
	    {
			Toast.makeText(this, getString(R.string.CheckDataTextChineseEnglishNum,new Object[]{getString(R.string.TextViewTextCategoryName)}), 1).show();
	    	return;
	    }
		
		if(mModelCategory == null)
		{
			mModelCategory = new ModelCategory();
			mModelCategory.SetTypeFlag(getString(R.string.PayoutTypeFlag));
			mModelCategory.SetPath("");
		}
		mModelCategory.SetCategoryName(_CategoryName);
		if(!spParentID.getSelectedItem().toString().equals("--��ѡ��--"))
		{
			ModelCategory _ModelCategory = (ModelCategory)spParentID.getSelectedItem();
			if(_ModelCategory != null)
			{
				mModelCategory.SetParentID(_ModelCategory.GetCategoryID());
			}
		} else {
			mModelCategory.SetParentID(0);
		}
		
		Boolean _Result = false;
		
		if(mModelCategory.GetCategoryID() == 0)
		{
			_Result = mBusinessCategory.InsertCategory(mModelCategory);
		}
		else {
			_Result = mBusinessCategory.UpdateCategoryByCategoryID(mModelCategory);
		}

		if(_Result)
		{
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddSucceed), 1).show();
			finish();
		}
		else {
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddFail), 1).show();
		}
	}
}
