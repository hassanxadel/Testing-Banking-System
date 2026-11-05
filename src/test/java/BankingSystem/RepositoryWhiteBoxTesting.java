package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class RepositoryWhiteBoxTesting {
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
        repository.add_asset(asset);
        assertEquals(1, repository.get_assets().size());
    }

    @Test
    public void testAssetRemoval() {
        repository.add_asset(asset);
        repository.remove_asset(asset.get_assetId());
        assertEquals(0, repository.get_assets().size());
    }

    @Test
    public void testCapitalCalculation() {
        repository.add_asset(asset);
        assertEquals(1000.0, repository.get_capital(), 0.001);
    }
    
}

