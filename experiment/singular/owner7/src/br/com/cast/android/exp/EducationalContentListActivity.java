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
import br.com.cast.android.exp.rest.widget.ImageListAdapter;


@EActivity(R.layout.activity_educational_content_list)
@OptionsMenu(R.menu.educational_content_list)
public class EducationalContentListActivity extends BaseActivity {

	@ViewById
	ListView listViewImagem;

	@Bean
	ImageListAdapter imagemAdapter;

	@RestService
	EducationalContentRestClient rest;

	@AfterViews
	void tudoOk() {
		super.registerForContextMenu(listViewImagem);
		super.iniciarLoading();
		carregarLista();
	}


	//Carregar Lista
	@Background
	void carregarLista(){
		List<EducationalContent> imagens = rest.findByOwner(EducationalContent.ID_OWNER);
		carregarListView(imagens);
	}

	@UiThread
	void carregarListView (List<EducationalContent> imagens){
		imagemAdapter.setImagem(imagens);
		listViewImagem.setAdapter(imagemAdapter);
		imagemAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	//	---------------------------------------------------------------

	//On Activities Results
	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarLista();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_inclusao);
	}
	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarLista();

		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_edicao);
	}
	//------------------------------------------------------
	//Context Menu
	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final EducationalContent imagemSelecionada = imagemAdapter.getItem(info.position);
		switch(item.getItemId()){
		case R.id.action_editar:
			Intent intent = ImageActivity_.intent(this).get();
			intent.putExtra(ImageActivity.CHAVE_IMAGEM, imagemSelecionada);
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					EducationalContentListActivity.super.iniciarLoading();
					deletarImagem(imagemSelecionada);
				}
			};
			// Exibe um alerta de confirmação usando o listener criado para o botão "Sim".
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.msg_confirmacao)
			.setMessage(String.format(getString(R.string.msg_sucesso_exclusao), imagemSelecionada.getTitle()))
			.show();

			break;

		}



		return true;
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.menu_context, menu);
	}



	@Background
	void deletarImagem(EducationalContent imagem){
		try {
			rest.delete(imagem.getId());
			super.iniciarLoading();
			carregarLista();
			mostrarToast(R.string.msg_sucesso_exclusao);

		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	//Menus Bar
	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarLista();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		ImageActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}
	//------------------------------------------------------------------



	//TOAST!
	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		}else if(RESULT_CANCELED != resultCode){
			mostrarToast(R.string.msg_erro_rest);
		}
	}
	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}
}
