package com.milos.contactsmanager.dao;

import com.milos.contactsmanager.exceptions.KontaktDAOException;
import com.milos.contactsmanager.model.Kontakt;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KontaktDAOImpl implements KontaktDAO {
    private static final String DATA_PATH = "src/main/resources/podaci/kontakt.csv";

    @Override
    public List<Kontakt> vratiListuKontakta() throws KontaktDAOException {
        List<Kontakt> kontakti = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(DATA_PATH))) {
            csvReader.readNext();
            String[] red;
            while ((red = csvReader.readNext()) != null) {
                kontakti.add(getKontakt(red));
            }
        } catch (CsvValidationException | IOException e) {
            throw new KontaktDAOException("Ne mogu da vratim listu kontakata iz CSV fajla.", e);
        }
        return kontakti;
    }

    @Override
    public Kontakt vratiKontaktPoID(int id) throws KontaktDAOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(DATA_PATH))) {
            csvReader.readNext();
            String[] red;
            while ((red = csvReader.readNext()) != null) {
                if (Integer.parseInt(red[0].trim()) == id) {
                    return getKontakt(red);
                }
            }
        } catch (CsvValidationException | IOException e) {
            throw new KontaktDAOException("Ne mogu da vratim kontakt iz CSV fajla.", e);
        }
        return null;
    }

    @Override
    public void dodajKontakt(Kontakt k) throws KontaktDAOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(DATA_PATH, true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

            int noviId = poslednjiSlobodanID();
            String[] red = {
                    String.valueOf(noviId),
                    k.getIme(),
                    orEmpty(k.getPrezime()),
                    orEmpty(k.getKompanija()),
                    dateToString(k.getRodjendan()),
                    orEmpty(k.getNapomene()),
                    orEmpty(k.getSlika()),
                    orEmpty(k.getAdresa())
            };
            writer.writeNext(red);
        } catch (IOException e) {
            throw new KontaktDAOException("Ne mogu da dodam novi kontakt u CSV fajl.", e);
        }
    }

    @Override
    public void azurirajKontakt(Kontakt k) throws KontaktDAOException {
        List<String[]> sviRedovi = ucitajSveRedove();
        for (int i = 1; i < sviRedovi.size(); i++) {
            if (Integer.parseInt(sviRedovi.get(i)[0].trim()) == k.getId()) {
                sviRedovi.set(i, napraviRed(k));
                break;
            }
        }
        upisiSveRedove(sviRedovi);
    }

    @Override
    public void obrisiKontakt(int id) throws KontaktDAOException {
        List<String[]> sviRedovi = ucitajSveRedove();
        for (int i = 1; i < sviRedovi.size(); i++) {
            if (Integer.parseInt(sviRedovi.get(i)[0].trim()) == id) {
                sviRedovi.remove(i);
                break;
            }
        }
        upisiSveRedove(sviRedovi);
    }

    //pomocne metode
    private List<String[]> ucitajSveRedove() throws KontaktDAOException {
        try (CSVReader reader = new CSVReader(new FileReader(DATA_PATH))) {
            return reader.readAll();
        } catch (CsvException | IOException e) {
            throw new KontaktDAOException("Ne mogu da pročitam CSV fajl kontakata.", e);
        }
    }

    private void upisiSveRedove(List<String[]> redovi) throws KontaktDAOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(DATA_PATH),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            writer.writeAll(redovi);
        } catch (IOException e) {
            throw new KontaktDAOException("Ne mogu da upišem u CSV fajl kontakata.", e);
        }
    }

    private String[] napraviRed(Kontakt k) {
        return new String[]{
                String.valueOf(k.getId()),
                k.getIme(),
                orEmpty(k.getPrezime()),
                orEmpty(k.getKompanija()),
                dateToString(k.getRodjendan()),
                orEmpty(k.getNapomene()),
                orEmpty(k.getSlika()),
                orEmpty(k.getAdresa())
        };
    }

    private String orEmpty(String s) {
        return s != null ? s : "";
    }

    private String dateToString(LocalDate date) {
        return date != null ? date.toString() : "";
    }

    private static Kontakt getKontakt(String[] vrednosti) {
        int id = Integer.parseInt(vrednosti[0].trim());
        String ime = vrednosti[1].trim();
        String prezime = vrednosti[2].trim();
        String kompanija = vrednosti[3].trim();
        LocalDate rodjendan = vrednosti[4].trim().isEmpty() ? null : LocalDate.parse(vrednosti[4].trim());
        String napomena = vrednosti[5].trim();
        String slika = vrednosti[6].trim();
        String adresa = vrednosti[7].trim();
        return new Kontakt(id, ime, prezime, kompanija, adresa, rodjendan, napomena, slika);
    }

    private int poslednjiSlobodanID() throws KontaktDAOException {
        int maxId = 0;
        try (CSVReader csvReader = new CSVReader(new FileReader(DATA_PATH))) {
            csvReader.readNext();
            String[] red;
            while ((red = csvReader.readNext()) != null) {
                int id = Integer.parseInt(red[0].trim());
                if (id > maxId) maxId = id;
            }
        } catch (CsvValidationException | IOException e) {
            throw new KontaktDAOException("Greška pri čitanju novog ID-ja za kontakt.", e);
        }
        return maxId + 1;
    }
}