package br.com.cast.android.exp.rest.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.exp.EducationalContentItemActivity;
import br.com.cast.android.exp.EducationalContentItemActivity_;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EBean
public class EducationalContentListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	private List<EducationalContent> educationalContents;

	public void setEducationalContents(List<EducationalContent> educationalContents) {
		this.educationalContents = educationalContents;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return educationalContents.size();
	}

	@Override
	public EducationalContent getItem(int position) {
		// TODO Auto-generated method stub
		return educationalContents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View itemViewAtual, ViewGroup parent) {
		EducationalContentItemActivity itemView;
		if (itemViewAtual == null) {
			itemView = EducationalContentItemActivity_.build(context);
		} else {
			itemView = (EducationalContentItemActivity) itemViewAtual;
		}
		itemView.configurarItem(getItem(position));
		return itemView;
	}

}
