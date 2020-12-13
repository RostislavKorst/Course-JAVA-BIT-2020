import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleEntitiesStorageTest {
    private DebitCard debitCard1;
    private DebitCard debitCard2;
    private DebitCard debitCard3;
    private DebitCard debitCard4;
    private DebitCard debitCard5;
    private DebitCard debitCard6;
    private SimpleEntitiesStorage<Double, DebitCard> storage;

    @Test
    public void save() {
        //given
        debitCardsInit();
        saveAllDebitCardsToStorage();
        //when
        SimpleEntitiesStorage<Double, DebitCard> expectedStorage =
                new SimpleEntitiesStorage<>(account -> account.balanceOn(LocalDateTime.MAX));
        expectedStorage.saveAll(List.of(debitCard1, debitCard2, debitCard3, debitCard4, debitCard5, debitCard6));
        //then
        assertEquals(storage.toString(), expectedStorage.toString());
    }

    @Test
    public void findByKey() {
        //given
        SimpleEntitiesStorage<Double, DebitCard> storage =
                new SimpleEntitiesStorage<>(account -> account.balanceOn(LocalDateTime.MAX));
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount = new BonusAccount(0);
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccount);
        //when
        debitCard1.addCash(2000.);
        storage.save(debitCard1);
        //then
        assertEquals(debitCard1, storage.findByKey(2000.));
    }

    @Test
    public void findAll() {
        //given
        debitCardsInit();
        saveAllDebitCardsToStorage();
        //when
        //then
        assertEquals(storage.findAll(),
                List.of(debitCard2, debitCard5, debitCard1, debitCard3, debitCard6, debitCard4));
    }

    @Test
    public void deleteByKey() {
        //given
        debitCardsInit();
        saveAllDebitCardsToStorage();
        //when
        SimpleEntitiesStorage<Double, DebitCard> expectedStorage =
                new SimpleEntitiesStorage<>(account -> account.balanceOn(LocalDateTime.MAX));
        expectedStorage.saveAll(List.of(debitCard2, debitCard3, debitCard4, debitCard5, debitCard6));
        storage.deleteByKey(1450.);
        //then
        assertEquals(storage.toString(), expectedStorage.toString());
    }

    @Test
    public void deleteAll() {
        //given
        debitCardsInit();
        saveAllDebitCardsToStorage();
        //when
        SimpleEntitiesStorage<Double, DebitCard> expectedStorage =
                new SimpleEntitiesStorage<>(account -> account.balanceOn(LocalDateTime.MAX));
        storage.deleteAll(List.of(debitCard1, debitCard2, debitCard3, debitCard4, debitCard5, debitCard6));
        //then
        assertEquals(storage.toString(), expectedStorage.toString());
    }

    private void debitCardsInit() {
        storage = new SimpleEntitiesStorage<>(account -> account.balanceOn(LocalDateTime.MAX));
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount = new BonusAccount(0);
        debitCard1 = new DebitCard(transactionManager, bonusAccount);
        debitCard2 = new DebitCard(transactionManager, bonusAccount);
        debitCard3 = new DebitCard(transactionManager, bonusAccount);
        debitCard4 = new DebitCard(transactionManager, bonusAccount);
        debitCard5 = new DebitCard(transactionManager, bonusAccount);
        debitCard6 = new DebitCard(transactionManager, bonusAccount);
        debitCard1.addCash(2000);
        debitCard3.addCash(3000);
        debitCard4.addCash(4000);
        debitCard5.addCash(5000);
        debitCard6.addCash(6000);
        debitCard1.withdraw(800, debitCard2);
        debitCard1.withdraw(150, debitCard2);
        debitCard2.withdraw(400, debitCard1);
    }

    private void saveAllDebitCardsToStorage() {
        storage.save(debitCard1);
        storage.save(debitCard2);
        storage.save(debitCard3);
        storage.save(debitCard4);
        storage.save(debitCard5);
        storage.save(debitCard6);
    }
}