package br.com.cast.android.exp;

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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cast.android.exp.base.BaseActivity;
import br.com.cast.android.exp.rest.EducationalContentRestClient;
import br.com.cast.android.exp.rest.entity.EducationalContent;
import br.com.cast.android.exp.widget.VideosListAdapter;

@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.educational_list)
public class EducationalContentListActivity extends BaseActivity {

	@ViewById
	ListView listViewVideos;

	@Bean
	VideosListAdapter videosListAdapter;

	@RestService
	EducationalContentRestClient educaticonRestClient;

	@AfterViews
	void tudoPronto() {
		// Ativa o menu de contexto da nossa ListView:
		super.registerForContextMenu(listViewVideos);
		// Inicia o "Loading" e  carrega a ListView:
		super.iniciarLoading();
		carregarListView();
	}

	@Background
	void carregarListView() {
		List<EducationalContent> educational = educaticonRestClient.findByOwner(EducationalContent.ID_OWNER);
		carregarListView(educational);
	}

	@UiThread
	void carregarListView(List<EducationalContent> edu) {
		videosListAdapter.setEducational(edu);
		listViewVideos.setAdapter(videosListAdapter);
		videosListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		// Redireciona para a UserActivity esperando um resultado identificado pela chave REQUESTCODE_INCLUIR:
		Videos_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}

	/**
	 * A annotation {@link OnActivityResult} faz com que esse método seja chamado quando a Activity iniciada com startForResult for finalizada (.finish()).
	 */
	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_MS01);
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
		super.getMenuInflater().inflate(R.menu.educational_content_list, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Recupera o item selecionado:
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final EducationalContent videoSelecionado = videosListAdapter.getItem(info.position);

		// Identifica a ação selecionada:
		switch(item.getItemId()){
		case R.id.action_editar:
			// Prepara uma intenção para a UserActivity com o usuário selecionado:
			Intent intent = Videos_.intent(this).get();
			intent.putExtra(Videos.CHAVE_VIDEOS, videoSelecionado);
			// Redireciona para a UserActivity esperando um resultado identificado pela chave REQUESTCODE_EDITAR:
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			// Cria um listener para a resposta positiva (Sim) na confirmação de exclusão:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					EducationalContentListActivity.super.iniciarLoading();
					deletarVideo(videoSelecionado);
				}
			};
			// Exibe um alerta de confirmação usando o listener criado para o botão "Sim".
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.tlt_exclusao)
			.setMessage(String.format(getString(R.string.msg_MC01), videoSelecionado.getTitle()))
			.show();

			break;
		}
		return super.onContextItemSelected(item);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_MS02);
	}

	@Background
	void deletarVideo(EducationalContent edu) {
		try {
			educaticonRestClient.delete(edu.getId());

			super.iniciarLoading();
			carregarListView();

			mostrarToast(R.string.msg_MS03);
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
		}
		else if(RESULT_CANCELED != resultCode){
			mostrarToast(R.string.msg_erro_rest);
		}
	}
}
