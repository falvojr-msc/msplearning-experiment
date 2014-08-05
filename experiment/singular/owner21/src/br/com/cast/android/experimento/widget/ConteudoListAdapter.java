package br.com.cast.android.experimento.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.experimento.rest.entity.EducationalContent;

/**
 * Adapter responsável pela manipulação de uma lista de {@link User}s.
 * A annotation {@link EBean} indica apenas que essa classe será instanciada automaticamente pelo Android Annotations.
 * 
 * @author venilton.junior
 */
@EBean
public class ConteudoListAdapter extends BaseAdapter {

	/**
	 * Recupera o contexto atual de execução. Neste caso podemos até mesmo restringir esse campo para o tipo {@link UserListActivity}.
	 */
	@RootContext
	Context context;

	private List<EducationalContent> conteudos;

	public void setUsuarios(List<EducationalContent> conteudos) {
		this.conteudos = conteudos;
	}

	@Override
	public int getCount() {
		return conteudos.size();
	}

	@Override
	public EducationalContent getItem(int position) {
		return conteudos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Principal método sobreescrito da classe {@link BaseAdapter}, nele configuramos nosso {@link UserItemView}.
	 */
	@Override
	public  ConteudoItemView getView(int position, View itemViewAtual, ViewGroup parent) {
		ConteudoItemView conteudoItemView;
		if (itemViewAtual == null) {
			conteudoItemView = ConteudoItemView_.build(context);
		} else {
			conteudoItemView = (ConteudoItemView) itemViewAtual;
		}
		conteudoItemView.configurarItem(getItem(position));
		return conteudoItemView;
	}
}
