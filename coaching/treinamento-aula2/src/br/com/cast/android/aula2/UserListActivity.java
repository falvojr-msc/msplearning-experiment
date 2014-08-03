package br.com.cast.android.aula2;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cast.android.aula2.base.BaseActivity;
import br.com.cast.android.aula2.rest.UserRestClient;
import br.com.cast.android.aula2.rest.entity.User;
import br.com.cast.android.aula2.widget.UserListAdapter;

/**
 * {@link BaseActivity} com a lógica de listagem de usuários, além das chamadas aos fluxos de "Incluir", "Alterar" e "Excluir".<br>
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_user_list)
@OptionsMenu(R.menu.user_list)
public class UserListActivity extends BaseActivity {

	/* LIST VIEW */

	@ViewById
	ListView listViewUsuarios;

	@Bean
	UserListAdapter userListAdapter;

	@RestService
	UserRestClient userRestClient;

	/**
	 * A annotation {@link AfterViews} indica que esse método será chamado quando todos os elementos de visão estiverem prontos.<br>
	 * Nesse momento os campos com {@link ViewById}, {@link Bean} e {@link RestService} também já estarão injetados!
	 */
	@AfterViews
	void tudoPronto() {
		// Ativa o menu de contexto da nossa ListView:
		super.registerForContextMenu(listViewUsuarios);
		// Inicia o "Loading" e  carrega a ListView:
		super.iniciarLoading();
		carregarListView();
	}

	/**
	 * A annotation {@link Background} faz com que esse método seja executado de forma assícrona,
	 * como se fosse uma {@link AsyncTask}. Ela é obrigatória para o consumo de serviços REST.
	 */
	@Background
	void carregarListView() {
		List<User> usuarios = userRestClient.findByOwner(User.ID_OWNER);
		carregarListView(usuarios);
	}

	/**
	 * A annotation {@link UiThread} identifica que esse método fará alterações em elementos visuais,
	 * o que não pode ser feito dentro de um método com {@link Background}.
	 */
	@UiThread
	void carregarListView(List<User> usuarios) {
		userListAdapter.setUsuarios(usuarios);
		listViewUsuarios.setAdapter(userListAdapter);
		userListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	/* INCLUIR */

	/**
	 * A annotation {@link OptionsItem} relaciona o click de um item do menu vinculado a activity com a annotation {@link OptionsMenu}.
	 */
	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		// Redireciona para a UserActivity esperando um resultado identificado pela chave REQUESTCODE_INCLUIR:
		UserActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}

	/**
	 * A annotation {@link OnActivityResult} faz com que esse método seja chamado quando a Activity iniciada com startForResult for finalizada (.finish()).
	 */
	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_inclusao);
	}

	/* REFRESH */

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarListView();
	}

	/* EDITAR e EXCLUIR (CONTEXT MENU) */

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.contextual_user_list , menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Recupera o item selecionado:
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final User usuarioSelecionado = userListAdapter.getItem(info.position);

		// Identifica a ação selecionada:
		switch(item.getItemId()){
		case R.id.action_editar:
			// Prepara uma intenção para a UserActivity com o usuário selecionado:
			Intent intent = UserActivity_.intent(this).get();
			intent.putExtra(UserActivity.CHAVE_USUARIO, usuarioSelecionado);
			// Redireciona para a UserActivity esperando um resultado identificado pela chave REQUESTCODE_EDITAR:
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			// Cria um listener para a resposta positiva (Sim) na confirmação de exclusão:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					UserListActivity.super.iniciarLoading();
					deletarUsuario(usuarioSelecionado);
				}
			};
			// Exibe um alerta de confirmação usando o listener criado para o botão "Sim".
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.titulo_dialog_confirmacao)
			.setMessage(String.format(getString(R.string.msg_dialog_confirmacao_exclusao), usuarioSelecionado.getFirstName()))
			.show();

			break;
		}
		return super.onContextItemSelected(item);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarListView();

		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_edicao);
	}

	@Background
	void deletarUsuario(User usuario) {
		try {
			userRestClient.delete(usuario.getId());

			super.iniciarLoading();
			carregarListView();

			mostrarToast(R.string.msg_sucesso_exclusao);
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	/* ÚTEIS (MENSAGENS) */

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		} else if (RESULT_CANCELED != resultCode) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}
}
