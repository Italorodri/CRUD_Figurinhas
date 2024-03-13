package com.figurinhas.autoestudo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.figurinhas.autoestudo.model.Figurinha;
import com.figurinhas.autoestudo.server.FigurinhaRepository;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/figurinhas")
public class FigurinhaController {
    
    @Autowired
    private FigurinhaRepository repositorio;

    
    @GetMapping()
    public List<Figurinha> listar(){
        return repositorio.findAll();
    }
    
    @PostMapping
    public Figurinha adicionar(@RequestBody Figurinha figurinha){
        return repositorio.save(figurinha);
    }
    
    @PutMapping("/{id}")
    public Figurinha atualizar(@PathVariable Long id, @RequestBody Figurinha figurinha) {
        figurinha.setId(id);
        return repositorio.save(figurinha);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }


}