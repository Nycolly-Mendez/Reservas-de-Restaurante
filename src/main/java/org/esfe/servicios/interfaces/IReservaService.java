package org.esfe.servicios.interfaces;

import org.esfe.Modelos.Reservas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface IReservaService {
    Page<Reservas> buscarTodosPaginados(Pageable pageable);

    List<Reservas> obtenerTodos();

    Optional<Reservas> buscarPorId(Integer id);

    Reservas createOrEditOne(Reservas reservas);

    void eliminarPorId(Integer reservas);
}







