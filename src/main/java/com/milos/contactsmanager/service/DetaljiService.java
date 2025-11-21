package com.milos.contactsmanager.service;
import com.milos.contactsmanager.dao.*;
import com.milos.contactsmanager.exceptions.DetaljiDAOException;
import com.milos.contactsmanager.exceptions.DetaljiServisException;
import com.milos.contactsmanager.exceptions.KontaktDAOException;
import com.milos.contactsmanager.model.*;
import java.util.List;

public class DetaljiService {
    private final DetaljiDAO detaljiDAO;
    private final KontaktDAO kontaktDAO;
    public DetaljiService(){
        this.detaljiDAO = new DetaljiDAOImpl();
        this.kontaktDAO = new KontaktDAOImpl();
    }
    public void dodajDetalj (int kontaktID, KontaktDetalji detalj) throws DetaljiServisException {
        try {
            Kontakt kontakt = kontaktDAO.vratiKontaktPoID(kontaktID);
            if (kontakt == null){
                throw new DetaljiServisException("Kontakt sa ID " + kontaktID + " nije pronadjen.");
            }
            detalj.setKontakt_id(kontaktID);
            detaljiDAO.dodajDetalj(detalj);
        } catch (DetaljiDAOException | KontaktDAOException e){
            throw new DetaljiServisException("Ne mogu da dodam detalj: ", e);
        }
    }
    public void obrisiDetalj(int detaljId, int kontaktId) throws DetaljiServisException {
        try {
            boolean obrisano = detaljiDAO.obrisiDetalj(detaljId, kontaktId);
            if (!obrisano){
                throw new DetaljiServisException(
                        "Detalj sa ID=" + detaljId + " ne pripada kontaktu ID=" + kontaktId + " ili ne postoji."
                );
            }
        } catch (DetaljiDAOException e){
            throw new DetaljiServisException("Ne mogu da obrisem detalj ID=" + detaljId + "za kontakt ID=" + kontaktId, e);
        }
    }
    public List<KontaktDetalji> vratiDetaljeZaKontakt(int kontaktId) throws DetaljiServisException{
        try {
            return detaljiDAO.uzmiDetaljePoKontaktID(kontaktId);
        } catch (DetaljiDAOException e){
            throw new DetaljiServisException("Ne mogu da vratim detalje za kontakt ID=" + kontaktId, e);
        }
    }

}
