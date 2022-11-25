package com.jet.carros.api;

import com.jet.carros.domain.Carro;
import com.jet.carros.domain.CarroService;
import com.jet.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CarroDTO> carro = service.getCarroById(id);

        return carro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = service.getCarrosByTipo(tipo);

        return carros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    public String post(@RequestBody Carro carro) {
        Carro c = service.insert(carro);

        return "Carro salvo com sucesso: " + c.getId();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        CarroDTO c = service.update(carro, id);

        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);

        return "Carro deletado com sucesso";
    }
}

