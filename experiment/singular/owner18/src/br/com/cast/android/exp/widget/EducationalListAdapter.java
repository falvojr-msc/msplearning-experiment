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

	private List<EducationalContent> listaEducationalContents;

	public void setListaEducationalContents(
			List<EducationalContent> listaEducationalContents) {
		this.listaEducationalContents = listaEducationalContents;
	}

	public List<EducationalContent> getListaEducationalContents() {
		return listaEducationalContents;
	}

	@Override
	public int getCount() {
		return listaEducationalContents.size();
	}

	@Override
	public EducationalContent getItem(int posicao) {
		return listaEducationalContents.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int posicao, View educacionalContentAtual, ViewGroup parent) {
		EducationalItemView educationalItemView = null;
		if(null == educationalItemView){
			educationalItemView = EducationalItemView_.build(context);
		}else{
			educationalItemView = (EducationalItemView) educacionalContentAtual;
		}
		educationalItemView.configurarItem(getItem(posicao));
		return educationalItemView;
	}

}
