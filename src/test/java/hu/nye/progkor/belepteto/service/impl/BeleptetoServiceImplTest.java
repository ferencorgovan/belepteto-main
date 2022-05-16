package hu.nye.progkor.belepteto.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hu.nye.progkor.belepteto.model.InOut;
import hu.nye.progkor.belepteto.model.User;
import hu.nye.progkor.belepteto.model.exception.NotFoundException;
import hu.nye.progkor.belepteto.model.exception.WrongDataException;
import hu.nye.progkor.belepteto.service.BeleptetoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BeleptetoServiceImplTest {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private final ZoneId zid = ZoneId.of("Europe/Paris");

  private static final User TESZT_ELEK = new User(1L, "Teszt", "Elek");
  private static final User NAGY_BELA = new User(2L, "Nagy", "Béla");
  private static final InOut ONE = new InOut(1L, LocalDateTime.of(2000, Month.MAY, 22, 11,
          22, 0).format(formatter), "Be");
  private static final InOut TWO = new InOut(2L, LocalDateTime.of(2022, Month.MAY, 14, 16,
          0, 0).format(formatter), "Ki");
  private static final List<User> USERS = List.of(TESZT_ELEK, NAGY_BELA);
  private static final List<InOut> INOUTS = List.of(ONE, TWO);

  private static final String GIPSZ_JAKAB_VEZETEKNEV = "Gipsz";
  private static final String GIPSZ_JAKAB_KERESZTNEV = "Jakab";


  private BeleptetoService underTest;

  @BeforeEach
  void setUp() { underTest = new BeleptetoServiceImpl(USERS, INOUTS); }

  @Test
  void getAllUsersShouldReturnAllUsers() {
    // when
    final List<User> actual = underTest.getAllUsers();
    // then
    Assertions.assertEquals(actual, USERS);
  }

  @Test
  void createUserShouldReturnUserWhenCreated() {
    // given
    final User gipszJakab = new User(null, GIPSZ_JAKAB_VEZETEKNEV, GIPSZ_JAKAB_KERESZTNEV);
    final User expectedGipszJakab = new User(3L, GIPSZ_JAKAB_VEZETEKNEV, GIPSZ_JAKAB_KERESZTNEV);
    //when
    final User actual = underTest.createUser(gipszJakab);
    //then
    Assertions.assertEquals(actual, expectedGipszJakab);
  }

  @Test
  void createUserShouldThrowWrongDataExceptionWhenFormIsEmpty() {
    // given
    final User empty = new User(null, "", "");
    // when then
    Assertions.assertThrows(WrongDataException.class, () -> underTest.createUser(empty));
  }

  @Test
  void getAllInOutShouldReturnAllInOuts() {
    // when
    final List<InOut> actual = underTest.getAllInOut();
    // then
    Assertions.assertEquals(actual, INOUTS);
  }

  @Test
  void createInOutShouldReturnNewInOutWhenCreated() {
    // given
    User gipszJakab = new User(null, GIPSZ_JAKAB_VEZETEKNEV, GIPSZ_JAKAB_KERESZTNEV);
    final InOut three = new InOut(3L, LocalDateTime.now(zid).format(formatter), "Ki");
    final InOut expectedThree = new InOut(3L, LocalDateTime.now(zid).format(formatter), "Ki");
    // when
    final User user = underTest.createUser(gipszJakab);
    final InOut actual = underTest.createInOut(three);
    // then
    Assertions.assertEquals(actual, expectedThree);
  }

  @Test
  void createInOutShouldThrowNotFoundExceptionWhenGivenIdOfNotExistingUser() {
    // given
    final InOut one = new InOut(3L, LocalDateTime.now(zid).format(formatter), "Ki");
    // when then
    Assertions.assertThrows(NotFoundException.class, () -> underTest.createInOut(one));
  }

  @Test
  void createInOutShouldCreateInOutInWhenUserIsOut() {
    // given
    final Long userId = 2L;
    final InOut one = new InOut(userId, LocalDateTime.now(zid).format(formatter), "Be");
    // when
    final List<InOut> inOuts = underTest.getInOutsByUser(userId);
    final InOut actual = underTest.createInOut(one);
    final InOut expected = new InOut(userId, LocalDateTime.now(zid).format(formatter), "Be");
    // then
    Assertions.assertEquals(actual, expected);
  }

  @Test
  void createInOutShouldCreateInOutOutWhenUserIsIn() {
    // given
    final Long userId = 1L;
    final InOut one = new InOut(1L, LocalDateTime.now(zid).format(formatter), "Ki");
    // when
    final List<InOut> inOuts = underTest.getInOutsByUser(userId);
    final InOut actual = underTest.createInOut(one);
    final InOut expected = new InOut(1L, LocalDateTime.now(zid).format(formatter), "Ki");
    // then
    Assertions.assertEquals(actual, expected);
  }

  @Test
  void createInOutShouldThrowWrongDataExceptionWhenUserIsAlreadyIn() {
    // given
    final InOut one = new InOut(1L, LocalDateTime.now(zid).format(formatter), "Be");
    // when then
    Assertions.assertThrows(WrongDataException.class, () -> underTest.createInOut(one));
  }
  @Test
  void createInOutShouldThrowWrongDataExceptionWhenUserIsAlreadyOut() {
    // given
    final InOut two = new InOut(2L, LocalDateTime.now(zid).format(formatter), "Ki");
    // when then
    Assertions.assertThrows(WrongDataException.class, () -> underTest.createInOut(two));
  }

  @Test
  void createInOutShouldThrowWrongDataExceptionWhenGivenDirectionIsNotInOrOut() {
    // given
    final InOut one = new InOut(2L, LocalDateTime.now(zid).format(formatter), "xyz");
    // when then
    Assertions.assertThrows(WrongDataException.class, () -> underTest.createInOut(one));
  }

  @Test
  void getInOutsByUserShouldReturnAllInOutsByUserOfGivenId() {
    // given
    final Long userId = 1L;
    final List<InOut> expectedList = List.of(ONE);
    // when
    final List<InOut> actual = underTest.getInOutsByUser(userId);
    // then
    Assertions.assertEquals(actual, expectedList);
  }
}
