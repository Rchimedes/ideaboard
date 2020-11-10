package com.lancewade.beltexam.services;

import com.lancewade.beltexam.models.Idea;
import com.lancewade.beltexam.repositories.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {
    private final IdeaRepository ideaRepository;

    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public List<Idea> findAllIdeas(){
        return ideaRepository.findAll();
    }

    public Idea saveIdea(Idea idea) {
        return ideaRepository.save(idea);
    }

    public Idea findIdeaById(Long id) {
        Optional<Idea> optionalIdea = ideaRepository.findById(id);
        return optionalIdea.orElse(null);
    }

    public void deleteIdea(Long id) {
        ideaRepository.deleteById(id);
    }
}
