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

	private List<EducationalContent> listEducational;

	public void setEducational(List<EducationalContent> listEducational) {
		this.listEducational = listEducational;
	}

	@Override
	public int getCount() {
		return listEducational.size();
	}

	@Override
	public EducationalContent getItem(int position) {
		return listEducational.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public EducationalItemView getView(int position, View itemViewAtual,
			ViewGroup parent) {
		EducationalItemView educationalItemView;
		if (itemViewAtual == null) {
			educationalItemView = EducationalItemView_.build(context);
		} else {
			educationalItemView = (EducationalItemView) itemViewAtual;
		}
		educationalItemView.configurarItem(getItem(position));
		return educationalItemView;
	}

}
