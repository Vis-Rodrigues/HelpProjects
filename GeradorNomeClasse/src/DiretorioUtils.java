import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DiretorioUtils {

	private List<Path> files;
	
	public DiretorioUtils() {
		files = new ArrayList<>();
	}
	
	public Path isDiretorioCorreto(String diretorio) throws IOException{
		File folder = new File(diretorio);
		if (!folder.exists())
			throw new IOException("O diretório selecionado não existe!");
		else
			return Paths.get(diretorio);	
	}
	
	public void listarArquivo(Path path) throws IOException {		
		DirectoryStream<Path> stream = Files.newDirectoryStream(path);
		for(Path entry: stream) {
			if (Files.isDirectory(entry))
				listarArquivo(entry);
			files.add(entry);
		}
	}
	
	public List<Path> getFilesJava(){
		return files;
	}
	
	public List<Path> filtrarArquivo(List<Path> lstArquivo, String filtro){
		List<Path> lstArquivoComFiltro = new ArrayList<>();
		for(Path arquivo:files) {
			if (arquivo.toString().contains(filtro))
				lstArquivoComFiltro.add(arquivo);
		}
		return lstArquivoComFiltro;
	}
	
	public List<Path> filtrarArquivoJava(List<Path> lstArquivo){
		return filtrarArquivo(lstArquivo, ".java");
	}
	
	public void abrirDiretorio(File arquivo) throws IOException {
		Desktop.getDesktop().open(arquivo);
	}
}
