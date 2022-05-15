package hu.nye.progkor.belepteto.service;

import java.util.List;

import hu.nye.progkor.belepteto.model.InOut;
import hu.nye.progkor.belepteto.model.User;

public interface BeleptetoService {
  List<User> getAllUsers();

  User createUser(User user);

  List<InOut> getAllInOut();

  List<InOut> getInOutsByUser(Long id);

  InOut createInOut(InOut inOut);
}
