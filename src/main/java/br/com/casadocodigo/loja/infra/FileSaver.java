package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe para gravar os arquivos anexasoa na aplicação
 *
 */
@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;
	
	/**
	 * Gravando o arquivo anexado.
	 * @param baseFolder
	 * @param file
	 * @return
	 */
	public String write(String baseFolder,MultipartFile file){
		
		try {
			String realPath = request.getServletContext().getRealPath("/"+baseFolder);			
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			
			return baseFolder + "/" + file.getOriginalFilename();
			//return path;// seria o nome completo
			
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
		
}
	
	
	
}
