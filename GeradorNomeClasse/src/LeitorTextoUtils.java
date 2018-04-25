import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

public class LeitorTextoUtils {

	private static final Pattern ext = Pattern.compile("(?<=.)\\.[^.]+$");

	private String lerClasse(String valor) throws IOException {

		String pacote = null, classe = null, nomeCompleto = null, linha;
		FileReader arquivo = new FileReader(valor);

		BufferedReader br = new BufferedReader(arquivo);
		linha = br.readLine();
		while (linha != null) {
			if (linha.toLowerCase().contains("package")) {
				pacote = linha;
			} else if (linha.toLowerCase().contains("class")) {
				classe = getFileNameWithoutExtension(new File(valor));
				break;
			}
			linha = br.readLine();
		}

		br.close();

		if (pacote != null & classe != null) {
			nomeCompleto = String.format("%s.%s", pacote.replace("package", "").replace(";", "").trim(),
					classe.replace("public", "").replace("class", "").replace("{", "").trim());
		}
		return nomeCompleto;
	}

	public List<String> capturarPacoteClasse(List<Path> lstArquivo) throws IOException {
		List<String> lstNomeClasse = new ArrayList<>();
		String texto;
		for (Path p : lstArquivo) {
			texto = lerClasse(p.toString());
			if (texto != null | texto != "")
				lstNomeClasse.add(texto);
		}
		return lstNomeClasse;
	}

	public File escreverArquivo(List<String> lstNomeCompleto) throws IOException {
		File diretorio = criarArquivo();
		FileWriter fw = new FileWriter(diretorio);
		PrintWriter pw = new PrintWriter(fw);

		for (String texto : lstNomeCompleto) {
			pw.println(texto);
		}
		fw.close();
		
		return diretorio.getParentFile();
	}

	public void escreverArquivo(String texto) throws IOException {
		File diretorio = criarArquivo();
		FileWriter fw = new FileWriter(diretorio);
		PrintWriter pw = new PrintWriter(fw);

		pw.println(texto);
		fw.close();
	}

	private File criarArquivo() throws IOException {
		String nomeArquivo = String.format("classes_%s.txt",
				new SimpleDateFormat("dd_MM_yy-HH_mm_ss").format(new Date()).toString());
		String diretorio = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
				+ "\\NomenclaturaClasse\\" + nomeArquivo;
		new File(diretorio).getParentFile().mkdirs();
		return new File(diretorio);
	}

	public static String getFileNameWithoutExtension(File file) {
		return ext.matcher(file.getName()).replaceAll("");
	}

}
