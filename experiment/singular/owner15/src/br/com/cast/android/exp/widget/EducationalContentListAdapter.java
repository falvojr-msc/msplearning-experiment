package br.com.cast.android.exp.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EBean
public class EducationalContentListAdapter extends BaseAdapter {


	@RootContext
	Context context;

	private List<EducationalContent> listItem;

	public void setListDiscipline(List<EducationalContent> listItem) {
		this.listItem = listItem;
	}

	public EducationalContentListAdapter() {
	}

	@Override
	public int getCount() {
		return listItem.size();
	}

	@Override
	public EducationalContent getItem(int position) {
		return listItem.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public EducationalContentItemView getView(int position, View itemViewAtual, ViewGroup nt) {
		EducationalContentItemView  educationalContentItemView;
		if (itemViewAtual == null) {
			educationalContentItemView = EducationalContentItemView_.build(context);
		} else {
			educationalContentItemView = (EducationalContentItemView) itemViewAtual;
		}
		educationalContentItemView.configurarItem(getItem(position));
		return educationalContentItemView;
	}



}
