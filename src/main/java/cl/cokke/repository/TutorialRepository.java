package cl.cokke.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.cokke.model.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	public List<Tutorial> findByPublicado(boolean publicado);
	public List<Tutorial> findByTituloIgnoreCase(String titulo);
	
}
