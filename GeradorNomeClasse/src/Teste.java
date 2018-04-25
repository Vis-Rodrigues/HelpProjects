import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JOptionPane;

public class Teste {

	public static void main(String[] args) throws IOException {

		String nomeDiretorio;
		
		nomeDiretorio = JOptionPane.showInputDialog("Por favor, insira o diretório das classes");
		DiretorioUtils dirUtils = new DiretorioUtils();
		LeitorTextoUtils leitor = new LeitorTextoUtils();
		try {
			dirUtils.listarArquivo(dirUtils.isDiretorioCorreto(nomeDiretorio));
			List<Path> lstArquivoComFiltro = dirUtils.filtrarArquivoJava(dirUtils.getFilesJava());
			File arquivo = leitor.escreverArquivo(leitor.capturarPacoteClasse(lstArquivoComFiltro));
			dirUtils.abrirDiretorio(arquivo);
		} catch (IOException e) {
			leitor.escreverArquivo("ERRO: " + e.getMessage() + "\n");
			e.printStackTrace();
		}
	}

}
