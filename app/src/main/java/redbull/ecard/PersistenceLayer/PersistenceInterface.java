package redbull.ecard.PersistenceLayer;

import redbull.ecard.DataLayer.Model;

interface PersistenceInterface {

    public void create(Model model);
    public PersistenceInterface read(Long id);
    public void update(Model model);
    public void delete(Long id);
}


