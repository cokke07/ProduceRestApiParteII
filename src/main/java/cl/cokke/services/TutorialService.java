package cl.cokke.services;

import java.util.List;
import java.util.Optional;

import cl.cokke.model.Tutorial;

public interface TutorialService {
	
	public List<Tutorial> findAll();
	public List<Tutorial> findByTitulo(String titulo);
	public Optional<Tutorial> findById(Long id);
	public Tutorial save(Tutorial tutorial);
	public Tutorial update(Tutorial tutorial);
	public void deleteAll();
	public void deleteById(Long id);
	public List<Tutorial> findByPublicado(boolean publicado);
	
}
