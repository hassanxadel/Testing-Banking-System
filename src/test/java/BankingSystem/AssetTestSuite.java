package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


public class AssetTestSuite {
    private Asset asset;
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
      
    @After
    public void tearDown() {
    }
    @Before
    public void setUp() {
        asset = new Asset(AssetType.Cash, 1000.0, 1000.0);
    }

    @Test
    public void testAssetCreation() {
        assertNotNull(asset);
        assertNotNull(asset.get_assetId());
    }

    @Test
    public void testAssetInformationRetrieval() {
        assertEquals(AssetType.Cash, asset.get_type());
        assertEquals(1000.0, asset.get_amount(), 0.001);
        assertEquals(1000.0, asset.get_equivalent_to(), 0.001);
    }
    
}
