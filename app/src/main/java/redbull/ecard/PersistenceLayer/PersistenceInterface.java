package redbull.ecard.PersistenceLayer;

import android.util.Log;

import redbull.ecard.DataLayer.Model;

interface PersistenceInterface {

    public void create(Model model);
    public Model read(Long id);
    public void update(Model model);
    public void delete(Long id);
}


