package hu.nye.progkor.belepteto.controller;

import java.util.List;

import hu.nye.progkor.belepteto.model.InOut;
import hu.nye.progkor.belepteto.model.User;
import hu.nye.progkor.belepteto.model.exception.NotFoundException;
import hu.nye.progkor.belepteto.model.exception.WrongDataException;
import hu.nye.progkor.belepteto.service.BeleptetoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/belepteto")
public class BeleptetoController {

  private final BeleptetoService beleptetoService;

  public BeleptetoController(final BeleptetoService beleptetoService) {
    this.beleptetoService = beleptetoService;
  }

  @GetMapping
  public String homepage(final Model model) {
    return "belepteto/index";
  }

  @GetMapping("/userList")
  public String getAllUser(final Model model) {
    final List<User> users = beleptetoService.getAllUsers();
    model.addAttribute("users", users);
    return "belepteto/userList";
  }

  @GetMapping("/inOutList")
  public String getAllInOut(final Model model) {
    final List<InOut> inOuts = beleptetoService.getAllInOut();
    model.addAttribute("inOuts", inOuts);
    return "belepteto/inOutList";
  }

  @GetMapping("/registration")
  public String userForm(Model model) {
    model.addAttribute("user", new User());
    return "belepteto/registration";
  }

  @PostMapping("/registration")
  public String createUser(@ModelAttribute("user") User user) {
    try {
      User newUser = beleptetoService.createUser(user);
    } catch (WrongDataException e) {
      return "belepteto/wrongData";
    }
    return "belepteto/success";
  }

  @GetMapping("/transaction")
  public String inOutForm(Model model) {
    model.addAttribute("inOut", new InOut());
    return "belepteto/transaction";
  }

  @PostMapping("/transaction")
  public String createInOut(@ModelAttribute("inOut") InOut inOut) {
    try {
      InOut newInOut = beleptetoService.createInOut(inOut);
    } catch (NotFoundException e) {
      return "belepteto/userNotFound";
    } catch (WrongDataException e) {
      return "belepteto/wrongData";
    }
    return "belepteto/success";
  }

  @GetMapping("/success")
  public String success(final Model model) {
    return "belepteto/success";
  }

}
