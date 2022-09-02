package cl.cokke.services;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.cokke.model.Tutorial;
import cl.cokke.repository.TutorialRepository;

@Service
public class TutorialServiceImp implements TutorialService {

	//inyeccion de dependencias
		@Autowired
		private TutorialRepository tutorialRepository;
		
		//metodo para consultar por todos los tutoriales
		@Override
		@Transactional(readOnly = true)
		public List<Tutorial> findAll() {
			return tutorialRepository.findAll();
		}
		//metodo para consultar tutorial por titulo
		@Override
		@Transactional(readOnly = true)
		public List<Tutorial> findByTitulo(String titulo) {
			return tutorialRepository.findByTituloIgnoreCase(titulo);
		}
		//metodo para buscar tutorial por id
		@Override
		@Transactional(readOnly = true)
		public Optional<Tutorial> findById(Long id) {
			return tutorialRepository.findById(id);
		}
		//metodo para guardar un nuevo registro de tutorial
		@Override
		@Transactional
		public Tutorial save(Tutorial tutorial) {
			return tutorialRepository.save(tutorial);
		}
		//metodo para actualizar un registro existente de tutorial
		@Override
		@Transactional
		public Tutorial update(Tutorial tutorial) {
			return tutorialRepository.save(tutorial);
		}
		//metodo para eliminar todos los registros de tutorial
		@Override
		@Transactional
		public void deleteAll() {
			tutorialRepository.deleteAll();
		}
		//metodo para eliminar tutorial por id
		@Override
		@Transactional
		public void deleteById(Long id) {
			tutorialRepository.deleteById(id);
		}
		//metodo para buscar tutorial por atributo publicado
		@Override
		@Transactional(readOnly = true)
		public List<Tutorial> findByPublicado(boolean publicado) {
			return tutorialRepository.findByPublicado(publicado);
		}
	}
