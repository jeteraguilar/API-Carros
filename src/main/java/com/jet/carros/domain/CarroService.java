package com.jet.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository repository;

    public Iterable<Carro> getCarros() {
        return repository.findAll();
    }

    public Optional<Carro> getCarroById(Long id) {
        return repository.findById(id);
    }

    public List<Carro> getCarrosByTipo(String tipo) {
        return repository.findByTipo(tipo);
    }

    public Carro insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
        return repository.save(carro);
    }

    public Carro update(Carro carro, Long id) {
        Assert.isNull(carro.getId(), "Não foi possível atualizar o registro");

        //Busca o carro do banco de dados
        Optional<Carro> optional = getCarroById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            //Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            //Atualiza carro
            repository.save(db);

            return db;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {

        if(getCarroById(id).isPresent()) {
            repository.deleteById(id);
        }
    }

    public List<Carro> getCarrosFake() {
        List<Carro> carros = new ArrayList<>();

//        carros.add(new Carro(1L, "Fusca"));
//        carros.add(new Carro(2L, "Brasilia"));
//        carros.add(new Carro(3L, "Chevette"));

        return carros;
    }
}
