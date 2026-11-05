import java.util.ArrayList;

public class Repository {
	private ArrayList<Asset> assets = new ArrayList<Asset>();
	private double capital;
	
	Repository() {
		
	}
	
	Repository(ArrayList<Asset> assets) {
		for(int i = 0; i < assets.size(); i++) {
			capital += assets.get(i).get_equivalent_to();
		}
	}
	
	void add_asset(Asset asset) {
		assets.add(asset);
		capital += asset.get_equivalent_to();
	}
	
	void remove_asset(String assetId) {
		for(int i=0; i < assets.size(); i++) {
			if(assets.get(i).get_assetId().equals(assetId)) {
				capital -= assets.get(i).get_equivalent_to();
				assets.remove(i);
			}
		}
	}
	
	ArrayList<Asset> get_assets() {
		return assets;
	}
	
	double get_capital() {
		return capital;
	}
}
