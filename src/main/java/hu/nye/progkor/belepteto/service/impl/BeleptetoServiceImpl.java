package hu.nye.progkor.belepteto.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hu.nye.progkor.belepteto.model.InOut;
import hu.nye.progkor.belepteto.model.User;
import hu.nye.progkor.belepteto.model.exception.NotFoundException;
import hu.nye.progkor.belepteto.model.exception.WrongDirectionException;
import hu.nye.progkor.belepteto.service.BeleptetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeleptetoServiceImpl implements BeleptetoService {

  private final List<User> userList = new ArrayList<>();
  private final List<InOut> inOutList = new ArrayList<>();
  private final List<InOut> inOutsByUser = new ArrayList<>();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Autowired
  public BeleptetoServiceImpl() {
    userList.add(new User(1L, "Teszt", "Elek"));
    userList.add(new User(2L, "Nagy", "Béla"));
    inOutList.add(new InOut(1L, LocalDateTime.of(2000, Month.MAY, 22, 11,
            22, 0).format(formatter), "Be"));
    inOutList.add(new InOut(2L, LocalDateTime.of(2022, Month.MAY, 14, 16,
            0, 0).format(formatter), "Be"));
  }


  public BeleptetoServiceImpl(final List<User> users) {
    userList.addAll(users);
  }

  @Override
  public List<User> getAllUsers() {
    return Collections.unmodifiableList(userList);
  }

  @Override
  public User getUser(Long id) {
    return userList.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElseThrow(NotFoundException::new);
  }

  @Override
  public User createUser(User user) {
    User newUser = new User();
    newUser.setId(getNextUserId());
    newUser.setFirstName(user.getFirstName());
    newUser.setLastName(user.getLastName());
    userList.add(newUser);
    return newUser;
  }

  @Override
  public List<InOut> getAllInOut() {
    return Collections.unmodifiableList(inOutList);
  }

  @Override
  public List<InOut> getInOutsByUser(Long id) {
    return inOutList.stream()
            .filter(inout -> inout.getId().equals(id)).collect(Collectors.toList());
  }

  @Override
  public InOut getInOut(Long id) {
    return inOutList.stream()
            .filter(inOut -> inOut.getId().equals(id))
            .findFirst()
            .orElseThrow(NotFoundException::new);
  }

  @Override
  public InOut createInOut(InOut inOut) {
    InOut newInOut = new InOut();
    if (userList.stream()
            .anyMatch(user -> user.getId()
                    .equals(inOut.getId()))
    ) {
      newInOut.setId(inOut.getId());
    } else {
      throw new NotFoundException();
    }
    newInOut.setTime(LocalDateTime.now().format(formatter));
    List<InOut> userInOut = getInOutsByUser(inOut.getId());
    if (userInOut.size() != 0) {

      for (InOut user :
              userInOut) {
        System.out.println(user.getId() + " " + user.getIn());
      }

      if (inOut.getIn().equals("Be")) {
        if (userInOut.get(userInOut.size() - 1).getIn().equals("Ki")) {
          newInOut.setIn(inOut.getIn());
        } else {
          throw new WrongDirectionException();
        }
      } else {
        if (userInOut.get(userInOut.size() - 1).getIn().equals("Be")) {
          newInOut.setIn(inOut.getIn());
        } else {
          throw new WrongDirectionException();
        }
      }
    } else {
      newInOut.setIn(inOut.getIn());
    }


    inOutList.add(newInOut);
    return newInOut;
  }

  private long getNextUserId() {
    return getLastUserId() + 1L;
  }

  private Long getLastUserId() {
    return userList.stream()
            .mapToLong(User::getId)
            .max()
            .orElse(0);
  }

  private long getNextInOutId() {
    return getLastInOutId() + 1L;
  }

  private Long getLastInOutId() {
    return inOutList.stream()
            .mapToLong(InOut::getId)
            .max()
            .orElse(0);
  }
}
