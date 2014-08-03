package br.com.cast.android.aula2.widget;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.aula2.UserListActivity;
import br.com.cast.android.aula2.rest.entity.User;

/**
 * Adapter responsável pela manipulação de uma lista de {@link User}s.
 * A annotation {@link EBean} indica apenas que essa classe será instanciada automaticamente pelo Android Annotations.
 * 
 * @author venilton.junior
 */
@EBean
public class UserListAdapter extends BaseAdapter {

	/**
	 * Recupera o contexto atual de execução. Neste caso podemos até mesmo restringir esse campo para o tipo {@link UserListActivity}.
	 */
	@RootContext
	Context context;

	private List<User> usuarios;

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public int getCount() {
		return usuarios.size();
	}

	@Override
	public User getItem(int position) {
		return usuarios.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Principal método sobreescrito da classe {@link BaseAdapter}, nele configuramos nosso {@link UserItemView}.
	 */
	@Override
	public UserItemView getView(int position, View itemViewAtual, ViewGroup parent) {
		UserItemView userItemView;
		if (itemViewAtual == null) {
			userItemView = UserItemView_.build(context);
		} else {
			userItemView = (UserItemView) itemViewAtual;
		}
		userItemView.configurarItem(getItem(position));
		return userItemView;
	}
}
