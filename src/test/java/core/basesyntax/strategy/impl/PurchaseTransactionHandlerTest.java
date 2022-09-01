package core.basesyntax.strategy.impl;

import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTransactionHandlerTest {
    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void beforeClass() {
        transactionHandler = new PurchaseTransactionHandler(new FruitStorageDaoImpl());
    }

    @Test
    public void purchaseWithEmptyStorage_ok() {
        transactionHandler.apply(new FruitTransaction("p", "banana", 5));
        Assert.assertEquals("Expected value -5", -5, (int) FruitStorage.storage.get("banana"));
    }

    @Test
    public void purchaseBanana_ok() {
        FruitStorage.storage.put("banana", 15);
        transactionHandler.apply(new FruitTransaction("p", "banana", 5));
        Assert.assertEquals("Expected value 10", 10, (int) FruitStorage.storage.get("banana"));
    }

    @Test
    public void purchaseApple_ok() {
        FruitStorage.storage.put("apple", 20);
        transactionHandler.apply(new FruitTransaction("p", "apple", 1));
        Assert.assertEquals("Expected value 19", 19, (int) FruitStorage.storage.get("apple"));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
