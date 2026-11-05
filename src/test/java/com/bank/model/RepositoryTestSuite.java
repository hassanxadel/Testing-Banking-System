package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTestSuite {
    private Repository repository;
    private Asset asset;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        repository = new Repository();
        asset = new Asset(AssetType.CASH, 1000.0, 1000.0);
    }

    @AfterEach
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

    @Test
    public void testAssetAdditionAndRemoval() {
        repository.add_asset(asset);
        assertEquals(1, repository.get_assets().size());

        repository.remove_asset(asset.get_assetId());
        assertEquals(0, repository.get_assets().size());
    }

    @Test
    public void testAssetInformationRetrieval() {
        repository.add_asset(asset);
        assertEquals(1000.0, repository.get_assets().get(0).get_amount(), 0.001);
        assertEquals(1000.0, repository.get_assets().get(0).get_equivalent_to(), 0.001);
    }

    @Test
    public void testCapitalCalculationAfterRemoval() {
        repository.add_asset(asset);
        assertEquals(1000.0, repository.get_capital(), 0.001);

        repository.remove_asset(asset.get_assetId());
        assertEquals(0.0, repository.get_capital(), 0.001);
    }
}
