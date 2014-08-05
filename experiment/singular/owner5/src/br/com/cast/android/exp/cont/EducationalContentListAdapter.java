package br.com.cast.android.exp.cont;

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

	private List<EducationalContent> contents;


	@Override
	public int getCount() {
		return contents.size();
	}

	@Override
	public EducationalContent getItem(int index) {
		return contents.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int position, View itemViewAtual, ViewGroup parent) {
		EducationalContentItemView contentItemView;
		if (itemViewAtual == null) {
			contentItemView = EducationalContentItemView_.build(context);
		} else {
			contentItemView = (EducationalContentItemView) itemViewAtual;
		}
		contentItemView.configurarItem(getItem(position));
		return contentItemView;
	}

	public List<EducationalContent> getContents() {
		return contents;
	}

	public void setContents(List<EducationalContent> contents) {
		this.contents = contents;
	}

}
