package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetTestSuite {
    private Asset asset;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @AfterEach
    public void tearDown() {
    }

    @BeforeEach
    public void setUp() {
        asset = new Asset(AssetType.CASH, 1000.0, 1000.0);
    }

    @Test
    public void testAssetCreation() {
        assertNotNull(asset);
        assertNotNull(asset.get_assetId());
    }

    @Test
    public void testAssetInformationRetrieval() {
        assertEquals(AssetType.CASH, asset.get_type());
        assertEquals(1000.0, asset.get_amount(), 0.001);
        assertEquals(1000.0, asset.get_equivalent_to(), 0.001);
    }
}
