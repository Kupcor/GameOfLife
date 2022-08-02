package pk.pl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.execution.TestInstancesProvider;
import org.junit.jupiter.engine.extension.ExtensionRegistrar;
import org.junit.jupiter.engine.extension.ExtensionRegistry;
import org.junit.jupiter.engine.extension.MutableExtensionRegistry;

public class CellTest implements TestInstancesProvider {

    @Test
    public void testCellInstanceIsDead() {
        Cell testCell = new Cell(20,30);
        Assertions.assertFalse(testCell.isAlive());
    }

    @Test
    public void testCellInstanceLifeCycle() {
        Cell testCell = new Cell(10,300);
        Assertions.assertFalse(testCell.isAlive());

        testCell.revive();
        Assertions.assertTrue(testCell.isAlive());

        testCell.kill();
        Assertions.assertFalse(testCell.isAlive());

        testCell.revive();
        Assertions.assertTrue(testCell.isAlive());
    }

    @Test
    public void testCellInstancePositions() {
        Cell testCell = new Cell(11,332);
        Cell testCell2 = new Cell(15,3043);
        Cell testCell3 = new Cell(170,499);

        Assertions.assertEquals(11, testCell.getVerticalPosition());
        Assertions.assertEquals(15, testCell2.getVerticalPosition());
        Assertions.assertEquals(170, testCell3.getVerticalPosition());

        Assertions.assertEquals(332, testCell.getHorizontalPosition());
        Assertions.assertEquals(3043, testCell2.getHorizontalPosition());
        Assertions.assertEquals(499, testCell3.getHorizontalPosition());
    }

    @Override
    public org.junit.jupiter.api.extension.TestInstances getTestInstances(MutableExtensionRegistry extensionRegistry, org.junit.platform.engine.support.hierarchical.ThrowableCollector throwableCollector) {
        return TestInstancesProvider.super.getTestInstances(extensionRegistry, throwableCollector);
    }

    @Override
    public org.junit.jupiter.api.extension.TestInstances getTestInstances(ExtensionRegistry extensionRegistry, ExtensionRegistrar extensionRegistrar, org.junit.platform.engine.support.hierarchical.ThrowableCollector throwableCollector) {
        return null;
    }
}
