package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class RepositoryTestSuite {
    private Repository repository;
    private Asset asset;

        @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
     @Before
    public void setUp() {
        repository = new Repository();
        asset = new Asset(AssetType.Cash, 1000.0, 1000.0);
    }

        @After
    public void tearDown() {
    
}
    
    @Test
    public void testAssetAddition() {
        repository.add_asset(asset); // Add asset without specifying if it's a deposit
        assertEquals(1, repository.get_assets().size());
    }

    @Test
    public void testAssetRemoval() {
        repository.add_asset(asset); // Add asset without specifying if it's a deposit
        repository.remove_asset(asset.get_assetId());
        assertEquals(0, repository.get_assets().size());
    }

    @Test
    public void testCapitalCalculation() {
        repository.add_asset(asset); // Add asset without specifying if it's a deposit
        assertEquals(1000.0, repository.get_capital(), 0.001);
    }

    @Test
    public void testAssetAdditionAndRemoval() {
        repository.add_asset(asset); // Deposite asset
        assertEquals(1, repository.get_assets().size());

        repository.remove_asset(asset.get_assetId());
        assertEquals(0, repository.get_assets().size());
    }

    @Test
    public void testAssetInformationRetrieval() {
        repository.add_asset(asset); // Deposite asset
        assertEquals(1000.0, repository.get_assets().get(0).get_amount(), 0.001);
        assertEquals(1000.0, repository.get_assets().get(0).get_equivalent_to(), 0.001);
    }

    @Test
    public void testCapitalCalculationAfterRemoval() {
        repository.add_asset(asset); // Deposite asset
        assertEquals(1000.0, repository.get_capital(), 0.001);

        repository.remove_asset(asset.get_assetId());
        assertEquals(0.0, repository.get_capital(), 0.001);
    }
}

