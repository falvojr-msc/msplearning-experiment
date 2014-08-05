package br.com.cast.android.exp.rest.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EBean
public class ImageListAdapter extends BaseAdapter {


	@RootContext
	Context cont;

	private List<EducationalContent> imagens;

	public void setImagem(List<EducationalContent> imagens){
		this.imagens = imagens;
	}
	@Override
	public int getCount() {
		return imagens.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {

		return imagens.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {

		return posicao;
	}

	@Override
	public ImageItemView getView(int posicao, View item, ViewGroup pai) {
		ImageItemView imagemItemView;
		if (item == null) {
			imagemItemView = ImageItemView_.build(cont);
		} else {
			imagemItemView = (ImageItemView) item;
		}
		imagemItemView.configurar(getItem(posicao));
		return imagemItemView;
	}

}
