package org.esfe.repositorios;

import org.esfe.Modelos.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaRepository extends JpaRepository<Reservas, Integer> {
}
