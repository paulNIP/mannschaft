package com.opolos.mannschaft.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


@Component
public class PdfProcessingTask {

    @Autowired
    private PdfService pdfService;

    @Value("${pdf.folder.path}")
    private String folderPath;

    static String[] lines;

    @Scheduled(cron = "0 * * * * ?") // Every Minute
    public void processPdfFiles() {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            if (files != null) {
                for (File file : files) {
                    try {
                        
                        //if report doesnot exist in our database
                        if(pdfService.checkReportExistence(file.getName().trim())==false){
                            //read Pdf file and insert into database
                            processPdf(file);

                        }   

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            System.out.println("Folder Soesnt exist Yooooooooo");
        }
    }

    private void processPdf(File file) throws IOException {
        

        try (PDDocument document = PDDocument.load(file)) {
            //insert report information

            String filename =  file.getName();
            String filepath =  file.getPath();

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            lines = text.split(System.getProperty("line.separator"));
            // System.out.println("Text in PDF: " + text);
            // Further processing of the text

            String clientId =lines[getLineNumberWithFilter("Auftraggeber (Kunde):")].split(":  ")[1];

            // client Details
            String client=lines[2];
            String street=lines[3];
            String address=lines[4];

            String[] vowels = {"mod","user"};
		
	        Set<String> vowelsSet = new HashSet <> (Arrays.asList(vowels));
            
            //check if client doesnot exist in user database
            if(pdfService.checkClientExistence(file.getName()) ==false){
                pdfService.insertNewUser(
                    client,
                    client.replaceAll(" ", ""),
                    client.replaceAll(" ", "").toLowerCase()+"@opolos.com",
                    "1234",
                    clientId,
                    street,
                    address,
                    null,
                    null,
                    null,
                    vowelsSet,
                    "1"
                
                );



            }else{


                //application type,identification number,test_according_to

                String application_type=lines[getLineNumberWithFilter("Geräteart: ")].split(":  ")[1].split(" ")[0];
                String identification_number=lines[getLineNumberWithFilter("Ident-. Nr.: ")].split(": ")[1];
                String test_according_to=lines[14];

                
                //manufucturer,department,measuring length,  profile
                String manufucturer = lines[getLineNumberWithFilter("Hersteller: ")].split(":  ")[1];
                String department = lines[getLineNumberWithFilter("Abteilung: ")].split(":  ")[1];
                String measuring_length = lines[getLineNumberWithFilter("Schutzleiterlänge (m): ")].split(":  ")[1];
                String profile = lines[getLineNumberWithFilter("Messprofil: ")].split(":  ")[1];

                //manufucturer,department,measuring length,  profile,type
                String type_number = lines[getLineNumberWithFilter("Typ:")];
                String serial_number = lines[getLineNumberWithFilter("Seriennummer:")];
                String device_type = lines[getLineNumberWithFilter("Gerätetyp: ")];
                String cross_section = lines[getLineNumberWithFilter("Querschnitt (qmm): ")];
                String test_date = lines[getLineNumberWithFilter("Prüfdatum: ")];

                String last = test_date.substring(test_date.lastIndexOf(' ') + 1);

                String serialNumber=serial_number.substring(serial_number.lastIndexOf(':') + 1);
                String deviceType=device_type.substring(device_type.lastIndexOf(':') + 1);
                String crossSection=cross_section.substring(cross_section.lastIndexOf(':') + 1);

                String testDate=dateConvert(last);
                String next = lines[getLineNumberWithFilter("Nächster Prüftermin: ")];
                String next_test_date = dateConvert(next.substring(next.lastIndexOf(' ') + 1));

                String num =type_number.substring(17);
                String examiner = lines[getLineNumberWithFilter("Prüfer: ")].split(": ")[1].split(" ")[0];

                 pdfService.insertNewReport(
                        Year.now().toString(),
                        client,
                        clientId,
                        application_type,
                        identification_number,
                        test_according_to,
                        manufucturer,
                        department,
                        measuring_length,
                        profile,
                        filepath,
                        filename,
                        num,
                        serialNumber,
                        deviceType,
                        crossSection,
                        testDate,
                        next_test_date,
                        examiner,
                        true  
                 );
            }
            
        }
    }


    // searches all lines for first line index containing `filter`
    static int getLineNumberWithFilter(String filter) {
        int n = 0;
        for(String line : lines) {
            if(line.indexOf(filter) != -1) {
                return n;
            }
            n++;
        }
        return -1;
    }

    // returns a full String line by number n
    static String getLine(int n) {
        return lines[n];
    }

    static String dateConvert(String inputDateString){

        // Define the input and output date formats
        DateTimeFormatter inputFormat = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTimeFormatter outputFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        
        // Parse the input date string to a DateTime object
        DateTime dateTime = inputFormat.parseDateTime(inputDateString);
        
        // Format the DateTime object to the desired output format
        String outputDateString = outputFormat.print(dateTime);

        return outputDateString;
    }


    
}
