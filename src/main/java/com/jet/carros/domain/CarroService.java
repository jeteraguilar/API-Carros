package com.jet.carros.domain;

import com.jet.carros.domain.dto.CarroDTO;
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

    public List<CarroDTO> getCarros() {
        return repository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public Optional<CarroDTO> getCarroById(Long id) {
        return repository.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return repository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
        return CarroDTO.create(repository.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Carro> optional = repository.findById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            // Atualiza o carro
            repository.save(db);

            return CarroDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Long id) {

        if(getCarroById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Carro> getCarrosFake() {
        List<Carro> carros = new ArrayList<>();

//        carros.add(new Carro(1L, "Fusca"));
//        carros.add(new Carro(2L, "Brasilia"));
//        carros.add(new Carro(3L, "Chevette"));

        return carros;
    }
}
