package br.com.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.agenda.DAO.AlunoDAO;
import br.com.agenda.converter.AlunoConverter;
import br.com.agenda.modelo.Aluno;

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private Context context;
    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando Alunos...", true, true);
    }

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaalunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);



        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
         Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
