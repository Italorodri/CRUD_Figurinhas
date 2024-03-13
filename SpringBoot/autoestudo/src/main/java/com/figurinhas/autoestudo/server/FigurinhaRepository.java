package com.figurinhas.autoestudo.server;

import org.springframework.data.jpa.repository.JpaRepository;

import com.figurinhas.autoestudo.model.Figurinha;

//extends JpaRepository<Figurinha, Long>: Indica que FigurinhaRepository estende JpaRepository, 
//uma interface do Spring Data JPA. Isso significa que FigurinhaRepository herda todos os métodos prontos para uso definidos por JpaRepository
public interface FigurinhaRepository extends JpaRepository<Figurinha, Long> {

}
