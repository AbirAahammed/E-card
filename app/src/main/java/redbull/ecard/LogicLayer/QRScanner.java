package redbull.ecard.LogicLayer;

import redbull.ecard.DataLayer.Profile;

public class QRScanner {
    public String scannedUID;
    public QRScanner(String a){
        scannedUID = a;
    }
    public void scanFetchCallbackSuccess(){
        Profile curProfile = CardDatabaseConnector.getCachedUserProfile();
        Profile scannedProfile = CardDatabaseConnector.getScannerProfile();

        if (scannedUID != null)
            scannedProfile.setUID (scannedUID);

        curProfile.getConnections().add(scannedProfile);

        ShareLogic.getInstance(curProfile).createConnection(scannedProfile.getUID());
    }

    public void scanfetchCallbackfailure(){}
}
