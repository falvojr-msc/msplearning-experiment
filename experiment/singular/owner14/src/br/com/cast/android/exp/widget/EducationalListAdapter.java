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
public class EducationalListAdapter extends BaseAdapter {


	@RootContext
	Context context;

	private List<EducationalContent> educationalContent;


	public void setEducational(List<EducationalContent> educationalContent) {
		this.educationalContent = educationalContent;
	}


	@Override
	public int getCount() {
		return educationalContent.size();
	}

	@Override
	public EducationalContent getItem(int position) {
		return educationalContent.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public EducationalItemView getView(int position, View itemViewAtual, ViewGroup parent) {
		EducationalItemView educationalContentItemView;
		if (itemViewAtual == null) {
			educationalContentItemView = EducationalItemView_.build(context);
		} else {
			educationalContentItemView = (EducationalItemView) itemViewAtual;
		}
		educationalContentItemView.configurarItem(getItem(position));
		return educationalContentItemView;
	}


}
