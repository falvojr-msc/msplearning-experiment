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
public class ImagemListAdapter extends BaseAdapter {

	private List<EducationalContent> educationalContent;

	@RootContext
	Context context;

	@Override
	public int getCount() {
		return educationalContent.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {
		return educationalContent.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public ImagemItemView getView(int position, View itemViewAtual, ViewGroup parent) {
		ImagemItemView imagemItemView;
		if (itemViewAtual == null) {
			imagemItemView = ImagemItemView_.build(context);
		} else {
			imagemItemView = (ImagemItemView) itemViewAtual;
		}
		imagemItemView.configurarItem(getItem(position));
		return imagemItemView;
	}

	public void setEducationalContent(List<EducationalContent> educationalContent) {
		this.educationalContent = educationalContent;
	}



}
