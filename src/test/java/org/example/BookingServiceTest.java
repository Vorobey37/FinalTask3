package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private static final Logger logger
            = LoggerFactory.getLogger(BookingServiceTest.class);
    private BookingService mokBookingService;
    private BookingService spyBookingService;
    private LocalDateTime time1;
    private LocalDateTime time2;
    private LocalDateTime time3;
    private String userId;

    @BeforeAll
    public static void startTest(){
        logger.info("Стартует тестирование!");
    }

    @BeforeEach
    public void setUp(){
        logger.info("Запускаем тест");
        logger.debug("Создаем мок класса BookingService");
        mokBookingService = Mockito.mock(BookingService.class);
        logger.debug("Создаем spy класса BookingService");
        spyBookingService = Mockito.spy(new BookingService());
        logger.debug("Создаем тестовые данные для параметра Time");
        time1 = LocalDateTime.of(2019, Month.MAY, 15, 12, 15);
        time2 = LocalDateTime.of(2020, Month.MAY, 15, 12, 15);
        time3 = LocalDateTime.of(2025, Month.MAY, 15, 12, 15);
        logger.debug("Создаем тестовые данные для параметра userId");
        userId = "153";
    }

    @Test
    public void testPositiveCheckTimeInBD(){
        logger.debug("Задаем поведение mokBookingService для операции проверки даты");
        when(mokBookingService.checkTimeInBD(time1, time2)).thenReturn(true);
        logger.debug("Проверяем действие проверки даты");
        Assertions.assertTrue(mokBookingService.checkTimeInBD(time1, time2));
        logger.debug("Проверяем выполнение действия");
        verify(mokBookingService).checkTimeInBD(time1, time2);
    }

    @Test
    public void testNegativeCheckTimeInBD(){
        logger.debug("Задаем поведение mokBookingService для операции проверки даты");
        when(mokBookingService.checkTimeInBD(time3, time2)).thenReturn(false);
        logger.debug("Проверяем действие проверки даты");
        Assertions.assertFalse(mokBookingService.checkTimeInBD(time3, time2));
        logger.debug("Проверяем выполнение действия");
        verify(mokBookingService).checkTimeInBD(time3, time2);
    }

    @Test
    public void testPositiveCreateBook(){
        logger.debug("Задаем поведение mokBookingService для операции создания книги");
        when(mokBookingService.createBook(userId, time1, time2)).thenReturn(true);
        logger.debug("Проверяем действие создания книги");
        Assertions.assertTrue(mokBookingService.createBook(userId, time1, time2));
        logger.debug("Проверяем выполнение действия");
        verify(mokBookingService).createBook(userId, time1, time2);
    }

    @Test
    public void testNegativeCreateBook(){
        logger.debug("Задаем поведение mokBookingService для операции создания книги");
        when(mokBookingService.createBook(userId, time3, time2)).thenReturn(false);
        logger.debug("Проверяем действие создания книги");
        Assertions.assertFalse(mokBookingService.createBook(userId, time3, time2));
        logger.debug("Проверяем выполнение действия");
        verify(mokBookingService).createBook(userId, time3, time2);
    }

    @Test
    public void testPositiveBook() throws CantBookException {
        logger.debug("Задаем поведение spyBookingService для операции проверки даты");
        when(spyBookingService.checkTimeInBD(time1, time2)).thenReturn(true);
        logger.debug("Задаем поведение spyBookingService для операции создания книги");
        when(spyBookingService.createBook(userId,time1,time2)).thenReturn(true);
        logger.debug("Проверяем действие проверки книги");
        Assertions.assertTrue(spyBookingService.book(userId, time1, time2));

    }

    @Test
    public void testNegativeBook() throws CantBookException {
        logger.debug("Задаем поведение spyBookingService для операции проверки даты");
        when(spyBookingService.checkTimeInBD(time3, time2)).thenReturn(false);
        logger.debug("Задаем поведение spyBookingService для операции создания книги");
        when(spyBookingService.createBook(userId,time3,time2)).thenReturn(false);
        logger.debug("Проверяем действие проверки книги");
        Assertions.assertThrows(CantBookException.class, () -> spyBookingService.book(userId, time3, time2));

    }

    @AfterAll
    public static void stopTest(){
        logger.info("Тестирование закончено!");
    }
}
