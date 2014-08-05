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
import br.com.cast.android.exp.components.EducationalContentListAdapter;
import br.com.cast.android.exp.rest.EducationalContentRestClient;
import br.com.cast.android.exp.rest.entity.EducationalContent;

@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.educational_content_list)
public class EducationalContentListActivity extends BaseActivity {

	@ViewById
	ListView educationalContentListView;

	@Bean
	EducationalContentListAdapter educationalContentListAdapter;

	@RestService
	EducationalContentRestClient restClient;

	@AfterViews
	void init() {
		super.iniciarLoading();
		registerForContextMenu(educationalContentListView);
		carregarConteudosEducacionais();
	}

	@Background
	void carregarConteudosEducacionais() {
		try {
			List<EducationalContent> conteudosEducacionais = restClient
					.findByOwner(EducationalContent.ID_OWNER);
			atualizarListView(conteudosEducacionais);
		} catch (RestClientException ex) {

		}
	}

	@UiThread
	void atualizarListView(List<EducationalContent> conteudosEducacionais) {
		educationalContentListAdapter
		.setListaConteudoEducacional(conteudosEducacionais);
		educationalContentListView.setAdapter(educationalContentListAdapter);
		educationalContentListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarConteudosEducacionais();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		EducationalContentActivity_.intent(this).startForResult(
				REQUESTCODE_INCLUIR);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		super.getMenuInflater().inflate(
				R.menu.educational_content_item_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final EducationalContent conteudoSelecionado = educationalContentListAdapter
				.getItem(info.position);

		switch (item.getItemId()) {
			case R.id.action_editar:
				Intent intent = EducationalContentActivity_.intent(this).get();
				intent.putExtra(
						EducationalContentActivity.EDUCATIONAL_CONTENT_KEY,
						conteudoSelecionado);
				startActivityForResult(intent, REQUESTCODE_EDITAR);
				break;
			case R.id.action_excluir:
				OnClickListener listenerSim = new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						EducationalContentListActivity.super.iniciarLoading();
						deletarConteudoEducacional(conteudoSelecionado);
					}
				};
				new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(R.string.sim, listenerSim)
				.setNegativeButton(R.string.nao, null)
				.setTitle(R.string.titulo_dialog_confirmacao)
				.setMessage(
						getString(R.string.msg_dialog_confirmacao_exclusao))
						.show();

				break;
		}
		return super.onContextItemSelected(item);
	}

	@Background
	void deletarConteudoEducacional(EducationalContent conteudoEducacional) {
		try {
			restClient.delete(conteudoEducacional.getId());
			mostrarToast(R.string.msg_sucesso_exclusao);
			carregarConteudosEducacionais();
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarConteudosEducacionais();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_inclusao);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarConteudosEducacionais();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_edicao);
	}

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros),
				Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk,
			Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		} else {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

}
