package org.esfe.servicios.implementaciones;
import org.esfe.Modelos.Reservas;
import org.esfe.repositorios.IReservaRepository;
import org.esfe.servicios.interfaces.IReservaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ReservaService  implements IReservaService {
    @Autowired
    private IReservaRepository reservaRepository;


    @Override
    public Page<Reservas> buscarTodosPaginados(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    @Override
    public List<Reservas> obtenerTodos() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reservas> buscarPorId(Integer id) {
        return reservaRepository.findById(id);
    }

    @Override
    public Reservas createOrEditOne(Reservas reservas) {
        return reservaRepository.save(reservas);
    }

    @Override
    public void eliminarPorId(Integer id) {
       reservaRepository.deleteById(id);
    }
}





