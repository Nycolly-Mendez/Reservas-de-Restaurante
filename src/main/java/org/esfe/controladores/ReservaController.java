package org.esfe.controladores;

import org.esfe.Modelos.Reservas;
import org.esfe.servicios.interfaces.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Reservas> reservas = reservaService.buscarTodosPaginados(pageable);
        model.addAttribute("reservas", reservas);

        int totalPages = reservas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "reservas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("reserva", new Reservas());
        model.addAttribute("estados", Reservas.EstadoReserva.values());
        return "reservas/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("reserva") Reservas reservas, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("estados", Reservas.EstadoReserva.values());
            return "reservas/create";
        }

        reservaService.createOrEditOne(reservas);
        attributes.addFlashAttribute("msg", "Reserva creada correctamente");
        return "redirect:/reservas";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<Reservas> optionalReserva = reservaService.buscarPorId(id);
        if (optionalReserva.isPresent()) {
            model.addAttribute("reserva", optionalReserva.get());
            return "reservas/details";
        } else {
            attributes.addFlashAttribute("error", "Reserva no encontrada.");
            return "redirect:/reservas";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<Reservas> optionalReserva = reservaService.buscarPorId(id);

        if (optionalReserva.isPresent()) {
            Reservas reserva = optionalReserva.get();
            model.addAttribute("reserva", reserva);

            // Formatear fecha y hora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            model.addAttribute("formattedFechaHora", reserva.getFechaHora().format(formatter));

            model.addAttribute("estados", Reservas.EstadoReserva.values());
            return "reservas/edit"; // Retorna la vista de edición
        } else {
            model.addAttribute("error", "Reserva no encontrada.");
            return "redirect:/reservas";
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Optional<Reservas> optionalReserva = reservaService.buscarPorId(id);
        if (optionalReserva.isPresent()) {
            model.addAttribute("reserva", optionalReserva.get());
            return "reservas/delete";
        } else {
            return "redirect:/reservas";
        }
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("reserva") Reservas reservas, RedirectAttributes attributes) {
        reservaService.eliminarPorId(reservas.getId());
        attributes.addFlashAttribute("msg", "Reserva eliminada correctamente");
        return "redirect:/reservas";
   }
}