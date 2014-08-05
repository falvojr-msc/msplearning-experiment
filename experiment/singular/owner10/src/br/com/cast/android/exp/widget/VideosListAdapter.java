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
public class VideosListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	private List<EducationalContent> educational;

	public void setEducational(List<EducationalContent> educational) {
		this.educational = educational;
	}

	@Override
	public int getCount() {
		return educational.size();
	}

	@Override
	public EducationalContent getItem(int position) {
		return educational.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public VideosItemView getView(int position, View itemViewAtual, ViewGroup parent) {
		VideosItemView videosItemView;
		if (itemViewAtual == null) {
			videosItemView = VideosItemView_.build(context);
		} else {
			videosItemView = (VideosItemView) itemViewAtual;
		}
		videosItemView.configurarItem(getItem(position));
		return videosItemView;
	}
}
