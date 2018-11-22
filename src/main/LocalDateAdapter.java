package main;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String date) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }

    public String marshal(LocalDate date) throws Exception {
        return date.toString();
    }
}